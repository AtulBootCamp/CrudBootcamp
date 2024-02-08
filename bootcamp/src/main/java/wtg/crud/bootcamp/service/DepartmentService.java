package wtg.crud.bootcamp.service;

import org.springframework.stereotype.Service;
import wtg.crud.bootcamp.exceptions.DepartmentNotFoundException;
import wtg.crud.bootcamp.model.Department;
import wtg.crud.bootcamp.model.Employee;
import wtg.crud.bootcamp.repository.DepartmentRepository;

import java.util.Optional;

@Service
public class DepartmentService {


    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public void addDepartment(Department department) {
        this.departmentRepository.save(department);
    }

    public Department getDepartmentById(Integer id) {
        return departmentRepository.findById(id).orElse(null);
    }

    public void deleteDepartment(Integer id) {
        departmentRepository.deleteById(id);
    }

    public Department updateDepartment(Integer departmentId, Department modifieddepartment){
        Department departmentInDB =this.departmentRepository.findById(departmentId).get();

        departmentInDB.setDepttName(modifieddepartment.getDepttName());
        departmentInDB.setIsReadOnly(modifieddepartment.getIsReadOnly());
        departmentInDB.setIsMandatory(modifieddepartment.getIsMandatory());


        return this.departmentRepository.save(departmentInDB);

    }



}
