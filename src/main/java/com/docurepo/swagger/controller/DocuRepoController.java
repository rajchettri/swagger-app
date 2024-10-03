package com.docurepo.swagger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.docurepo.swagger.model.EmployeeEntity;
import com.docurepo.swagger.repository.EmployeeResposity;
import com.docurepo.swagger.service.EmployeeService;

@CacheConfig(cacheNames = "employee")
@RestController
public class DocuRepoController {
	
	
	@Autowired
	EmployeeResposity repoResposity;
	
	@Autowired
	EmployeeService employeeService;
	
	
	@PostMapping("/employee/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void addEmployee(@RequestBody EmployeeEntity employee) {
		
		employeeService.addEmployee(employee); 
		
	}
	
	@GetMapping("/get/{id}")
	@ResponseStatus(code = HttpStatus.FOUND)
	@Cacheable(value = "employee", key = "#id")
	public EmployeeEntity getEmployee(@PathVariable("id") int id) {
		
		return employeeService.getEmployee(id);
		
	}
	
	@GetMapping("/get")
	@ResponseStatus(code = HttpStatus.FOUND)
	@Cacheable(value = "employee", keyGenerator = "customKeyGenerator")
	public List<EmployeeEntity> getAllEmployee() {
		
		return employeeService.getAllEmployee();
		
	}
	
	@PutMapping("/employee/update/{id}")
	@CachePut(cacheNames = "employee", key = "#id")
	public void updateEmployee(@RequestBody EmployeeEntity employee, @PathVariable("id") int id) {
		
		employeeService.updateEmployee(employee, id);
		
			
	}
	
	@PatchMapping("/employee/miniupdate/{id}")
	@CachePut(cacheNames = "employee", key = "#id")
	public void updatePatchEmployee(EmployeeEntity employee, Integer id) {
		employeeService.updatePatchEmployee(employee, id);
		
		
	}
	
	@DeleteMapping("/employee/delete/{id}")
	@CacheEvict(cacheNames = "employee", key = "#id", beforeInvocation = true)
	public void deleteEmployee(@PathVariable("id") int id) {
		
		employeeService.deleteEmployee(id);
	}

}
