package wtg.crud.bootcamp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wtg.crud.bootcamp.exceptions.DepartmentIsReadOnlyException;
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

    public Department getDepartmentById(Integer departmentId) {
        checkIfValidDepartment(departmentId);
        return departmentRepository.findById(departmentId).get();
    }

    public void deleteDepartment(Integer departmentId) {

        //check if department id is valid or not
        checkIfValidDepartment(departmentId);

        //check if department is read-only, then we can't delete
        checkIfReadOnlyDepartment(departmentId);
        departmentRepository.deleteById(departmentId);
    }

    public Department updateDepartment(Integer departmentId, Department modifieddepartment){

        checkIfValidDepartment(departmentId);
        checkIfReadOnlyDepartment(departmentId);
        //check if department is read-only, then we can't update
        

        Department departmentInDB =this.departmentRepository.findById(departmentId).get();

        departmentInDB.setDepttName(modifieddepartment.getDepttName());
        departmentInDB.setIsReadOnly(modifieddepartment.getIsReadOnly());
        departmentInDB.setIsMandatory(modifieddepartment.getIsMandatory());

        return this.departmentRepository.save(departmentInDB);
    }

    private void checkIfValidDepartment(Integer departmentId) {
        Optional<Department> departmentOptional = this.departmentRepository.findById(departmentId);
        if(departmentOptional.isEmpty())
            throw new DepartmentNotFoundException("Department Id "+ departmentId + " is not valid");
    }

    private void checkIfReadOnlyDepartment(Integer departmentId) {
        Department readOnlyDepartment = this.departmentRepository.findByIsReadOnly(true);
        if(readOnlyDepartment.getIsReadOnly())
            throw new DepartmentIsReadOnlyException("This department is Read-only, can't be deleted");
    }
}
