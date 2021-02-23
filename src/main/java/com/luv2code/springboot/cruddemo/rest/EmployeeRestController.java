package com.luv2code.springboot.cruddemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springboot.cruddemo.dao.EmployeeDAO;
import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
	
	// quick and dirty : inject employee dao 

	
	private EmployeeService employeeServide ;
	
	
	@Autowired
	public EmployeeRestController(EmployeeService theEmployeeService) {
		employeeServide = theEmployeeService ;
	}

	
	// expose "/employee" and return list of employees 
	@GetMapping("/employees")
	public List<Employee> findAll() {
		return employeeServide.findAll();
	}
	
	
	
	// expose "/employee/{employeeId} and return a single employee 
	@GetMapping("/employees/{employeeId}")
	public Employee getEmployee(@PathVariable int employeeId) {
		Employee theEmployee = employeeServide.findById(employeeId);
	
		if (theEmployee == null) {
			throw new RuntimeException("employee id not found - " + employeeId);
		}
		
		return theEmployee;
	}
	
	
	
	// add mapping for post / employees - add new employee
	@PostMapping("/employees")
	public Employee addEmployee(@RequestBody Employee theEmployee) {
		// we set id to 0 - as if anyone try to put id in json 
		// we did this so that this not update 
		theEmployee.setId(0);
		employeeServide.save(theEmployee);
		return theEmployee;
	}
	
	
	// add mapping for put / employees - update existing  employee
	@PutMapping("/employees")
	public Employee updateEmployee(@RequestBody Employee theEmployee) {
		employeeServide.save(theEmployee);
		return theEmployee;
	}
	
	
	
	// add mapping for Delete /employees/{employeeId} - delete  employee
	@DeleteMapping("/employees/{employeeId}")
	public String deleteEmployee(@PathVariable int employeeId) {
	 
		Employee theEmployee = employeeServide.findById(employeeId);
		
		if (theEmployee == null) {
			throw new RuntimeException("employee id not found - " + employeeId);
		}
		
		employeeServide.deleteByID(employeeId);
		
		return "deleted Employee is " + employeeId;
	}
	
}
