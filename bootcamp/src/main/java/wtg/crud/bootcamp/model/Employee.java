package wtg.crud.bootcamp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@Column(name = "employee_name")
	private String empName;

	@ManyToMany(cascade = {CascadeType.MERGE},fetch = FetchType.LAZY)
	@JoinTable(name = "map_employee_department",
			joinColumns = @JoinColumn(name = "employee_id"),
			inverseJoinColumns = @JoinColumn(name = "department_id"))
	private Set<Department> departments = new HashSet<>();
}
