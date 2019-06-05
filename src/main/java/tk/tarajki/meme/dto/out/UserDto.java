package tk.tarajki.meme.dto.out;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import tk.tarajki.meme.models.User;
import tk.tarajki.meme.models.UserFeedback;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "User")
public class UserDto {
    private long id;
    private String nickname;
    private String avatar;
    private LocalDateTime createdAt;
    private long commentsCount;
    private long postsCount;
    private long likes;
    private long dislikes;

    public UserDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.avatar = user.getAvatarURL();
        this.createdAt = user.getCreatedAt();
        this.commentsCount = user.getComments().size();
        this.postsCount = user.getPosts().size();
        this.likes = user.getUserFeedback().stream().filter(UserFeedback::isPositive).count();
        this.dislikes = user.getUserFeedback().stream().filter(it -> !it.isPositive()).count();
    }
}
