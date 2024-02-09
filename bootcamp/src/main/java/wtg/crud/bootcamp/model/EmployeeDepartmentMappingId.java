package wtg.crud.bootcamp.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class EmployeeDepartmentMappingId implements Serializable {
    private Integer employee_id;
    private Integer department_id;
}
