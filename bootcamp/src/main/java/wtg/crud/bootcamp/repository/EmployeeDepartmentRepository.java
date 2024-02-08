package wtg.crud.bootcamp.repository;

import org.springframework.data.repository.CrudRepository;
import wtg.crud.bootcamp.model.Department;
import wtg.crud.bootcamp.model.Employee;
import wtg.crud.bootcamp.model.EmployeeDepartment;

import java.util.List;

public interface EmployeeDepartmentRepository extends CrudRepository<EmployeeDepartment,Integer> {

    public List<EmployeeDepartment> findByEmployee(Employee employee);

    public List<EmployeeDepartment> findByDepartment(Department department);
}
