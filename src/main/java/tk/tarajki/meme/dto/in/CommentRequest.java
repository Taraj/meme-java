package tk.tarajki.meme.dto.in;

import lombok.Data;
import tk.tarajki.meme.validators.annotations.Comment;

import javax.validation.constraints.NotBlank;

@Data
public class CommentRequest {

    @Comment
    private String content;
}
