package wtg.crud.bootcamp.controller;
import org.springframework.web.bind.annotation.*;
import wtg.crud.bootcamp.model.Department;
import wtg.crud.bootcamp.service.DepartmentService;
import wtg.crud.bootcamp.service.DepartmentServiceImpl;
import java.util.List;

@RestController
@RequestMapping("/api/department/")
@CrossOrigin(origins = "http://localhost:4200")
public class DepartmentController {

    private final DepartmentService departmentService;
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("create")
    public Department createDepartment(@RequestBody Department department) {
        return this.departmentService.addDepartment(department);
    }

    @GetMapping("{id}")
    public Department getDepartment(@PathVariable("id") Integer departmentId) {
        return this.departmentService.getDepartmentById(departmentId);
    }

    @PutMapping("{id}")
    public Department updateDepartment(@PathVariable("id") Integer departmentId, @RequestBody Department department){
        return this.departmentService.updateDepartment(departmentId,department);
    }

    @DeleteMapping("{id}")
    public void deleteDepartment(@PathVariable("id") Integer departmentId) {
        this.departmentService.deleteDepartment(departmentId);
    }

    @GetMapping("all")
    public List<Department> getAllDepartments(){
    return this.departmentService.getAllDepartments();
    }
}
