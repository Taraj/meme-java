package tk.tarajki.meme.dto.in;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ResetPasswordRequest {

    @NotBlank
    private String usernameOrEmail;

}
