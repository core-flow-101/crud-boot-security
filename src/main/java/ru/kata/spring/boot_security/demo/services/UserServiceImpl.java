package ru.kata.spring.boot_security.demo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.kata.spring.boot_security.demo.entities.Department;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    private final DepartmentServiceImpl departmentService;


    @Override
    @Transactional
    public User getUserByName(String username) {
        return userRepository.findByUsername(username).get();
    }


    @Override
    public List<User> getUsersList() {
        return userRepository.findAll();
    }


    @Override
    @Transactional
    public void saveUser(User user, String role, String departmentName, MultipartFile file) {
        Department department = departmentService.getDepartmentByName(departmentName);
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (file != null && !file.isEmpty()) {
            try {
                user.setPhoto(file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Ошибка при загрузке фото", e);
            }
        }
        user.setRoles(modifyRole(role, user));
        user.setDepartment(department);
        userRepository.save(user);
    }


    @Override
    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public User getUserById(Long id) {
        return userRepository.getById(id).get();
    }

    @Override
    @Transactional
    public void updateUser(User updatedUser, String role, String departmentName, MultipartFile file) {
        updatedUser.setRoles(modifyRole(role, updatedUser));
        Department department = departmentService.getDepartmentByName(departmentName);
        updatedUser.setDepartment(department);
        if (file != null && !file.isEmpty()) {
            try {
                updatedUser.setPhoto(file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Ошибка при загрузке фото", e);
            }
        }
        userRepository.save(updatedUser);
    }

    private Set<Role> modifyRole(String role, User user) {
        List<Role> resultRoles = new ArrayList<>();

        resultRoles.add(roleService.findRoleByName(role));

        if (role.equals("ROLE_ADMIN")) {
            resultRoles.add(roleService.findRoleByName("ROLE_USER"));
        }

        for (Role role1: resultRoles) {
            role1.getUsers().add(user);
        }
        return new HashSet<>(resultRoles);
    }


}


