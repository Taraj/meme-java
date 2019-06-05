package tk.tarajki.meme.dto.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import tk.tarajki.meme.validators.annotations.PostTitle;
import tk.tarajki.meme.validators.annotations.TagList;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class PostRequest {

    @PostTitle
    @ApiModelProperty(required = true)
    private String title;

    @URL
    @NotBlank
    @ApiModelProperty(required = true)
    private String url;

    @TagList
    @ApiModelProperty(required = true)
    private List<String> tags;
}
