package tk.tarajki.meme.dto.in;

import lombok.Data;

import java.util.List;

@Data
public class PostRequest {
    String title;
    String url;
    List<String> tags;
}
