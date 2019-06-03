package tk.tarajki.meme.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.tarajki.meme.models.Post;
import tk.tarajki.meme.models.PostFeedback;

@Repository
public interface PostFeedbackRepository extends CrudRepository<PostFeedback, Long>{
    PostFeedback findPostFeedbackByAuthorIpAndTarget(String authorIp, Post target);
}
