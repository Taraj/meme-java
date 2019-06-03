package tk.tarajki.meme.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.tarajki.meme.models.User;
import tk.tarajki.meme.models.UserFeedback;

@Repository
public interface UserFeedbackRepository extends CrudRepository<UserFeedback, Long> {
    UserFeedback findUserFeedbackByAuthorAndTarget(User author, User target);
}
