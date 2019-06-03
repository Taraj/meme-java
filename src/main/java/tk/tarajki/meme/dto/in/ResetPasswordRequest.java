package tk.tarajki.meme.dto.in;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    String usernameOrEmail;
}
