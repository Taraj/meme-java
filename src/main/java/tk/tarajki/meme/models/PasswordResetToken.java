package tk.tarajki.meme.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private LocalDateTime expireAt;

    @Column(nullable = false, unique = true)
    private int code;

    @ManyToOne(fetch = FetchType.LAZY)
    private User target;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public PasswordResetToken(LocalDateTime expireAt, int code, User target) {
        this.expireAt = expireAt;
        this.code = code;
        this.target = target;
    }
}
