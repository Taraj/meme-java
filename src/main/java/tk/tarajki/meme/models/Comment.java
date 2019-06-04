package tk.tarajki.meme.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @JoinColumn(name = "target_id")
    @OneToMany(fetch = FetchType.LAZY)
    private List<CommentFeedback> commentFeedback;

    public Comment(String content, User author, Post post) {
        this.content = content;
        this.author = author;
        this.post = post;
    }
}
