package wtg.crud.bootcamp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wtg.crud.bootcamp.model.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee,Integer> {
}
