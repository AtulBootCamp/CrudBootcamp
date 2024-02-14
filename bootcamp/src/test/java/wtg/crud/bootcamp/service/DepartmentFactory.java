package wtg.crud.bootcamp.service;

import wtg.crud.bootcamp.model.Department;

public class DepartmentFactory {
    public static Department createDepartment(Integer id, String departmentName, Boolean readOnly, Boolean mandatory){
        Department department=new Department();
        department.setId(id);
        department.setDepartmentName(departmentName);
        department.setReadOnly(readOnly);
        department.setMandatory(mandatory);
        return department;
    }
}
