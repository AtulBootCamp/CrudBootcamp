package wtg.crud.bootcamp.service;

import wtg.crud.bootcamp.model.Department;

import java.util.List;

public interface DepartmentService {

    public Department addDepartment(Department department);
    public Department getDepartmentById(Integer departmentId);
    public Department updateDepartment(Integer departmentId, Department modifieddepartment);
    public List<Department> getAllDepartments();
    public void deleteDepartment(Integer departmentId);
}
