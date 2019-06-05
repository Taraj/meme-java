package tk.tarajki.meme.dto.out;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "AuthResponse")
public class JwtAuthResponse {
    private String accessToken;
    private boolean isAdmin;
    private boolean isActive;
    private String nickname;
    private String tokenType = "Bearer";

    public JwtAuthResponse(String accessToken, boolean isAdmin, boolean isActive, String nickname) {
        this.accessToken = accessToken;
        this.isAdmin = isAdmin;
        this.isActive = isActive;
        this.nickname = nickname;
    }
}
