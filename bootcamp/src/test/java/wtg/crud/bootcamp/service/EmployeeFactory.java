package wtg.crud.bootcamp.service;

import wtg.crud.bootcamp.model.Department;
import wtg.crud.bootcamp.model.Employee;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EmployeeFactory {



    public static Employee createEmployee(Integer id, String employeeName, Set<Department> departments){
        Employee employee=new Employee();
        employee.setId(id);
        employee.setEmpName(employeeName);
        employee.setDepartments(departments);
        return employee;
    }

//    public static Employee createEmployeeWithNonMandatoryDepartments(Integer id, String employeeName, Set<Department> departments){
//        Employee employee=new Employee();
//        employee.setId(id);
//        employee.setEmpName(employeeName);
//        employee.setDepartments(departments);
//        return employee;
//    }
}
