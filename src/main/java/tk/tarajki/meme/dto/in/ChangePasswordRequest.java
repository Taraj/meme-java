package tk.tarajki.meme.dto.in;

import lombok.Data;
import tk.tarajki.meme.validators.annotations.Password;

import javax.validation.constraints.NotBlank;

@Data
public class ChangePasswordRequest {

    @NotBlank
    private String oldPassword;

    @Password
    private String newPassword;
}
