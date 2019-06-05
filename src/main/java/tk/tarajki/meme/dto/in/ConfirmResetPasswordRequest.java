package tk.tarajki.meme.dto.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ConfirmResetPasswordRequest {

    @ApiModelProperty(required = true)
    private int code;

    @NotBlank
    @ApiModelProperty(required = true)
    private String usernameOrEmail;
}
