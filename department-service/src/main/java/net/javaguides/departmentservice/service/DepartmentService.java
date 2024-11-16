package net.javaguides.departmentservice.service;

import java.util.List;

import net.javaguides.departmentservice.dto.DepartmentDto;
import net.javaguides.departmentservice.entity.Department;

public interface DepartmentService {
	DepartmentDto saveDepartment(DepartmentDto departmentDto);

	DepartmentDto getDepartmentByCode(String code);

	public List<Department> getAllDepartments();
}