package tk.tarajki.meme.dto.out;

import lombok.Data;
import tk.tarajki.meme.models.Comment;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private long id;
    private String content;
    private PostDto post;
    private UserDto author;
    private LocalDateTime createdAt;
    private int likes;
    private int dislikes;
    public CommentDto(Comment comment) {

    }
}
