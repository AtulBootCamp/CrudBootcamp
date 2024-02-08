package wtg.crud.bootcamp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String empName;


	@OneToMany(mappedBy = "employee")
	private Set<EmployeeDepartment> employeeDepartments = new HashSet<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Set<EmployeeDepartment> getEmployeeDepartments() {
		return employeeDepartments;
	}

	public void setEmployeeDepartments(Set<EmployeeDepartment> employeeDepartments) {
		this.employeeDepartments = employeeDepartments;
	}
}
