package com.simple.blogging.dataloader;

import com.simple.blogging.model.role.Role;
import com.simple.blogging.model.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Order(1)
public class RoleDataLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;

    public RoleDataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        int count = roleRepository.findAll().size();
        if (count == 0) {
            loadData();
        }
    }

    private void loadData() {
        List<Role> roles = new ArrayList<>();

        roles.add(Role.builder().fullName("SUPER_ADMIN").build());
        roles.add(Role.builder().fullName("USER").build());
        roleRepository.saveAll(roles);
    }
}
