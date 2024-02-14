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
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "department_sequence_generator")
	@SequenceGenerator(name = "department_sequence_generator",initialValue = 1,sequenceName = "department_sequence",allocationSize = 1)
	private Integer id;

	@Column(name = "name")
	private String departmentName;

	@Column(name = "read_only")
	private Boolean readOnly;

	@Column(name = "mandatory")
	private Boolean mandatory;

	@ManyToMany(mappedBy = "departments",cascade={CascadeType.MERGE})
	@JsonIgnore
	private Set<Employee> employee = new HashSet<>();
}
