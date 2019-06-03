package tk.tarajki.meme.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.tarajki.meme.models.Role;
import tk.tarajki.meme.models.RoleName;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{
    Role findRoleByName(RoleName name);
}
