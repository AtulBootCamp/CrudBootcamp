package wtg.crud.bootcamp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wtg.crud.bootcamp.model.Department;
import wtg.crud.bootcamp.service.DepartmentService;

@RestController
@RequestMapping("/api/department/")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("create")
    public void createDepartment(@RequestBody Department department) {
        this.departmentService.addDepartment(department);
    }

    @GetMapping("{id}")
    public ResponseEntity<Department> getDepartment(@PathVariable("id") Integer departmentId) {

        Department department = this.departmentService.getDepartmentById(departmentId);
        if(department!=null)
            return ResponseEntity.ok(department);
        else
            return ResponseEntity.notFound().build();
    }

    @PutMapping("{id}")
    public void updateDepartment(@PathVariable("id") Integer departmentId, @RequestBody Department department){
         this.departmentService.updateDepartment(departmentId,department);

    }

    @DeleteMapping("{id}")
    public void deleteDepartment(@PathVariable("id") Integer departmentId) {
        this.departmentService.deleteDepartment(departmentId);
    }



}
