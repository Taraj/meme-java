package tk.tarajki.meme.dto.in;

import lombok.Data;

@Data
public class ConfirmResetPasswordRequest {
    int code;
    String usernameOrEmail;
}
