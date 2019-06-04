package tk.tarajki.meme.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String url;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;

    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    private User confirmedBy;

    private LocalDateTime confirmedAt;

    @JoinColumn(name = "post_id")
    @OneToMany(fetch = FetchType.LAZY)
    private List<Comment> comments;

    @JoinColumn(name = "target_id")
    @OneToMany(fetch = FetchType.LAZY)
    private List<PostFeedback> postFeedback;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Post(String title, String url, List<Tag> tags, User author) {
        this.title = title;
        this.url = url;
        this.tags = tags;
        this.author = author;
    }
}
