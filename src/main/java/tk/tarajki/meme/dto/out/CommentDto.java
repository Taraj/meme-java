package tk.tarajki.meme.dto.out;

import lombok.Data;
import tk.tarajki.meme.models.Comment;
import tk.tarajki.meme.models.CommentFeedback;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private long id;
    private String content;
    private UserDto author;
    private LocalDateTime createdAt;
    private long likes;
    private long dislikes;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.author = new UserDto(comment.getAuthor());
        this.createdAt = comment.getCreatedAt();
        this.likes = comment.getCommentFeedback().stream().filter(CommentFeedback::isPositive).count();
        this.dislikes = comment.getCommentFeedback().stream().filter(it -> !it.isPositive()).count();
    }
}
