package tk.tarajki.meme.dto.out;

import lombok.Data;
import tk.tarajki.meme.models.Tag;

@Data
public class TagDto {
    private long id;
    private String name;
    private long postsCount;

    public TagDto(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
        this.postsCount = tag.getPosts().size();
    }
}
