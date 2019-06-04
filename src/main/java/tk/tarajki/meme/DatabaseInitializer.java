package tk.tarajki.meme;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import tk.tarajki.meme.models.RoleName;
import tk.tarajki.meme.services.RoleService;

@Component
public class DatabaseInitializer implements ApplicationRunner {

    private RoleService roleService;

    public DatabaseInitializer(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void run(ApplicationArguments args){
        roleService.createIfNoExist(RoleName.ROLE_ADMIN);
        roleService.createIfNoExist(RoleName.ROLE_USER);
    }

}
