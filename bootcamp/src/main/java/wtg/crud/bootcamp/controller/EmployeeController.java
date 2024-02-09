package wtg.crud.bootcamp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wtg.crud.bootcamp.model.Employee;
import wtg.crud.bootcamp.service.EmployeeServiceImpl;

import java.util.List;

@RestController
@RequestMapping("api/employee/")
public class EmployeeController {

    private final EmployeeServiceImpl employeeService;

    public EmployeeController(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("create")
    public Employee createEmployee(@RequestBody Employee employee) {
        return this.employeeService.createEmployee(employee);
    }

    @GetMapping("{empId}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("empId") Integer empId){
        Employee employee = this.employeeService.getEmployeeById(empId);
        if(employee!=null)
            return ResponseEntity.ok(employee);
        else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{empId}")
    public void deleteEmployee(@PathVariable("empId") Integer empId) {
        this.employeeService.deleteEmployee(empId);
    }

    @PutMapping("{empId}")
    public Employee updateEmployee(@PathVariable("empId") Integer empId,@RequestBody Employee employee){
        return this.employeeService.updateEmployee(empId,employee);
    }

    @GetMapping("all")
    public List<Employee> getAllEmployees(){
        return this.employeeService.getAllEmployees();
    }
}
