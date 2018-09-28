package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Department;
import model.Employee;
import repository.DepartmentRepository;

/**
 * <h1>DepartmentService</h1>
 * 
 * @author Ashwin Raghunath.
 * @version 1.0
 * @since 3-9-18
 */
@Service
public class DepartmentService {

	@Autowired
	private final DepartmentRepository departmentRepository;
	@Autowired
	private final EmployeeService employeeService;

	public DepartmentService(DepartmentRepository departmentRepository, EmployeeService employeeService) {
		this.departmentRepository = departmentRepository;
		this.employeeService = employeeService;
	}

	public void saveDepartment(Department department) {
		departmentRepository.save(department);
	}

	public void saveNewDepartment(String departmentname, Integer user_id) {
		Employee employee = employeeService.getEmployeeById(user_id);
		employee.setRole("manager");
		Department department = new Department(departmentname, employee);
		departmentRepository.save(department);
	}

	public Department getDepartmentById(Integer department_id) {
		return departmentRepository.findById(department_id).get();
		
	}

	public List<Department> getAllDepartments() {
		List<Department> list = (List<Department>) departmentRepository.findAll();
		return list;
	}
	
	public Department getDepartmentByDepartmentName(String departmentname) {
		return departmentRepository.findBydepartmentname(departmentname);
	}
	
	public List<Department> getDepartmentByManager(Employee employee) {
		return departmentRepository.findBymanager(employee);
	}
	
	public void deleteDepartment(Integer department_id) {
		departmentRepository.deleteById(department_id);
	}

}
