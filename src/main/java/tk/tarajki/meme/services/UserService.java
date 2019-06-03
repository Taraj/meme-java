package tk.tarajki.meme.services;

import net.bytebuddy.utility.RandomString;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tk.tarajki.meme.dto.in.*;
import tk.tarajki.meme.dto.out.JwtAuthResponse;
import tk.tarajki.meme.dto.out.PostDto;
import tk.tarajki.meme.dto.out.UserDto;
import tk.tarajki.meme.exceptions.ResourceAlreadyExistException;
import tk.tarajki.meme.exceptions.ResourceNotFoundException;
import tk.tarajki.meme.exceptions.UserAuthException;
import tk.tarajki.meme.models.*;
import tk.tarajki.meme.repositories.PasswordResetTokenRepository;
import tk.tarajki.meme.repositories.RoleRepository;
import tk.tarajki.meme.repositories.UserFeedbackRepository;
import tk.tarajki.meme.repositories.UserRepository;
import tk.tarajki.meme.security.JwtTokenProvider;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private EmailService emailService;
    private PasswordResetTokenRepository passwordResetTokenRepository;
    private UserFeedbackRepository userFeedbackRepository;

    public UserService(
            UserRepository userRepository, RoleRepository roleRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            AuthenticationManager authenticationManager,
            JwtTokenProvider jwtTokenProvider,
            EmailService emailService,
            PasswordResetTokenRepository passwordResetTokenRepository,
            UserFeedbackRepository userFeedbackRepository
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.emailService = emailService;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.userFeedbackRepository = userFeedbackRepository;
    }


    public UserDto getUserDtoByNickname(String nickname) {
        User user = findUserByNickname(nickname);
        return new UserDto(user);
    }


    public List<PostDto> getUserPostsDtoByNickname(String nickname, int offset, int count) {
        List<Post> posts = findUserByNickname(nickname).getPosts();
        if (posts == null) {
            return Collections.emptyList();
        }
        return posts.stream()
                .skip(offset)
                .limit(count)
                .map(PostDto::new)
                .collect(Collectors.toList());

    }

    public void releaseNewToken(User user) {
        user.setLastTokenRelease(LocalDateTime.now());
        userRepository.save(user);
    }

    @Transactional
    public void changePassword(User user, ChangePasswordRequest changePasswordRequest) {
        if (bCryptPasswordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            changePassword(user, changePasswordRequest.getNewPassword());
        } else {
            throw new UserAuthException("Invalid password.");
        }
    }

    private void changePassword(User user, String newPassword) {
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        user.setLastTokenRelease(LocalDateTime.now());
        userRepository.save(user);
    }


    User findUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        return user;
    }

    private User findUserByNickname(String nickname) {
        User user = userRepository.findUserByNickname(nickname);
        if (user == null) {
            throw new ResourceNotFoundException("User not found.");
        }
        return user;
    }

    public JwtAuthResponse login(LoginRequest loginRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        User user = findUserByUsername(loginRequest.getUsername());

        return createAuthResponse(user);

    }

    @Transactional
    public void activeAccount(User user, ActiveRequest activeRequest) {
        if (user.getActivationToken() == activeRequest.getCode()) {
            user.setActivationToken(null);
            userRepository.save(user);
        } else {
            throw new UserAuthException("Bad token.");
        }

    }

    @Transactional
    public void addFeedback(String nickname, FeedbackRequest feedbackRequest, User author) {
        User user = findUserByNickname(nickname);

        if (userFeedbackRepository.findUserFeedbackByAuthorAndTarget(author, user) == null) {

            userFeedbackRepository.save(new UserFeedback(
                    feedbackRequest.isLike(),
                    author,
                    user
            ));

        } else {
            throw new ResourceAlreadyExistException("You already vote.");
        }
    }

    @Transactional
    public void resendActivationToken(User user) {
        if (user.getActivationToken() == null) {
            throw new ResourceAlreadyExistException("Your account is active.");
        }
        user.setActivationToken(ThreadLocalRandom.current().nextInt(10000, 99999));
        User editedUser = userRepository.save(user);
        if (editedUser == null) {
            throw new RuntimeException("Unknown error");
        }
        emailService.sendConfirmationEmail(editedUser, editedUser.getActivationToken());
    }

    @Transactional
    public void setAvatar(User user, SetAvatarRequest setAvatarRequest) {
        user.setAvatarURL(setAvatarRequest.getAvatarURL());
        userRepository.save(user);
    }

    @Transactional
    public void resetPassword(ConfirmResetPasswordRequest confirmResetPasswordRequest) {

        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findPasswordResetTokenByCode(confirmResetPasswordRequest.getCode());
        if (passwordResetToken == null) {
            throw new ResourceNotFoundException("User not found");
        }

        User user = passwordResetToken.getTarget();

        if (!user.getEmail().equals(confirmResetPasswordRequest.getUsernameOrEmail()) && !user.getUsername().equals(confirmResetPasswordRequest.getUsernameOrEmail())) {
            throw new ResourceNotFoundException("User not found");
        }

        if (passwordResetToken.getExpireAt().compareTo(LocalDateTime.now()) < 0) {
            throw new ResourceNotFoundException("Code expired");
        }

        String newPassword = RandomString.make(8);
        changePassword(user, newPassword);

        passwordResetTokenRepository.delete(passwordResetToken);
        emailService.sendNewPassword(user, newPassword);
    }

    @Transactional
    public void sendResetPasswordEmail(ResetPasswordRequest resetPasswordRequest) {
        User user = userRepository.findUserByUsernameOrEmail(resetPasswordRequest.getUsernameOrEmail(), resetPasswordRequest.getUsernameOrEmail());
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        passwordResetTokenRepository.deletePasswordResetTokensByTarget(user);

        PasswordResetToken savedToken = passwordResetTokenRepository.save(new PasswordResetToken(
                LocalDateTime.now().plusHours(2),
                ThreadLocalRandom.current().nextInt(10000, 99999),
                user
        ));

        emailService.sendResetPasswordRequest(user, savedToken.getCode());
    }

    @Transactional
    public JwtAuthResponse register(RegisterRequest registerRequest) {

        if (userRepository.findUserByEmail(registerRequest.getEmail()) != null) {
            throw new ResourceAlreadyExistException("Email already exist.");
        }

        if (userRepository.findUserByUsername(registerRequest.getUsername()) != null) {
            throw new ResourceAlreadyExistException("Username already exist.");
        }

        if (userRepository.findUserByNickname(registerRequest.getNickname()) != null) {
            throw new ResourceAlreadyExistException("Nickname already exist.");
        }

        Role role = roleRepository.findRoleByName(RoleName.ROLE_USER);

        if (role == null) {
            throw new ResourceNotFoundException("Role not found.");
        }

        User user = userRepository.save(new User(
                registerRequest.getUsername(),
                registerRequest.getNickname(),
                registerRequest.getEmail(),
                bCryptPasswordEncoder.encode(registerRequest.getPassword()),
                role
        ));

        if (user == null) {
            throw new RuntimeException("Unknown error");
        }


        emailService.sendConfirmationEmail(user, user.getActivationToken());

        return createAuthResponse(user);
    }


    private JwtAuthResponse createAuthResponse(User user) {
        return new JwtAuthResponse(
                jwtTokenProvider.createToken(user.getUsername(), user.getLastTokenRelease()),
                user.getRole().getName() == RoleName.ROLE_ADMIN,
                user.getActivationToken() == null,
                user.getNickname()
        );
    }
}
