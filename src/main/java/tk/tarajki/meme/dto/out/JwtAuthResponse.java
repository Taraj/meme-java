package tk.tarajki.meme.dto.out;

import lombok.Data;

@Data
public class JwtAuthResponse {
    private String accessToken;
    private boolean admin;
    private boolean active;
    private String nickname;
    private String tokenType = "Bearer";

    public JwtAuthResponse(String accessToken, boolean admin, boolean active, String nickname) {
        this.accessToken = accessToken;
        this.admin = admin;
        this.active = active;
        this.nickname = nickname;
    }
}
