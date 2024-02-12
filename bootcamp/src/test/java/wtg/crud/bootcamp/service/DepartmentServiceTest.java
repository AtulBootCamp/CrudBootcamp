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

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        departmentService=new DepartmentServiceImpl(departmentRepository);
    }

    @Test
    public void testAddDepartment(){

        Department department=new Department();
        department.setId(1);
        department.setDepttName("HR");
        department.setReadOnly(false);
        department.setMandatory(false);

        when(departmentRepository.save(department)).thenReturn(department);

        Department savedDepartment = departmentService.addDepartment(department);

        assertThat(savedDepartment).isNotNull();
        assertEquals(department, savedDepartment);

    }

    @Test
    public void testGetDepartment(){
        Department department=new Department();
        department.setId(1);
        department.setDepttName("HR");
        department.setReadOnly(false);
        department.setMandatory(false);

        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));

        Department savedDepartment = departmentService.getDepartmentById(department.getId());

        assertEquals(department,savedDepartment);
    }

    @Test
    public void testGivenDepartmentNotFound(){
        Department department=new Department();
        department.setId(1);
        department.setDepttName("HR");
        department.setReadOnly(false);
        department.setMandatory(false);

        when(departmentRepository.findById(department.getId())).thenReturn(Optional.empty());

        assertThrows(DepartmentNotFoundException.class,()->departmentService.getDepartmentById(department.getId()));

    }

    @Test
    public void testDeleteDepartment(){
        Department department=new Department();
        department.setId(1);
        department.setDepttName("HR");
        department.setReadOnly(false);
        department.setMandatory(false);

        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));

        departmentService.deleteDepartment(department.getId());

        verify(departmentRepository,times(1)).deleteById(department.getId());
    }

    @Test
    public void testDeleteDepartmentNotFound(){
        Department department=new Department();
        department.setId(1);

        when(departmentRepository.findById(department.getId())).thenReturn(Optional.empty());

        assertThrows(DepartmentNotFoundException.class,()->departmentService.deleteDepartment(department.getId()));

        verify(departmentRepository,never()).deleteById(department.getId());
    }

    @Test
    public void testDeleteDepartmentReadOnly(){
        Department department=new Department();
        department.setId(1);
        department.setReadOnly(true);

        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        assertThrows(DepartmentIsReadOnlyException.class,()->departmentService.deleteDepartment(department.getId()));

        verify(departmentRepository,never()).deleteById(department.getId());
    }

    @Test
    public void testUpdateDepartment(){
        Department department=new Department();
        department.setId(1);
        department.setDepttName("HR");
        department.setReadOnly(false);
        department.setMandatory(false);

        when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        when(departmentRepository.save(department)).thenReturn(department);
        Department savedDepartment = departmentService.updateDepartment(department.getId(),department);

        assertEquals(department,savedDepartment);
    }

    @Test
    public void testGetAllDepartments(){
        Department departmentHR=new Department();
        departmentHR.setId(1);
        departmentHR.setDepttName("HR");
        departmentHR.setReadOnly(false);
        departmentHR.setMandatory(false);

        Department departmentOrganisation=new Department();
        departmentOrganisation.setId(1);
        departmentOrganisation.setDepttName("Organisation");
        departmentOrganisation.setReadOnly(true);
        departmentOrganisation.setMandatory(true);

        List<Department> departmentList = List.of(departmentHR, departmentOrganisation);

        when(departmentRepository.findAll()).thenReturn(departmentList);

        List<Department> allDepartments = departmentService.getAllDepartments();

        assertThat(allDepartments).isEqualTo(departmentList);
        verify(departmentRepository,times(1)).findAll();


    }
}
