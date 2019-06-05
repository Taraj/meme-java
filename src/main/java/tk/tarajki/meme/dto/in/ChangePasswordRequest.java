package tk.tarajki.meme.dto.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.tarajki.meme.validators.annotations.Password;

import javax.validation.constraints.NotBlank;

@Data
public class ChangePasswordRequest {

    @NotBlank
    @ApiModelProperty(required = true)
    private String oldPassword;

    @Password
    @ApiModelProperty(required = true)
    private String newPassword;
}
