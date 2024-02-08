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

    public Employee getEmployeeById(Integer id) {
        return employeeRepository.findById(id).orElse(null);

    }

    public void createEmployee(Employee employee) {

        Employee savedEmployee = employeeRepository.save(employee);

        Department mandatoryDepartment = this.departmentRepository.findByIsMandatory(true);
        System.out.println(mandatoryDepartment);

        EmployeeDepartment mandatoryEmployeeDepartments=new EmployeeDepartment(employee,mandatoryDepartment);

        Set<EmployeeDepartment> employeeDepartments = employee.getEmployeeDepartments();
        employeeDepartments.add(mandatoryEmployeeDepartments);

        for (EmployeeDepartment employeeDepartment : employeeDepartments) {

            employeeDepartment.setEmployee(savedEmployee);
            employeeDepartmentRepository.save(employeeDepartment);
        }

    }

    public void deleteEmployee(Integer id) {
        Employee employee = this.employeeRepository.findById(id).get();
        List<EmployeeDepartment> employeeDepartments = this.employeeDepartmentRepository.findByEmployee(employee);

        this.employeeDepartmentRepository.deleteAll(employeeDepartments);
        employeeRepository.deleteById(id);
    }

    public void updateEmployee(Integer id, Employee modifiedEmployee){
        Employee employeeInDB = this.employeeRepository.findById(id).get();

        employeeInDB.setEmpName(modifiedEmployee.getEmpName());
        employeeInDB.setEmployeeDepartments(modifiedEmployee.getEmployeeDepartments());

        this.employeeRepository.save(employeeInDB);

    }
}
