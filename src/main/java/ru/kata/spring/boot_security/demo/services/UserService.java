package ru.kata.spring.boot_security.demo.services;

import org.springframework.web.multipart.MultipartFile;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

public interface UserService {

    User getUserByName(String username);

    List<User> getUsersList();

    void saveUser(User user, String role, String departmentName, MultipartFile file);

    void deleteUserById(Long id);

    User getUserById(Long id);

    void updateUser(User user, String role, String departmentName, MultipartFile file);
}
