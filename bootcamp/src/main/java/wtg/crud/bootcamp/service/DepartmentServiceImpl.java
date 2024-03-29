package wtg.crud.bootcamp.service;

import org.springframework.stereotype.Service;
import wtg.crud.bootcamp.exceptions.DepartmentIsReadOnlyException;
import wtg.crud.bootcamp.exceptions.DepartmentNotFoundException;
import wtg.crud.bootcamp.model.Department;
import wtg.crud.bootcamp.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService{
    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Department addDepartment(Department department) {
        return this.departmentRepository.save(department);
    }

    @Override
    public Department getDepartmentById(Integer departmentId) {
        return this.departmentRepository.
                findById(departmentId).
                orElseThrow(() -> new DepartmentNotFoundException("Department Id " + departmentId + " is not valid"));
    }

    @Override
    public void deleteDepartment(Integer departmentId) {
        Department department = this.departmentRepository.
                findById(departmentId).
                orElseThrow(() -> new DepartmentNotFoundException("Department Id " + departmentId + " is not valid"));

        if(department.getReadOnly()){
            throw new DepartmentIsReadOnlyException("This department is Read-only, can't be deleted");
        }

        this.departmentRepository.deleteById(departmentId);
    }

    @Override
    public Department updateDepartment(Integer departmentId, Department modifieddepartment){

        Department departmentInDB = this.departmentRepository.
                findById(departmentId).
                orElseThrow(() -> new DepartmentNotFoundException("Department Id " + departmentId + " is not valid"));

        if(departmentInDB.getReadOnly()){
            throw new DepartmentIsReadOnlyException("This department is Read-only, can't be updated");
        }
        departmentInDB.setDepartmentName(modifieddepartment.getDepartmentName());
        departmentInDB.setMandatory(modifieddepartment.getMandatory());
        departmentInDB.setReadOnly(modifieddepartment.getReadOnly());

        return this.departmentRepository.save(modifieddepartment);
    }

    @Override
    public List<Department> getAllDepartments(){
        return (List<Department>) this.departmentRepository.findAll();
    }


}
