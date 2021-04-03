package com.simple.blogging.model.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping(value = "/user")
public class AppUserController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    AppUserService appUserService;

    @Autowired
    AppUserRepository appUserRepository;

    @GetMapping("/list")
    public ResponseEntity<?> getUsersList() {
        List<AppUser> users = appUserService.userList();

        if (users.isEmpty()) {
            return new ResponseEntity<>("No Users Found.", HttpStatus.OK);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserInfo(@PathVariable String username) {
        AppUser user = appUserService.findByUsername(username);
        if (user == null) {
            return new ResponseEntity<>("No Users Found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/findByUsername/{username}")
    public ResponseEntity<?> getUsersListByUsername(@PathVariable String username) {
        List<AppUser> users = appUserService.getUsersListByUsername(username);
        if (users.isEmpty()) {
            return new ResponseEntity<>("No Users Found.", HttpStatus.OK);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody HashMap<String, String> request) {
        String fullName = request.get("name");
        String username = request.get("username");
        String password = request.get("password");
        String roleStr = request.get("role");
        if (appUserService.findByUsername(username) != null) {
            return new ResponseEntity<>("usernameExist", HttpStatus.CONFLICT);
        }
        try {
            AppUser user = appUserService.saveUser(fullName, username, password, roleStr);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occured", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestBody HashMap<String, String> request) {
        String id = request.get("id");
        AppUser user = appUserService.findUserById(Long.parseLong(id));
        if (user == null) {
            return new ResponseEntity<>("userNotFound", HttpStatus.NOT_FOUND);
        }
        try {
            appUserService.updateUser(user, request);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occured", HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestBody HashMap<String, String> mapper) {
        String username = mapper.get("username");
        AppUser user = appUserService.findByUsername(username);
        appUserService.deleteUser(user);
        return new ResponseEntity<String>("User Deleted Successfully!", HttpStatus.OK);
    }

    @PostMapping("/activate")
    public ResponseEntity<String> activateUser(@RequestBody HashMap<String, String> mapper) {
        String username = mapper.get("username");
        AppUser user = appUserService.findByUsername(username);
        user.setIsActive(true);
        appUserRepository.save(user);
        return new ResponseEntity<String>("User Activated Successfully!", HttpStatus.OK);
    }
    @PostMapping("/deactivate")
    public ResponseEntity<String> deactivateUser(@RequestBody HashMap<String, String> mapper) {
        String username = mapper.get("username");
        AppUser user = appUserService.findByUsername(username);
        user.setIsActive(false);
        appUserRepository.save(user);
        return new ResponseEntity<String>("User Deactivated Successfully!", HttpStatus.OK);
    }


}
