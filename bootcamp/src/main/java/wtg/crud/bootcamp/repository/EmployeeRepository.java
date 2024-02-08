package wtg.crud.bootcamp.repository;

import org.springframework.data.repository.CrudRepository;
import wtg.crud.bootcamp.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee,Integer> {
}
