package tk.tarajki.meme.dto.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

@Data
public class SetAvatarRequest {

    @URL
    @NotBlank
    @ApiModelProperty(required = true)
    private String avatarURL;

}
