package tk.tarajki.meme.services;

import org.springframework.stereotype.Service;
import tk.tarajki.meme.models.Role;
import tk.tarajki.meme.models.RoleName;
import tk.tarajki.meme.repositories.RoleRepository;

import javax.transaction.Transactional;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    public void createIfNoExist(RoleName name) {
        Role role = roleRepository.findRoleByName(name);
        if (role == null) {
            roleRepository.save(
                    new Role(
                            name
                    )
            );
        }
    }

}
