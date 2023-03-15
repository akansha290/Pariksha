package com.exam.examserver.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String about;
    private String profile;

    //user has many roles
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "user")
    @JsonIgnore
    private Set<UserRole> userRoles = new HashSet<>();

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public User(Long userId, String username, String password, String firstName, String lastName, String email, String phone, String about, String profile, boolean enabled) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.about = about;
        this.profile = profile;
        this.enabled = enabled;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private boolean enabled=true;

    public User(){

    }

}

//Explanations
//@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "user")
//@JsonIgnore
//private Set<UserRole> userRoles = new HashSet<>();
//    The @OneToMany annotation in this case is used to indicate a one-to-many relationship between two entities,
//    where the parent entity is a User and the child entity is a UserRole.

//    The cascade = CascadeType.ALL parameter indicates that any operation performed on the parent User entity,
//    such as insertion, update, or deletion, should also be propagated to the child UserRole entities.
//
//    The fetch = FetchType.EAGER parameter indicates that the child UserRole entities should be eagerly fetched from
//    the database when the parent User entity is loaded. This means that all UserRole entities associated with a User
//    entity will be loaded at once, even if they are not immediately needed, which can potentially impact performance.
//
//    The mappedBy = "user" parameter indicates that the User entity is the inverse side of the relationship,
//    and the child UserRole entity is the owner side. This means that the UserRole entity is responsible for managing
//    the relationship with the parent User entity.
//
//    The @JsonIgnore annotation is used to exclude the userRoles field from serialization and deserialization.
//    This is commonly used when you want to avoid infinite recursion in JSON serialization, where a User object would
//    contain a Set of UserRole objects which in turn contain a reference back to the User object, resulting in an infinite loop.
//
//    Overall, this annotation and its parameters help define the behavior of the relationship between
//    the User and UserRole entities, and how they should be retrieved and persisted in the database, and
//    how they should be serialized and deserialized in JSON.

