package wtg.crud.bootcamp.service;

import wtg.crud.bootcamp.model.Employee;

import java.util.List;

public interface EmployeeService {

    Employee getEmployeeById(Integer employeeId);
    Employee createEmployee(Employee employee);
    void deleteEmployee(Integer employeeId);
    Employee updateEmployee(Integer employeeId, Employee modifiedEmployee);
    List<Employee> getAllEmployees();
}
