package wtg.crud.bootcamp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wtg.crud.bootcamp.model.Department;

@Repository
public interface DepartmentRepository extends CrudRepository<Department,Integer> {

//    public Department findByDepttName(String depttName);

    public Department findByIsMandatory(Boolean isMandatory);
    public Department findByIsReadOnly(Boolean isReadOnly);
}
