package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import model.Employee;
import model.Login;
import repository.EmployeeRepository;
import repository.LoginRepository;

/**
 * <h1>EmployeeService</h1>
 * 
 * @author Ashwin Raghunath
 * @version 1.0
 * @since 3-9-18
 */
@Transactional
@Service
public class EmployeeService {

	@Autowired
	private final EmployeeRepository employeeRepository;
	@Autowired
	private final LoginRepository loginRepository;

	public EmployeeService(EmployeeRepository employeeRepository, LoginRepository loginRepository) {
		this.employeeRepository = employeeRepository;
		this.loginRepository = loginRepository;
	}

	public void saveEmployee(Employee employee, String password) {

		employee.setRole("employee");
		employeeRepository.save(employee);
		employee.getUser_id();
		Login loginTemp = new Login(employee.getUser_id(), employee, password);
		loginRepository.save(loginTemp);
	}

	public void updateEmployee(Employee employee) {
		Optional<Employee> toUpdateEmployee = employeeRepository.findById(employee.getUser_id());
		toUpdateEmployee.get().setUser_id(employee.getUser_id());
		toUpdateEmployee.get().setDob(employee.getDob());
		toUpdateEmployee.get().setEmail(employee.getEmail());
		toUpdateEmployee.get().setFname(employee.getFname());
		toUpdateEmployee.get().setLname(employee.getLname());
		toUpdateEmployee.get().setRole(employee.getRole());

		employeeRepository.save(toUpdateEmployee.get());
	}

//	public Optional<Employee> updateEmployee(Integer user_id) {
//		return employeeRepository.findById(user_id);
//	}

	public Employee getEmployeeById(Integer user_id) {
		return employeeRepository.findById(user_id).get();
	}

	public List<Employee> getAllEmployees() {
		List<Employee> employees = (List<Employee>) employeeRepository.findAll();
		return employees;
	}

	public List<Employee> getAllRegularEmployees() {
		List<Employee> employees = (List<Employee>) employeeRepository.findAll();
		List<Employee> regularEmployees = new ArrayList<Employee>();
		for(Employee employee : employees) {
			if(employee.getRole().equals("employee"))
				regularEmployees.add(employee);
		}
		return regularEmployees;
	}
	
	public void deleteEmployee(Integer user_id) {
		employeeRepository.deleteById(user_id);
	}

	public Employee findByemail(String email) {
		return employeeRepository.findByemail(email);
	}
}
