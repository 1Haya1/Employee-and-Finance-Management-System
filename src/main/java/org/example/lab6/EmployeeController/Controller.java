package org.example.lab6.EmployeeController;



import jakarta.validation.Valid;
import org.example.lab6.Api.ApiResponse;
import org.example.lab6.EmployeeModel.Employee;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class Controller {

    private List<Employee> employees = new ArrayList<>();

@GetMapping("/get")
public List<Employee> getAllEmployees() {
    return employees;
}

@PostMapping("/add")
    public ResponseEntity addEmployee(@RequestBody @Valid Employee employee){
    employees.add(employee);
    return ResponseEntity.status(200).body(new ApiResponse("employee added"));
}


@PutMapping("/update/{id}")
    public ResponseEntity updateEmployee(@PathVariable String id, @RequestBody @Valid Employee updatedEmployee) {
        for (Employee employee : employees) {
            if (employee.getId().equals(id)) {
                employee.setName(updatedEmployee.getName());
                employee.setEmail(updatedEmployee.getEmail());
                employee.setPhonenumber(updatedEmployee.getPhonenumber());
                employee.setAge(updatedEmployee.getAge());
                employee.setPosition(updatedEmployee.getPosition());
                employee.setOnleave(updatedEmployee.isOnleave());
                employee.setHiredate(updatedEmployee.getHiredate());
                employee.setAnuualleave(updatedEmployee.getAnuualleave());
                return ResponseEntity.status(200).body(new ApiResponse("employee updated"));
            }}
    return ResponseEntity.status(400).body(new ApiResponse("not found"));
}



    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEmployee(@PathVariable String id) {
        for (Employee employee : employees) {
            if (employee.getId().equals(id)) {
                employees.remove(employee);
                return ResponseEntity.status(200).body(new ApiResponse("employee deleted"));
            }
        }
        return ResponseEntity.status(400).body(new ApiResponse("not found"));
    }


    @GetMapping("/search/position")
    public ResponseEntity searchEmployeesByPosition(@RequestParam String position) {
        if (!position.equalsIgnoreCase("supervisor") && !position.equalsIgnoreCase("coordinator")) {
            return ResponseEntity.status(400).body(new ApiResponse("not found"));
        }

        List<Employee> employeesByPosition = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getPosition().equalsIgnoreCase(position)) {
                employeesByPosition.add(employee);
            }
        }
        return ResponseEntity.ok(employeesByPosition);
    }


    @GetMapping("/search/range")
    public ResponseEntity getEmployeesByAgeRange(@RequestParam int minAge, @RequestParam int maxAge) {
        if (minAge <= 25 || maxAge <= 25 || minAge >= maxAge) {
            return ResponseEntity.status(400).body(new ApiResponse("not found"));
        }

        List<Employee> employeesInAgeRange = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getAge() >= minAge && employee.getAge() <= maxAge) {
                employeesInAgeRange.add(employee);
            }
        }
        return ResponseEntity.ok(employeesInAgeRange);
    }


    @PostMapping("/{id}/apply/leave")
    public ResponseEntity applyForAnnualLeave(@PathVariable @Valid String id) {
        for (Employee employee : employees) {
            if (employee.getId().equals(id)) {
                if (!employee.isOnleave() && employee.getAnuualleave() > 0) {
                    employee.setOnleave(true);
                    employee.setAnuualleave(employee.getAnuualleave() - 1);
                    return ResponseEntity.ok("Leave application submitted");
                } else {
                    return ResponseEntity.status(400).body("Employee cant apply for leave");
                }
            }
        }
        return ResponseEntity.status(400).body(new ApiResponse("not found"));
    }


    @GetMapping("/noannual/leave")
    public ResponseEntity getEmployeesWithNoAnnualLeave() {
        List<Employee> employeesWithNoLeave = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getAnuualleave() == 0) {
                employeesWithNoLeave.add(employee);
            }
        }
        return ResponseEntity.ok(employeesWithNoLeave);
    }




    @PostMapping("/{id}/promote")
    public ResponseEntity promoteEmployee(@PathVariable @Valid String id) {
        for (Employee employee : employees) {
            if (employee.getId().equals(id)) {
                if (employee.getPosition().equalsIgnoreCase("coordinator") &&
                        employee.getAge() >= 30 &&
                        !employee.isOnleave()) {
                    employee.setPosition("supervisor");
                    return ResponseEntity.ok("employee promoted");
                } else {
                    return ResponseEntity.status(400).body("employee cant be promoted");
                }
            }
        }
        return ResponseEntity.status(400).body(new ApiResponse("not found"));
    }




}








