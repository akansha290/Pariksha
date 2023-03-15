package com.exam.examserver.service;

import com.exam.examserver.model.User;
import com.exam.examserver.model.UserRole;

import java.util.List;
import java.util.Locale;
import java.util.Set;

public interface UserService {

    //Creating User
    public User createUser(User user, Set<UserRole> userRoles) throws Exception;

    //Get all users;
    public List<User> getAllUsers();

    //Getting User by Username
    public User getUser(String username);

    //Delete User by UserId
    public void deleteUserByUserId(Long userId);

    //Delete User by UserName
    public void deleteUserByUsername(String username);


}
