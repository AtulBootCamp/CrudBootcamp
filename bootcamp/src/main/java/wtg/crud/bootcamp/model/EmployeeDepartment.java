package wtg.crud.bootcamp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "map_employee_department")
public class EmployeeDepartment {

    @EmbeddedId
    private EmployeeDepartmentMappingId id;

    @ManyToOne
    @MapsId("employee_id")
    private Employee employee;

    @ManyToOne
    @MapsId("department_id")
    private Department department;

    public EmployeeDepartment() {

    }

}
