package tk.tarajki.meme.dto.in;

import lombok.Data;

@Data
public class RegisterRequest {
   String username;
   String password;
   String nickname;
   String email;
}
