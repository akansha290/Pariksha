package com.exam.examserver.controller;

import com.exam.examserver.model.Role;
import com.exam.examserver.model.User;
import com.exam.examserver.model.UserRole;
import com.exam.examserver.repo.UserRepository;
import com.exam.examserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

//    @Autowired
//    private UserRepository userRepository;

    //Creating User
    @PostMapping("/")
    public User createUser(@RequestBody User user) throws Exception {
        Set<UserRole> roleSet = new HashSet<>();

        Role role = new Role();
        role.setRoleId(45L);
        role.setRoleName("NORMAL");

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);

        roleSet.add(userRole);

        return this.userService.createUser(user,roleSet);
    }

    //Getting all Users
    @GetMapping("/all")
    public List<User> getAllUsers(){
        return this.userService.getAllUsers().stream().toList();
    }

    //Getting User From Username
    @GetMapping("/{username}")
    public User getUser(@PathVariable("username") String username){
        return this.userService.getUser(username);
    }

    //Delete the user by id
    @DeleteMapping("/userId/{userId}")
    public void deleteUserByUserId(@PathVariable("userId") Long userId){
        this.userService.deleteUserByUserId(userId);
    }

    //Delete the user by username
    @DeleteMapping("/username/{username}")
    public void deleteUserByUsername(@PathVariable("username") String username){
        this.userService.deleteUserByUsername(username);
    }
}
