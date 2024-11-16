package net.javaguides.employeeservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import net.javaguides.employeeservice.dto.APIResponseDto;
import net.javaguides.employeeservice.dto.DepartmentDto;
import net.javaguides.employeeservice.dto.EmployeeDto;
import net.javaguides.employeeservice.entity.Employee;
import net.javaguides.employeeservice.repository.EmployeeRepository;
import net.javaguides.employeeservice.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private WebClient webClient;

	@Override
	public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
		Employee employee = new Employee(employeeDto.getId(), employeeDto.getFirstName(), employeeDto.getLastName(),
				employeeDto.getEmail(), employeeDto.getDepartmentCode());
		Employee saveDEmployee = employeeRepository.save(employee);
		EmployeeDto savedEmployeeDto = new EmployeeDto(saveDEmployee.getId(), saveDEmployee.getFirstName(),
				saveDEmployee.getLastName(), saveDEmployee.getEmail(), saveDEmployee.getDepartmentCode());
		return savedEmployeeDto;
	}

	@Override
	public APIResponseDto getEmployeeById(Long employeeId) {
		Employee employee = employeeRepository.findById(employeeId).get();
		DepartmentDto departmentDto = webClient.get()
				.uri("http://localhost:8080/api/departments/" + employee.getDepartmentCode()).retrieve()
				.bodyToMono(DepartmentDto.class).block();
		EmployeeDto employeeDto = new EmployeeDto(employee.getId(), employee.getFirstName(), employee.getLastName(),
				employee.getEmail(), employee.getDepartmentCode());

		APIResponseDto apiResponseDto = new APIResponseDto();
		apiResponseDto.setEmployee(employeeDto);
		apiResponseDto.setDepartment(departmentDto);
		return apiResponseDto;
	}

	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}
}
