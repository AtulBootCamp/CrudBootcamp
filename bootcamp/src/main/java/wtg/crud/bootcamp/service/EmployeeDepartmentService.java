package wtg.crud.bootcamp.service;

import wtg.crud.bootcamp.model.EmployeeDepartment;
import wtg.crud.bootcamp.repository.EmployeeDepartmentRepository;

import java.util.List;

public class EmployeeDepartmentService {
    private final EmployeeDepartmentRepository employeeDepartmentRepository;

    public EmployeeDepartmentService(EmployeeDepartmentRepository employeeDepartmentRepository) {
        this.employeeDepartmentRepository = employeeDepartmentRepository;
    }

    public List<EmployeeDepartment> getAllEmployeeDepartments() {
        return (List<EmployeeDepartment>) employeeDepartmentRepository.findAll();
    }

    public EmployeeDepartment getEmployeeDepartmentById(Integer id) {
        return employeeDepartmentRepository.findById(id).orElse(null);
    }

    public EmployeeDepartment saveEmployeeDepartment(EmployeeDepartment employeeDepartment) {
        return employeeDepartmentRepository.save(employeeDepartment);
    }

    public void deleteEmployeeDepartment(Integer id) {
        employeeDepartmentRepository.deleteById(id);
    }
}
