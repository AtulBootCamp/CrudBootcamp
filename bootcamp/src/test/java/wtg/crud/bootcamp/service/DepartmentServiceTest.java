package wtg.crud.bootcamp.service;

import org.assertj.core.api.*;
import org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import wtg.crud.bootcamp.exceptions.DepartmentNotFoundException;
import wtg.crud.bootcamp.model.Department;
import wtg.crud.bootcamp.repository.DepartmentRepository;

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



}
