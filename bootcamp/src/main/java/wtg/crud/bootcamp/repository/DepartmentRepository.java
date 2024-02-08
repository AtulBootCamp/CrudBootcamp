package wtg.crud.bootcamp.repository;

import org.springframework.data.repository.CrudRepository;
import wtg.crud.bootcamp.model.Department;

public interface DepartmentRepository extends CrudRepository<Department,Integer> {

//    public Department findByDepttName(String depttName);

    public Department findByIsMandatory(Boolean isMandatory);
}
