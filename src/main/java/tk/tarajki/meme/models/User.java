package tk.tarajki.meme.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Data
@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, length = 32)
    private String username;

    @Lob
    @Column(nullable = false)
    private String avatarURL = "https://www.w3schools.com/w3css/img_avatar3.png";

    @Column(nullable = false, unique = true, length = 32)
    private String nickname;

    @Column(nullable = false, unique = true, length = 64)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    @JoinColumn(name = "author_id")
    @OneToMany(fetch = FetchType.LAZY)
    private List<Post> posts;

    @JoinColumn(name = "author_id")
    @OneToMany(fetch = FetchType.LAZY)
    private List<Comment> comments;

    @JoinColumn(name = "target_id")
    @OneToMany(fetch = FetchType.LAZY)
    private List<UserFeedback> userFeedback;

    private Integer activationToken = ThreadLocalRandom.current().nextInt(10000, 99999);

    @Column(nullable = false)
    private LocalDateTime lastTokenRelease = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public User(String username, String nickname, String email, String password, Role role) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
