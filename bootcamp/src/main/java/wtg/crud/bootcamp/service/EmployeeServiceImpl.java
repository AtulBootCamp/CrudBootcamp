package wtg.crud.bootcamp.service;

import org.springframework.stereotype.Service;
import wtg.crud.bootcamp.exceptions.EmployeeNotFoundException;
import wtg.crud.bootcamp.model.Department;
import wtg.crud.bootcamp.model.Employee;
import wtg.crud.bootcamp.repository.DepartmentRepository;
import wtg.crud.bootcamp.repository.EmployeeDepartmentRepository;
import wtg.crud.bootcamp.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    private final EmployeeDepartmentRepository employeeDepartmentRepository;
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               DepartmentRepository departmentRepository,
                               EmployeeDepartmentRepository employeeDepartmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository=departmentRepository;
        this.employeeDepartmentRepository=employeeDepartmentRepository;

    }

    @Override
    public Employee getEmployeeById(Integer employeeId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if(employeeOptional.isEmpty())
            throw new EmployeeNotFoundException("Employee Id "+ employeeId + " is not valid");
        return employeeOptional.get();

    }

    @Override
    public Employee createEmployee(Employee employee) {

        Set<Department> departments = employee.getDepartments();

        List<Department> allDepartments = (List<Department>) departmentRepository.findAll();

        Set<Department> mandatoryDepartments=allDepartments.stream().filter(Department::getMandatory).collect(Collectors.toSet());
        departments.addAll(mandatoryDepartments);

        Employee savedEmployee = employeeRepository.save(employee);

        return savedEmployee;
    }

    @Override
    public void deleteEmployee(Integer employeeId) {
        Optional<Employee> employeeOptional = this.employeeRepository.findById(employeeId);
        if(employeeOptional.isEmpty())
            throw new EmployeeNotFoundException("Employee Id "+ employeeId + " is not valid");

        this.employeeRepository.deleteById(employeeId);
    }

    @Override
    public Employee updateEmployee(Integer employeeId, Employee modifiedEmployee){

        Optional<Employee> employeeOptional = this.employeeRepository.findById(employeeId);

        if(employeeOptional.isEmpty())
            throw new EmployeeNotFoundException("Employee Id "+ employeeId + " is not valid");

        return this.employeeRepository.save(modifiedEmployee);

    }

    @Override
    public List<Employee> getAllEmployees(){
        return (List<Employee>) this.employeeRepository.findAll();
    }
}
