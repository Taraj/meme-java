package tk.tarajki.meme.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.tarajki.meme.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByNickname(String nickname);

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    User findUserByUsernameOrEmail(String username, String email);


}
