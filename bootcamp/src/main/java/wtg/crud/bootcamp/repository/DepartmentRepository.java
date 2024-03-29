package wtg.crud.bootcamp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wtg.crud.bootcamp.model.Department;

import java.util.List;
import java.util.Set;

@Repository
public interface DepartmentRepository extends CrudRepository<Department,Integer> {

    Set<Department> findDepartmentsByMandatory(Boolean flag);
}
