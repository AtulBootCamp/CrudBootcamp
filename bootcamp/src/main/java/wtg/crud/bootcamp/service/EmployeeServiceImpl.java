package wtg.crud.bootcamp.service;

import org.springframework.stereotype.Service;
import wtg.crud.bootcamp.exceptions.EmployeeNotFoundException;
import wtg.crud.bootcamp.model.Department;
import wtg.crud.bootcamp.model.Employee;
import wtg.crud.bootcamp.repository.DepartmentRepository;
import wtg.crud.bootcamp.repository.EmployeeRepository;
import java.util.List;
import java.util.Set;

@Service

public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;


    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               DepartmentRepository departmentRepository){
        this.employeeRepository = employeeRepository;
        this.departmentRepository=departmentRepository;
    }
    @Override
    public Employee createEmployee(Employee employee) {
        Set<Department> departments = employee.getDepartments();
        Set<Department> mandatoryDepartments = departmentRepository.findDepartmentsByMandatory(true);
        departments.addAll(mandatoryDepartments);
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Integer employeeId) {
        this.employeeRepository.findById(employeeId).
            orElseThrow(() -> new EmployeeNotFoundException("Employee Id " + employeeId + " is not valid"));
        this.employeeRepository.deleteById(employeeId);
    }

    @Override
    public Employee getEmployeeById(Integer employeeId) {
        return this.employeeRepository.findById(employeeId).
            orElseThrow(()->new EmployeeNotFoundException("Employee Id "+ employeeId + " is not valid"));
    }

    @Override
    public Employee updateEmployee(Integer employeeId, Employee modifiedEmployee){
        Employee employeeInDB = this.employeeRepository.findById(employeeId).
            orElseThrow(() -> new EmployeeNotFoundException("Employee Id " + employeeId + " is not valid"));

        employeeInDB.setEmpName(modifiedEmployee.getEmpName());
        return this.employeeRepository.save(employeeInDB);
    }
    @Override
    public List<Employee> getAllEmployees(){
        return (List<Employee>) this.employeeRepository.findAll();
    }
}
