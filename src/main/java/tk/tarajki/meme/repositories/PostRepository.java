package tk.tarajki.meme.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.tarajki.meme.models.Post;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    Post findPostById(long id);
}
