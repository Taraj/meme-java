package tk.tarajki.meme.dto.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FeedbackRequest {
    @ApiModelProperty(required = true)
    private boolean like;
}
