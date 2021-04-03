package com.simple.blogging.dataloader;

import com.simple.blogging.model.appuser.AppUser;
import com.simple.blogging.model.appuser.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@Order(1)
public class AppUserDataLoader implements CommandLineRunner {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AppUserDataLoader(AppUserRepository appUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.appUserRepository = appUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        int count = appUserRepository.findAll().size();
        if (count == 0) {
            loadData();
        }
    }

    private void loadData() {
        List<AppUser> appUsers = new ArrayList<>();

        appUsers.add(AppUser.builder().fullName("Admin").username("admin").isActive(true).password(bCryptPasswordEncoder.encode("123")).build());
        appUserRepository.saveAll(appUsers);
    }
}
