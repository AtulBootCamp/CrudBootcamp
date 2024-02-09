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
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@Column(name = "department_name")
	private String depttName;

	@Column(name = "read_only")
	private Boolean readOnly;

	@Column(name = "mandatory")
	private Boolean mandatory;

	@JsonIgnore
	@ManyToMany(mappedBy = "departments",cascade={CascadeType.MERGE,CascadeType.REMOVE})
	private Set<Employee> employee = new HashSet<>();


}
