package tk.tarajki.meme.dto.in;

import lombok.Data;
import org.hibernate.validator.constraints.URL;
import tk.tarajki.meme.validators.annotations.PostTitle;
import tk.tarajki.meme.validators.annotations.TagList;

import java.util.List;

@Data
public class PostRequest {

    @PostTitle
    private String title;

    @URL
    private String url;

    @TagList
    private List<String> tags;
}
