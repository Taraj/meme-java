package tk.tarajki.meme.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.tarajki.meme.models.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    Comment findCommentById(long id);
}
