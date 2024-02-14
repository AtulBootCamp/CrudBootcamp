package wtg.crud.bootcamp.service;

import wtg.crud.bootcamp.model.Department;

import java.util.List;

public interface DepartmentService {

    Department addDepartment(Department department);
    Department getDepartmentById(Integer departmentId);
    Department updateDepartment(Integer departmentId, Department modifieddepartment);
    List<Department> getAllDepartments();
    void deleteDepartment(Integer departmentId);
}
