package wtg.crud.bootcamp.service;

import org.assertj.core.api.*;
import org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import wtg.crud.bootcamp.exceptions.DepartmentIsReadOnlyException;
import wtg.crud.bootcamp.exceptions.DepartmentNotFoundException;
import wtg.crud.bootcamp.model.Department;
import wtg.crud.bootcamp.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;
@SpringBootTest
public class DepartmentServiceTest {

    private final Integer ID_ONE=1;
    private final String DEPARTMENT_ORGANISATION="Organisation";

    private final Integer ID_TWO=2;
    private final String DEPARTMENT_IT="IT";

    private final Integer ID_THREE=3;
    private final String DEPARTMENT_HR="HR";
    private final Boolean READ_ONLY_TRUE=true;
    private final Boolean MANDATORY_TRUE=true;

    private final Boolean READ_ONLY_FALSE=false;
    private final Boolean MANDATORY_FALSE=false;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DepartmentService ref;
    @InjectMocks
    private DepartmentServiceImpl concreateRef;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        concreateRef=new DepartmentServiceImpl(departmentRepository);
        ref=concreateRef;
    }

    @Test
    public void test_whenAddDepartment_success(){
        Department department = DepartmentFactory.createDepartment(ID_ONE,DEPARTMENT_ORGANISATION,READ_ONLY_TRUE,MANDATORY_TRUE);

        when(departmentRepository.save(department)).thenReturn(department);

        Department savedDepartment = ref.addDepartment(department);

        assertThat(savedDepartment).isNotNull();
        assertEquals(department, savedDepartment);

    }

    @Test
    public void test_whenGetDepartment_success(){
        Department department = DepartmentFactory.createDepartment(ID_TWO,DEPARTMENT_IT,READ_ONLY_FALSE,MANDATORY_FALSE);

        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));

        Department savedDepartment = ref.getDepartmentById(department.getId());

        assertEquals(department,savedDepartment);
    }

    @Test
    public void test_whenGiven_departmentId_IsWrong_fail(){
        Department department = DepartmentFactory.createDepartment(ID_THREE,DEPARTMENT_HR,READ_ONLY_FALSE,MANDATORY_FALSE);

        when(departmentRepository.findById(department.getId())).thenReturn(Optional.empty());

        assertThrows(DepartmentNotFoundException.class,()->ref.getDepartmentById(department.getId()));

    }

    @Test
    public void test_when_deleteDepartment_success(){
        Department department = DepartmentFactory.createDepartment(ID_TWO,DEPARTMENT_IT,READ_ONLY_FALSE,READ_ONLY_TRUE);

        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));

        ref.deleteDepartment(department.getId());

        verify(departmentRepository,times(1)).deleteById(department.getId());
    }

    @Test
    public void test_when_deleteDepartmentId_IsWrong_fail(){
        Department department=new Department();
        department.setId(ID_THREE);

        when(departmentRepository.findById(department.getId())).thenReturn(Optional.empty());

        assertThrows(DepartmentNotFoundException.class,()->ref.deleteDepartment(department.getId()));

        verify(departmentRepository,never()).deleteById(department.getId());
    }

    @Test
    public void test_when_deleteDepartment_IsReadOnly_fail(){
        Department department = DepartmentFactory.createDepartment(ID_ONE,DEPARTMENT_ORGANISATION,READ_ONLY_TRUE,MANDATORY_TRUE);

        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));

        assertThrows(DepartmentIsReadOnlyException.class,()->ref.deleteDepartment(department.getId()));

        verify(departmentRepository,never()).deleteById(department.getId());
    }

    @Test
    public void test_when_updateDepartment_success(){
        Department department = DepartmentFactory.createDepartment(ID_THREE,DEPARTMENT_HR,READ_ONLY_FALSE,MANDATORY_FALSE);

        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        when(departmentRepository.save(department)).thenReturn(department);

        Department savedDepartment = ref.updateDepartment(department.getId(),department);

        assertEquals(department,savedDepartment);
    }

    @Test
    public void test_whenGetAllDepartments_success(){
        Department departmentIT = DepartmentFactory.createDepartment(ID_TWO,DEPARTMENT_IT,READ_ONLY_FALSE,MANDATORY_FALSE);
        Department departmentHR = DepartmentFactory.createDepartment(ID_THREE,DEPARTMENT_HR,READ_ONLY_FALSE,MANDATORY_FALSE);

        List<Department> departmentList = List.of(departmentHR, departmentIT);

        when(departmentRepository.findAll()).thenReturn(departmentList);

        List<Department> allDepartments = ref.getAllDepartments();

        assertThat(allDepartments).isEqualTo(departmentList);
        verify(departmentRepository,times(1)).findAll();
    }
}
