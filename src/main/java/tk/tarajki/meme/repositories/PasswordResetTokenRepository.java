package tk.tarajki.meme.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.tarajki.meme.models.PasswordResetToken;
import tk.tarajki.meme.models.User;

@Repository
public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Long> {
    PasswordResetToken findPasswordResetTokenByCode(int code);

    void deletePasswordResetTokensByTarget(User target);
}
