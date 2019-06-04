package tk.tarajki.meme.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class UserFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private boolean isPositive;

    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    private User target;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public UserFeedback(boolean isPositive, User author, User target) {
        this.isPositive = isPositive;
        this.author = author;
        this.target = target;
    }
}
