package tk.tarajki.meme.dto.out;

import lombok.Data;
import tk.tarajki.meme.models.Post;
import tk.tarajki.meme.models.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDto {
  private long id;
  private String title;
  private String url;
  private List<TagDto> tags;
  private User author;
  private int commentsCount;
  private int likes;
  private int dislikes;
  private LocalDateTime createdAt;
  public   PostDto(Post post){

    }
}
