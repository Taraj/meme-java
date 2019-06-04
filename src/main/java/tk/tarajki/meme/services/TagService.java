package tk.tarajki.meme.services;

import org.springframework.stereotype.Service;
import tk.tarajki.meme.dto.out.PostDto;
import tk.tarajki.meme.exceptions.ResourceNotFoundException;
import tk.tarajki.meme.models.Post;
import tk.tarajki.meme.models.Tag;
import tk.tarajki.meme.repositories.TagRepository;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    private TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Transactional
    public Tag getOrCreateTag(String name) {
        Tag tag = tagRepository.findTagByName(name);
        if (tag == null) {
            tag = tagRepository.save(new Tag(
                    name
            ));
        }
        return tag;
    }

    public List<PostDto> getAllPostsDtoByTagName(String tagName, long offset, long count) {

        Tag tag = tagRepository.findTagByName(tagName);

        if (tag == null) {
            throw new ResourceNotFoundException("Tag not found.");
        }

        List<Post> posts = tag.getPosts();
        if (posts == null) {
            return Collections.emptyList();
        }

        return posts.stream()
                .skip(offset)
                .limit(count)
                .map(PostDto::new).collect(Collectors.toList());
    }

}
