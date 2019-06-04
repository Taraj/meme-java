package tk.tarajki.meme.dto.in;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class SetAvatarRequest {

    @URL
    private String avatarURL;

}
