package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import exceptionHandling.ResourceNotFoundException;
import model.Department;
import model.Employee;
import service.AWSService;
import service.DepartmentService;
import service.EmployeeService;

/**
 * <h1>DepartmentController</h1>
 * 
 * @author Ashwin Raghunath
 * @version 1.0
 * @since 3-9-18
 */
@Controller
public class DepartmentController {

	@Autowired
	private final DepartmentService departmentService;
	@Autowired
	private final EmployeeService employeeService;
	@Autowired
	private final AWSService awsService;

	public DepartmentController(DepartmentService departmentService, EmployeeService employeeService,
			AWSService awsService) {
		this.departmentService = departmentService;
		this.employeeService = employeeService;
		this.awsService = awsService;
	}

	@GetMapping("/getDepartmentById")
	public Department getDepartmentById(@RequestParam Integer department_id) {
		return departmentService.getDepartmentById(department_id);
	}

	@GetMapping("/showDepartments")
	public String showAllDepartments(HttpServletRequest httpServletRequest, Authentication authentication) {
		httpServletRequest.setAttribute("departments", departmentService.getAllDepartments());
		httpServletRequest.setAttribute("mode", "ALL_DEPARTMENTS");
		httpServletRequest.setAttribute("userset", "YES");
		Employee employeeTemp = employeeService.findByemail(authentication.getName());
		if (employeeTemp.getRole().equals("admin")) {
			httpServletRequest.setAttribute("role", "admin");
		}
		return "welcomepage";
	}

	@GetMapping("/getAllDepartments")
	public List<Department> getAllDepartments() {
		List<Department> list = departmentService.getAllDepartments();
		return list;

	}

	@PostMapping("/addDepartment")
	public void addDepartment(@RequestBody Department department) {
		departmentService.saveDepartment(department);
	}

	@PostMapping("/addNewDepartment")
	public String addNewDepartment(@RequestParam String departmentname, @RequestParam Integer user_id,
			HttpServletRequest httpServletRequest, Authentication authentication) throws ResourceNotFoundException {
		System.out.println("in controller "+user_id);
		try {
			departmentService.saveNewDepartment(departmentname, user_id);
			httpServletRequest.setAttribute("departments", departmentService.getAllDepartments());
			httpServletRequest.setAttribute("mode", "ALL_DEPARTMENTS");
			httpServletRequest.setAttribute("userset", "YES");
			Employee employeeTemp = employeeService.findByemail(authentication.getName());
			if (employeeTemp.getRole().equals("admin")) {
				httpServletRequest.setAttribute("role", "admin");
			}
			if (employeeTemp.getRole().equals("manager")) {
				httpServletRequest.setAttribute("role", "manager");
			}
			return "welcomepage";

		} catch (Exception e) {
			httpServletRequest.setAttribute("userset", "YES");
			throw new ResourceNotFoundException("Enter correct ID, Manager not found with given ID");
		}
	}

	@RequestMapping("/deleteDepartment")
	public String delete(@RequestParam Integer department_id, HttpServletRequest httpServletRequest,
			Authentication authentication) throws ResourceNotFoundException {
		try {
			Employee employee = departmentService.getDepartmentById(department_id).getEmployee();
			departmentService.deleteDepartment(department_id);
			employee.setRole("employee");
			employeeService.updateEmployee(employee);
			httpServletRequest.setAttribute("departments", departmentService.getAllDepartments());
			httpServletRequest.setAttribute("mode", "ALL_DEPARTMENTS");
			httpServletRequest.setAttribute("userset", "YES");
			Employee employeeTemp = employeeService.findByemail(authentication.getName());
			if (employeeTemp.getRole().equals("admin")) {
				httpServletRequest.setAttribute("role", "admin");
			}
			if (employeeTemp.getRole().equals("manager")) {
				httpServletRequest.setAttribute("role", "manager");
			}
			return "welcomepage";
		} catch (Exception e) {
			httpServletRequest.setAttribute("userset", "YES");
			httpServletRequest.setAttribute("mode", "MODE_EMAIL");
			httpServletRequest.setAttribute("departmentname",
					departmentService.getDepartmentById(department_id).getDepartmentname());
			return "welcomepage";

		}
	}

	@GetMapping("/sendDepartmentEmail")
	public String sendDepartmentEmail(String departmentname, HttpServletRequest httpServletRequest,
			Authentication authentication) {

		final String FROM = "ashwinr2296@gmail.com";
		final String TO = "araghunath@teksystems.com";
		final String SUBJECT = "De-allocation ";
		final String TEXTBODY = "Please delete projects in department " + departmentname;

		awsService.sendEmail(FROM, TO, SUBJECT, TEXTBODY);

		httpServletRequest.setAttribute("userset", "YES");
		httpServletRequest.setAttribute("departments", departmentService.getAllDepartments());
		httpServletRequest.setAttribute("mode", "ALL_DEPARTMENTS");
		httpServletRequest.setAttribute("emailSent", "yes");
		Employee employeeTemp = employeeService.findByemail(authentication.getName());
		if (employeeTemp.getRole().equals("admin")) {
			httpServletRequest.setAttribute("role", "admin");
		}
		return "welcomepage";
	}

}
