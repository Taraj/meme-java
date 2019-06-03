package tk.tarajki.meme.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class PostFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String authorIp;

    @Column(nullable = false)
    private boolean isPositive;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post target;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public PostFeedback(String authorIp, boolean isPositive, Post target) {
        this.authorIp = authorIp;
        this.isPositive = isPositive;
        this.target = target;
    }
}
