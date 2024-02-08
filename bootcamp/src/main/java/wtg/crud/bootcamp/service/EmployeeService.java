package wtg.crud.bootcamp.service;

import org.springframework.stereotype.Service;
import wtg.crud.bootcamp.exceptions.EmployeeNotFoundException;
import wtg.crud.bootcamp.model.Department;
import wtg.crud.bootcamp.model.Employee;
import wtg.crud.bootcamp.model.EmployeeDepartment;
import wtg.crud.bootcamp.repository.DepartmentRepository;
import wtg.crud.bootcamp.repository.EmployeeDepartmentRepository;
import wtg.crud.bootcamp.repository.EmployeeRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.StreamSupport;
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeDepartmentRepository employeeDepartmentRepository;

    private final DepartmentRepository departmentRepository;

    public EmployeeService(EmployeeRepository employeeRepository,
                           EmployeeDepartmentRepository employeeDepartmentRepository,
                           DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeDepartmentRepository=employeeDepartmentRepository;
        this.departmentRepository=departmentRepository;

    }

    public Employee getEmployeeById(Integer employeeId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if(employeeOptional.isEmpty())
            throw new EmployeeNotFoundException("Employee Id "+ employeeId + " is not valid");
        return employeeOptional.get();

    }

    public void createEmployee(Employee employee) {

        Employee savedEmployee = employeeRepository.save(employee);

        Department mandatoryDepartment = this.departmentRepository.findByIsMandatory(true);

        EmployeeDepartment mandatoryEmployeeDepartments=new EmployeeDepartment(employee,mandatoryDepartment);

        Set<EmployeeDepartment> employeeDepartments = employee.getEmployeeDepartments();
        employeeDepartments.add(mandatoryEmployeeDepartments);

        for (EmployeeDepartment employeeDepartment : employeeDepartments) {

            employeeDepartment.setEmployee(savedEmployee);
            employeeDepartmentRepository.save(employeeDepartment);
        }

    }

    public void deleteEmployee(Integer employeeId) {
        Optional<Employee> employeeOptional = this.employeeRepository.findById(employeeId);
        if(employeeOptional.isEmpty())
            throw new EmployeeNotFoundException("Employee Id "+ employeeId + " is not valid");
        List<EmployeeDepartment> employeeDepartments = this.employeeDepartmentRepository.findByEmployee(employeeOptional.get());

        this.employeeDepartmentRepository.deleteAll(employeeDepartments);
        employeeRepository.deleteById(employeeId);
    }

    public void updateEmployee(Integer employeeId, Employee modifiedEmployee){
        
        this.employeeRepository.findById(employeeId);
        
        Employee employeeInDB = this.employeeRepository.findById(employeeId).get();

        employeeInDB.setEmpName(modifiedEmployee.getEmpName());
        employeeInDB.setEmployeeDepartments(modifiedEmployee.getEmployeeDepartments());

        this.employeeRepository.save(employeeInDB);

    }
}
