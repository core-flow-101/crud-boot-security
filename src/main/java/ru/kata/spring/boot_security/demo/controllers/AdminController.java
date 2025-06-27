package ru.kata.spring.boot_security.demo.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.kata.spring.boot_security.demo.entities.Department;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.DepartmentServiceImpl;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final DepartmentServiceImpl departmentService;

    private final RoleService roleService;


    @GetMapping()
    public String showUsers(Model model, Principal principal) {
        List<User> allUsers = userService.getUsersList();
        List<Department> allDepartments = departmentService.getAllDepartments();
        for (User u : allUsers) {
            if (u.getPhoto() != null && u.getPhoto().length > 0) {
                String base64 = Base64.getEncoder().encodeToString(u.getPhoto());
                u.setPhotoBase64(base64);
            }
        }
        model.addAttribute("allDepartments", allDepartments);
        model.addAttribute("newUser", new User());
        model.addAttribute("allUsers", allUsers);
        model.addAttribute("currentUser", userService.getUserByName(principal.getName()));
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "admin";
    }

    @PostMapping("/new")
    public String createNewUser(@RequestParam("username") String username,
                                @RequestParam("surname") String surname,
                                @RequestParam("middleName") String middleName,
                                @RequestParam("email") String email,
                                @RequestParam("password") String password,
                                @RequestParam("selectedRole") String role,
                                @RequestParam("department") String departmentName,
                                @RequestParam("birthday") String birthday,
                                @RequestParam("photo") MultipartFile photoFile) {
        User user = User.builder()
                .username(username)
                .surname(surname)
                .middleName(middleName)
                .email(email)
                .password(password)
                .birthday(LocalDate.parse(birthday))
                .build();
        userService.saveUser(user, role, departmentName, photoFile);

        return "redirect:/admin";
    }


    @PatchMapping("/update")
    public String createUser(
                             @RequestParam("id") Long id,
                             @RequestParam("username") String username,
                             @RequestParam("surname") String surname,
                             @RequestParam("middleName") String middleName,
                             @RequestParam("birthday") String birthday,
                             @RequestParam("email") String email,
                             @RequestParam("department") String departmentName,
                             @RequestParam("photo") MultipartFile photo,
                             @RequestParam("password") String password,
                             @RequestParam("updatedRole") String role
    ) {
        User user = userService.getUserById(id);
        user.setUsername(username);
        user.setSurname(surname);
        user.setMiddleName(middleName);
        user.setEmail(email);
        user.setBirthday(LocalDate.parse(birthday));
        userService.updateUser(user, role, departmentName, photo);
        return "redirect:/admin";
    }


    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}
