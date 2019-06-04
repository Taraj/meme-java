package tk.tarajki.meme.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tk.tarajki.meme.dto.in.ActiveRequest;
import tk.tarajki.meme.dto.in.ChangePasswordRequest;
import tk.tarajki.meme.dto.in.SetAvatarRequest;
import tk.tarajki.meme.dto.out.UserDto;
import tk.tarajki.meme.security.UserPrincipal;
import tk.tarajki.meme.services.UserService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/self")
public class SelfController {

    private UserService userService;

    public SelfController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/active")
    public ResponseEntity activeAccount(
            @RequestBody @Valid ActiveRequest activeRequest,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        userService.activeAccount(principal.getUser(), activeRequest);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping("/active/resend")
    public ResponseEntity resend(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        userService.resendActivationToken(principal.getUser());
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping("/password")
    public ResponseEntity changePassword(
            @RequestBody @Valid ChangePasswordRequest changePasswordRequest,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        userService.changePassword(principal.getUser(), changePasswordRequest);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping("/logout")
    public ResponseEntity logoutFromAll(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        userService.releaseNewToken(principal.getUser());
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping("/avatar")
    public ResponseEntity setAvatar(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody @Valid SetAvatarRequest setAvatarRequest
    ) {
        userService.setAvatar(principal.getUser(), setAvatarRequest);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}
