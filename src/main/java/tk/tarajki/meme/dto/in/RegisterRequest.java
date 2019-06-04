package tk.tarajki.meme.dto.in;

import lombok.Data;
import tk.tarajki.meme.validators.annotations.Nickname;
import tk.tarajki.meme.validators.annotations.Password;
import tk.tarajki.meme.validators.annotations.Username;

import javax.validation.constraints.Email;

@Data
public class RegisterRequest {

    @Username
    private String username;

    @Password
    private String password;

    @Nickname
    private String nickname;

    @Email
    private String email;

}
