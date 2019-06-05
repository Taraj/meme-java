package tk.tarajki.meme.dto.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.tarajki.meme.validators.annotations.Comment;

@Data
public class CommentRequest {

    @Comment
    @ApiModelProperty(required = true)
    private String content;
}
