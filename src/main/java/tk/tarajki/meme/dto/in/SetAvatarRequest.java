package tk.tarajki.meme.dto.in;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

@Data
public class SetAvatarRequest {

    @URL @NotBlank
    private String avatarURL;

}
