package ru.kata.spring.boot_security.demo.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entities.Department;
import ru.kata.spring.boot_security.demo.repositories.DepartmentRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl {

    private final DepartmentRepository departmentRepository;
    public Department getDepartmentByName(String name) {
        return departmentRepository.findDepartmentByName(name)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
}
