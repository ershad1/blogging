package com.simple.blogging.model.appuser;

import com.simple.blogging.model.role.Role;
import com.simple.blogging.model.role.RoleRepository;
import com.simple.blogging.model.userrole.UserRole;
import com.simple.blogging.model.userrole.UserRoleRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class AppUserService {

    @Autowired
    AppUserService appUserService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Transactional
    public AppUser saveUser(String name, String username, String password, String roleStr) {
        String encryptedPassword = bCryptPasswordEncoder.encode(password);
        AppUser appUser = AppUser.builder().fullName(name).username(username).password(encryptedPassword).build();
        AppUser savedUserObj = appUserRepository.save(appUser);
        Role roleObj = roleRepository.findRoleByFullName(roleStr);
        userRoleRepository.save(UserRole.builder().appUser(savedUserObj).role(roleObj).build());
        return appUser;
    }

    public void updateUserPassword(AppUser appUser, String newpassword) {
        String encryptedPassword = bCryptPasswordEncoder.encode(newpassword);
        appUser.setPassword(encryptedPassword);
        appUserRepository.save(appUser);

    }

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    public AppUser findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    public AppUser findByEmail(String userEmail) {
        return appUserRepository.findByEmail(userEmail);
    }

    public List<AppUser> userList() {
        return appUserRepository.findAll();
    }

    public Role findUserRoleByName(String name) {
        return roleRepository.findRoleByFullName(name);
    }

    public AppUser simpleSaveUser(AppUser user) {
        appUserRepository.save(user);

        return user;

    }

    public AppUser updateUser(AppUser user, HashMap<String, String> request) {
        String name = request.get("name");
        // String username = request.get("username");
        String email = request.get("email");
        String bio = request.get("bio");
        user.setFullName(name);
        // appUser.setUsername(username);
        user.setEmail(email);
        //        user.setBio(bio);
        appUserRepository.save(user);

        return user;

    }

    public AppUser findUserById(Long id) {
        return appUserRepository.findUserById(id);
    }

    public void deleteUser(AppUser appUser) {
        appUserRepository.delete(appUser);

    }

    public void resetPassword(AppUser user) {
        String password = RandomStringUtils.randomAlphanumeric(10);
        String encryptedPassword = bCryptPasswordEncoder.encode(password);
        user.setPassword(encryptedPassword);
        appUserRepository.save(user);


    }

    public List<AppUser> getUsersListByUsername(String username) {
        return appUserRepository.findByUsernameContaining(username);
    }


}
