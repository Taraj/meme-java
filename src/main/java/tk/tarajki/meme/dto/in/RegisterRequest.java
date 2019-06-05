package tk.tarajki.meme.dto.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.tarajki.meme.validators.annotations.Nickname;
import tk.tarajki.meme.validators.annotations.Password;
import tk.tarajki.meme.validators.annotations.Username;

import javax.validation.constraints.Email;

@Data
public class RegisterRequest {

    @Username
    @ApiModelProperty(required = true)
    private String username;

    @Password
    @ApiModelProperty(required = true)
    private String password;

    @Nickname
    @ApiModelProperty(required = true)
    private String nickname;

    @Email
    @ApiModelProperty(required = true)
    private String email;

}
