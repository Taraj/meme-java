package tk.tarajki.meme.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.tarajki.meme.models.Comment;
import tk.tarajki.meme.models.CommentFeedback;
import tk.tarajki.meme.models.User;

@Repository
public interface CommentFeedbackRepository extends CrudRepository<CommentFeedback, Long> {
    CommentFeedback findCommentFeedbackByAuthorAndTarget(User author, Comment target);
}
