package tk.tarajki.meme.dto.in;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ConfirmResetPasswordRequest {

    private int code;

    @NotBlank
    private String usernameOrEmail;
}
