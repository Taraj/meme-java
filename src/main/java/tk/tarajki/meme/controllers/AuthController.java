package tk.tarajki.meme.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tk.tarajki.meme.dto.in.ConfirmResetPasswordRequest;
import tk.tarajki.meme.dto.in.LoginRequest;
import tk.tarajki.meme.dto.in.RegisterRequest;
import tk.tarajki.meme.dto.in.ResetPasswordRequest;
import tk.tarajki.meme.dto.out.JwtAuthResponse;
import tk.tarajki.meme.services.UserService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public JwtAuthResponse login(
            @RequestBody @Valid LoginRequest loginRequest
    ) {
        return userService.login(loginRequest);
    }

    @PostMapping("/register")
    public JwtAuthResponse register(
            @RequestBody @Valid RegisterRequest registerRequest
    ) {
        return userService.register(registerRequest);
    }

    @PostMapping("/reset")
    public ResponseEntity sendResetPasswordEmail(
            @RequestBody @Valid ResetPasswordRequest resetPasswordRequest
    ) {
        userService.sendResetPasswordEmail(resetPasswordRequest);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping("/reset/confirm")
    public  ResponseEntity confirmPasswordReset(
            @RequestBody @Valid ConfirmResetPasswordRequest confirmResetPasswordRequest
    ) {
        userService.resetPassword(confirmResetPasswordRequest);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

}
