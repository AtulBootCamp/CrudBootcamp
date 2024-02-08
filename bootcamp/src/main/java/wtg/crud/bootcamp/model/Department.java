package wtg.crud.bootcamp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "department_name")
	private String depttName;

	@Column(name = "is_read_only")
	private Boolean isReadOnly;

	@Column(name = "is_mandatory")
	private Boolean isMandatory;

	@OneToMany(mappedBy = "department",fetch = FetchType.EAGER)
	private Set<EmployeeDepartment> employeeDepartments = new HashSet<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDepttName() {
		return depttName;
	}

	public void setDepttName(String depttName) {
		this.depttName = depttName;
	}

	public Boolean getIsReadOnly() {
		return isReadOnly;
	}

	public void setIsReadOnly(Boolean readOnly) {
		isReadOnly = readOnly;
	}

	public Boolean getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(Boolean mandatory) {
		isMandatory = mandatory;
	}

	public Set<EmployeeDepartment> getEmployeeDepartments() {
		return employeeDepartments;
	}

	public void setEmployeeDepartments(Set<EmployeeDepartment> employeeDepartments) {
		this.employeeDepartments = employeeDepartments;
	}


}
