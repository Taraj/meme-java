package tk.tarajki.meme.dto.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {

    @NotBlank
    @ApiModelProperty(required = true)
    private String username;

    @NotBlank
    @ApiModelProperty(required = true)
    private String password;

}
