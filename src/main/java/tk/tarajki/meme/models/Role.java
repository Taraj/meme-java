package tk.tarajki.meme.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private RoleName name;

    @JoinColumn(name = "role_id")
    @OneToMany(fetch = FetchType.LAZY)
    private List<User> users;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Role(RoleName name) {
        this.name = name;
    }
}
