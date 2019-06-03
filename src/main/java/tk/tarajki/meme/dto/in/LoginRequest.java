package tk.tarajki.meme.dto.in;

import lombok.Data;

@Data
public class LoginRequest {
    String username;
    String password;
}
