package wtg.crud.bootcamp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import wtg.crud.bootcamp.exceptions.EmployeeNotFoundException;
import wtg.crud.bootcamp.model.Department;
import wtg.crud.bootcamp.model.Employee;
import wtg.crud.bootcamp.repository.DepartmentRepository;
import wtg.crud.bootcamp.repository.EmployeeDepartmentRepository;
import wtg.crud.bootcamp.repository.EmployeeRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private EmployeeDepartmentRepository employeeDepartmentRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        employeeService=new EmployeeServiceImpl(employeeRepository,departmentRepository,employeeDepartmentRepository);
    }

    @Test
    public void testCreateEmployee(){
        Employee employee=new Employee();
        employee.setId(1);
        employee.setEmpName("Ram");

        Set<Department> departments=new HashSet<>();

        Department departmentIt=new Department();
        departmentIt.setDepttName("IT");
        departmentIt.setReadOnly(false);
        departmentIt.setMandatory(false);

        Department departmentOrganisation=new Department();
        departmentOrganisation.setDepttName("Organisation");
        departmentOrganisation.setReadOnly(true);
        departmentOrganisation.setMandatory(true);

        List<Department> allDepartments = List.of(departmentIt, departmentOrganisation);
        when(departmentRepository.findAll()).thenReturn(allDepartments);

        departments.addAll(allDepartments);
        employee.setDepartments(departments);

        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee savedEmployee = employeeService.createEmployee(employee);

        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee).isEqualTo(employee);
    }

    @Test
    public void testGetEmployee(){
        Employee employee=new Employee();
        employee.setId(1);
        employee.setEmpName("Ram");

        Set<Department> departments=new HashSet<>();

        Department departmentIt=new Department();
        departmentIt.setDepttName("IT");
        departmentIt.setReadOnly(false);
        departmentIt.setMandatory(false);

        employee.setDepartments(departments);

        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        Employee savedEmployee = employeeService.getEmployeeById(employee.getId());

        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee).isEqualTo(employee);

    }

    @Test
    public void testGetEmployeeNotFound(){
        Employee employee=new Employee();
        employee.setId(1);
        employee.setEmpName("Ram");

        Set<Department> departments=new HashSet<>();

        Department departmentIt=new Department();
        departmentIt.setDepttName("IT");
        departmentIt.setReadOnly(false);
        departmentIt.setMandatory(false);

        employee.setDepartments(departments);

        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class,()->employeeService.getEmployeeById(employee.getId()));
    }

    @Test
    public void testDeleteEmployee(){
        Employee employee=new Employee();
        employee.setId(1);
        employee.setEmpName("Ram");

        Set<Department> departments=new HashSet<>();

        Department departmentIt=new Department();
        departmentIt.setDepttName("IT");
        departmentIt.setReadOnly(false);
        departmentIt.setMandatory(false);

        employee.setDepartments(departments);

        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        employeeService.deleteEmployee(employee.getId());

        verify(employeeRepository,times(1)).deleteById(employee.getId());

    }

    @Test
    public void testDeleteEmployeeNotFound(){
        Employee employee=new Employee();
        employee.setId(1);

        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class,()->employeeService.deleteEmployee(employee.getId()));

        verify(employeeRepository,never()).deleteById(employee.getId());
    }

    @Test
    public void testUpdateEmployee(){
        Employee modifiedEmployee=new Employee();
        modifiedEmployee.setEmpName("Atul");

        Employee existingEmployee=new Employee();
        existingEmployee.setId(1);


        when(employeeRepository.findById(existingEmployee.getId())).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(existingEmployee)).thenReturn(existingEmployee);

        Employee updatedEmployee = employeeService.updateEmployee(existingEmployee.getId(), modifiedEmployee);

        assertThat(modifiedEmployee.getEmpName()).isEqualTo(updatedEmployee.getEmpName());
        verify(employeeRepository,times(1)).findById(1);
        verify(employeeRepository,times(1)).save(existingEmployee);
        assertEquals(existingEmployee,updatedEmployee);


    }

    @Test
    public void testUpdateEmployeeNotFound(){
        Employee modifiedEmployee=new Employee();
        modifiedEmployee.setId(1);

        when(employeeRepository.findById(modifiedEmployee.getId())).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class,()->employeeService.updateEmployee(modifiedEmployee.getId(),modifiedEmployee));

        verify(employeeRepository,never()).save(modifiedEmployee);

    }


    @Test
    public void testGetAllEmployees(){
        Employee employeeRam=new Employee();
        employeeRam.setId(1);
        employeeRam.setEmpName("Ram");

        Employee employeeSham=new Employee();
        employeeSham.setId(2);
        employeeSham.setEmpName("Sham");

        Employee employeeSita=new Employee();
        employeeSita.setId(3);
        employeeSita.setEmpName("Sita");

        List<Employee> employeeList = List.of(employeeSham, employeeSita, employeeRam);

        when(employeeRepository.findAll()).thenReturn(employeeList);

        List<Employee> allEmployees = employeeService.getAllEmployees();

        assertThat(allEmployees).isEqualTo(employeeList);
        verify(employeeRepository,times(1)).findAll();
    }

}
