package tk.tarajki.meme.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class CommentFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private boolean isPositive;

    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment target;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public CommentFeedback(boolean isPositive, User author, Comment target) {
        this.isPositive = isPositive;
        this.author = author;
        this.target = target;
    }
}
