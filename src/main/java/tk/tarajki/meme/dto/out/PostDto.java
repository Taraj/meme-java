package tk.tarajki.meme.dto.out;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import tk.tarajki.meme.models.Post;
import tk.tarajki.meme.models.PostFeedback;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ApiModel(value = "Post")
public class PostDto {
    private long id;
    private String title;
    private String url;
    private List<TagDto> tags;
    private UserDto author;
    private long commentsCount;
    private long likes;
    private long dislikes;
    private LocalDateTime createdAt;

    public PostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.url = post.getUrl();
        this.tags = post.getTags().stream().map(TagDto::new).collect(Collectors.toList());
        this.author = new UserDto(post.getAuthor());
        this.commentsCount = post.getComments().size();
        this.likes = post.getPostFeedback().stream().filter(PostFeedback::isPositive).count();
        this.dislikes = post.getPostFeedback().stream().filter(it -> !it.isPositive()).count();
        this.createdAt = post.getCreatedAt();
    }
}
