package wtg.crud.bootcamp.service;

import wtg.crud.bootcamp.model.Employee;

import java.util.List;

public interface EmployeeService {

    public Employee getEmployeeById(Integer employeeId);
    public Employee createEmployee(Employee employee);
    public void deleteEmployee(Integer employeeId);
    public Employee updateEmployee(Integer employeeId, Employee modifiedEmployee);
    public List<Employee> getAllEmployees();
}
