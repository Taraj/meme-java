package tk.tarajki.meme.dto.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ResetPasswordRequest {

    @NotBlank
    @ApiModelProperty(required = true)
    private String usernameOrEmail;

}
