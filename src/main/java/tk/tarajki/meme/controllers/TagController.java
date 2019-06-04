package tk.tarajki.meme.controllers;

import org.springframework.web.bind.annotation.*;
import tk.tarajki.meme.dto.out.PostDto;
import tk.tarajki.meme.services.TagService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/tags")
public class TagController {

    private TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/{name}/posts")
    public List<PostDto> getPostsByTag(
            @PathVariable String name,
            @RequestParam(name = "offset", defaultValue = "0") long offset,
            @RequestParam(name = "count", defaultValue = "10") long count
    ) {
        return tagService.getAllPostsDtoByTagName(name, offset, count);
    }

}
