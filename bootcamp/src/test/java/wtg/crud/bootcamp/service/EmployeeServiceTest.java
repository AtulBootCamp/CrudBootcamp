package wtg.crud.bootcamp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wtg.crud.bootcamp.exceptions.EmployeeNotFoundException;
import wtg.crud.bootcamp.model.Department;
import wtg.crud.bootcamp.model.Employee;
import wtg.crud.bootcamp.repository.DepartmentRepository;
import wtg.crud.bootcamp.repository.EmployeeRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    public final Integer ID_ONE=1;
    public final Integer ID_TWO=2;
    public final Integer ID_THREE=3;

    public final Integer ID_FOUR=4;

    public final String EMPLOYEE_RAM="RAM";
    public final String EMPLOYEE_SHAM="SHAM";
    public final String EMPLOYEE_SITA="SITA";
    public final String EMPLOYEE_GITA="GITA";

    private final Integer DEPARTMENT_ID_ONE=1;
    private final String DEPARTMENT_ORGANISATION="Organisation";

    private final Integer DEPARTMENT_ID_TWO=2;
    private final String DEPARTMENT_IT="IT";

    private final Integer DEPARTMENT_ID_THREE=3;
    private final String DEPARTMENT_HR="HR";
    private final Boolean READ_ONLY_TRUE=true;
    private final Boolean MANDATORY_TRUE=true;

    private final Boolean READ_ONLY_FALSE=false;
    private final Boolean MANDATORY_FALSE=false;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;


    private EmployeeService underTest;

    @InjectMocks
    private EmployeeServiceImpl concreateRef;

    @BeforeEach
    public void setUp(){
        concreateRef=new EmployeeServiceImpl(employeeRepository,departmentRepository);
        underTest=concreateRef;
    }

    @Test
    public void test_when_createEmployee_success(){

        Department departmentOrganisation = DepartmentFactory.createDepartment(DEPARTMENT_ID_ONE, DEPARTMENT_ORGANISATION, READ_ONLY_TRUE, MANDATORY_TRUE);
        Department departmentIT = DepartmentFactory.createDepartment(DEPARTMENT_ID_TWO, DEPARTMENT_IT, READ_ONLY_FALSE, MANDATORY_FALSE);
        Department departmentHR = DepartmentFactory.createDepartment(DEPARTMENT_ID_THREE, DEPARTMENT_HR, READ_ONLY_FALSE, MANDATORY_FALSE);
        Set<Department> departments=new HashSet<>(List.of(departmentHR,departmentIT,departmentOrganisation));

        Employee employee = EmployeeFactory.createEmployee(ID_ONE, EMPLOYEE_RAM, departments);

        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee savedEmployee = underTest.createEmployee(employee);

        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee).isEqualTo(employee);
    }

    @Test
    public void test_when_getEmployee_Success(){

        Department departmentIT = DepartmentFactory.createDepartment(DEPARTMENT_ID_TWO, DEPARTMENT_IT, READ_ONLY_FALSE, MANDATORY_FALSE);
        Department departmentHR = DepartmentFactory.createDepartment(DEPARTMENT_ID_THREE, DEPARTMENT_HR, READ_ONLY_FALSE, MANDATORY_FALSE);

        Set<Department> departments=new HashSet<>();
        departments.addAll(List.of(departmentIT,departmentHR));

        Employee employee = EmployeeFactory.createEmployee(ID_TWO, EMPLOYEE_SHAM, departments);

        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        Employee savedEmployee = underTest.getEmployeeById(employee.getId());

        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee).isEqualTo(employee);

    }

    @Test
    public void test_when_getEmployee_idWrong_fail(){

        Employee employee = EmployeeFactory.createEmployee(ID_THREE, EMPLOYEE_SITA,new HashSet<>());

        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class,()->underTest.getEmployeeById(employee.getId()));
    }

    @Test
    public void test_When_deleteEmployee_Success(){

        Department departmentIT = DepartmentFactory.createDepartment(DEPARTMENT_ID_TWO, DEPARTMENT_IT, READ_ONLY_FALSE, MANDATORY_FALSE);
        Department departmentHR = DepartmentFactory.createDepartment(DEPARTMENT_ID_THREE, DEPARTMENT_HR, READ_ONLY_FALSE, MANDATORY_FALSE);

        Employee employee = EmployeeFactory.createEmployee(ID_FOUR, EMPLOYEE_GITA, new HashSet<>(List.of(departmentIT, departmentHR)));

        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        underTest.deleteEmployee(employee.getId());

        verify(employeeRepository,times(1)).deleteById(employee.getId());

    }

    @Test
    public void test_when_deleteEmployee_idWrong_fail(){
        Employee employee = EmployeeFactory.createEmployee(ID_FOUR, EMPLOYEE_GITA, new HashSet<>());

        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class,()->underTest.deleteEmployee(employee.getId()));

        verify(employeeRepository,never()).deleteById(employee.getId());
    }

    @Test
    public void test_when_updateEmployee_success(){

        Employee modifiedEmployee = EmployeeFactory.createEmployee(ID_FOUR, EMPLOYEE_GITA, new HashSet<>());
        Employee existingEmployee = EmployeeFactory.createEmployee(ID_FOUR, EMPLOYEE_SITA, new HashSet<>());

        when(employeeRepository.findById(existingEmployee.getId())).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(existingEmployee)).thenReturn(existingEmployee);

        Employee updatedEmployee = underTest.updateEmployee(existingEmployee.getId(), modifiedEmployee);

        assertThat(modifiedEmployee.getEmpName()).isEqualTo(updatedEmployee.getEmpName());
        verify(employeeRepository,times(1)).findById(ID_FOUR);
        verify(employeeRepository,times(1)).save(existingEmployee);
        assertEquals(existingEmployee,updatedEmployee);


    }

    @Test
    public void test_when_updateEmployee_idWrong_fail(){
        Employee modifiedEmployee = EmployeeFactory.createEmployee(ID_FOUR, EMPLOYEE_GITA, new HashSet<>());

        when(employeeRepository.findById(modifiedEmployee.getId())).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class,()->underTest.updateEmployee(modifiedEmployee.getId(),modifiedEmployee));

        verify(employeeRepository,never()).save(modifiedEmployee);

    }


    @Test
    public void test_when_getAllEmployees_Success(){

        Employee employeeRam = EmployeeFactory.createEmployee(ID_ONE, EMPLOYEE_RAM, new HashSet<>());
        Employee employeeSham = EmployeeFactory.createEmployee(ID_TWO, EMPLOYEE_SHAM, new HashSet<>());
        Employee employeeSita = EmployeeFactory.createEmployee(ID_THREE, EMPLOYEE_SITA, new HashSet<>());

        List<Employee> employeeList = List.of(employeeSham, employeeSita, employeeRam);

        when(employeeRepository.findAll()).thenReturn(employeeList);

        List<Employee> allEmployees = underTest.getAllEmployees();

        assertThat(allEmployees).isEqualTo(employeeList);
        verify(employeeRepository,times(1)).findAll();
    }

}
