package com.simple.blogging.dataloader;

import com.simple.blogging.model.appuser.AppUser;
import com.simple.blogging.model.appuser.AppUserRepository;
import com.simple.blogging.model.role.Role;
import com.simple.blogging.model.role.RoleRepository;
import com.simple.blogging.model.userrole.UserRole;
import com.simple.blogging.model.userrole.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class UserRoleDataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final AppUserRepository appUserRepository;
    private final UserRoleRepository userRoleRepository;

    public UserRoleDataLoader(RoleRepository roleRepository, AppUserRepository appUserRepository, UserRoleRepository userRoleRepository) {
        this.roleRepository = roleRepository;
        this.appUserRepository = appUserRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        int count = userRoleRepository.findAll().size();
        if (count == 0) {
            loadData();
        }
    }

    private void loadData() {
        AppUser superAdminUser = appUserRepository.findByUsername("ershad");
        Role superAdminRole = roleRepository.findRoleByFullName("SUPER_ADMIN");
        userRoleRepository.save(UserRole.builder().appUser(superAdminUser).role(superAdminRole).build());
    }
}
