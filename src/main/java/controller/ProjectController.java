package controller;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import exceptionHandling.ResourceAllocatedException;
import exceptionHandling.ResourceNotFoundException;
import model.AvailableProject;
import model.Employee;
import model.Projects;
import service.DepartmentService;
import service.EmployeeService;
import service.ProjectService;

/**
 * <h1>ProjectController</h1>
 * 
 * @author Ashwin Raghunath
 * @version 1.0
 * @since 3-9-18
 */
@Controller
public class ProjectController {

	@Autowired
	private final ProjectService projectService;
	@Autowired
	private final EmployeeService employeeService;
	@Autowired
	private final DepartmentService departmentService;

	public ProjectController(ProjectService projectService, EmployeeService employeeService, DepartmentService departmentService) {
		this.projectService = projectService;
		this.employeeService = employeeService;
		this.departmentService = departmentService;
	}

	@RequestMapping("/deleteAvailableProject")
	public String delete(@RequestParam Integer a_id, HttpServletRequest httpServletRequest,
			Authentication authentication) throws ResourceAllocatedException {
		AvailableProject availableProjectTemp = projectService.getAvailableProjectById(a_id);
		try {
//		if (projectService.findAllByallocatedProject(availableProjectTemp) == null) {
			projectService.deleteAvailableProject(a_id);
			httpServletRequest.setAttribute("availibleprojects", projectService.getAllAvailableProjects());
			httpServletRequest.setAttribute("mode", "ALL_PROJECTS");
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
			throw new ResourceAllocatedException("Employees are allocated to project, first remove allocation.");
		}
	}

	@GetMapping("/allocateToProject")
	public String allocateToProject(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute("mode", "MODE_ALLOCATE");
		httpServletRequest.setAttribute("userset", "YES");
		httpServletRequest.setAttribute("availableProjects", projectService.getAllAvailableProjects());
		httpServletRequest.setAttribute("employees", employeeService.getAllRegularEmployees());
		return "welcomepage";
	}
	
	@GetMapping("/showAvailableProjects")
	public String showAllProjects(HttpServletRequest httpServletRequest, Authentication authentication) {
		httpServletRequest.setAttribute("availibleprojects", projectService.getAllAvailableProjects());
		httpServletRequest.setAttribute("mode", "ALL_PROJECTS");
		httpServletRequest.setAttribute("userset", "YES");
		Employee employeeTemp = employeeService.findByemail(authentication.getName());
		if (employeeTemp.getRole().equals("admin")) {
			httpServletRequest.setAttribute("role", "admin");
		}
		if (employeeTemp.getRole().equals("manager")) {
			httpServletRequest.setAttribute("role", "manager");
		}
		return "welcomepage";
	}

	@GetMapping("/getAllAvailableProjects")
	public List<AvailableProject> getAllAvailableProjects(HttpServletRequest httpServletRequest) {
		List<AvailableProject> list = projectService.getAllAvailableProjects();
		return list;
	}

	@GetMapping("/getAllProjects")
	public List<Projects> getAllProjects() {
		List<Projects> list = projectService.getAllProjects();
		return list;
	}

	@GetMapping("/showAllocations")
	public String showAllocations(HttpServletRequest httpServletRequest, Authentication authentication) {
		httpServletRequest.setAttribute("allocations", projectService.getAllProjects());
		httpServletRequest.setAttribute("mode", "ALL_ALLOCATIONS");
		httpServletRequest.setAttribute("userset", "YES");
		Employee employeeTemp = employeeService.findByemail(authentication.getName());
		if (employeeTemp.getRole().equals("admin")) {
			httpServletRequest.setAttribute("role", "admin");
		}
		if (employeeTemp.getRole().equals("manager")) {
			httpServletRequest.setAttribute("role", "manager");
		}
		return "welcomepage";
	}

	@GetMapping("/registerAvailableProject")
	public String registerProject(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute("mode", "MODE_PREGISTER");
		httpServletRequest.setAttribute("userset", "YES");
		httpServletRequest.setAttribute("departments", departmentService.getAllDepartments());
		return "welcomepage";
	}
	
	@PostMapping("/registerNewAvailableProject")
	public String addNewAvailableProject(@RequestParam String name, @RequestParam Date start_date,
			@RequestParam Date end_date, @RequestParam String description, @RequestParam Integer department_id,
			HttpServletRequest httpServletRequest, Authentication authentication) throws ResourceNotFoundException {
		projectService.addNewAvailableProject(name, start_date, end_date, description, department_id);
		httpServletRequest.setAttribute("availibleprojects", projectService.getAllAvailableProjects());
		httpServletRequest.setAttribute("mode", "ALL_PROJECTS");
		httpServletRequest.setAttribute("userset", "YES");
		Employee employeeTemp = employeeService.findByemail(authentication.getName());
		if (employeeTemp.getRole().equals("admin")) {
			httpServletRequest.setAttribute("role", "admin");
		}
		if (employeeTemp.getRole().equals("manager")) {
			httpServletRequest.setAttribute("role", "manager");
		}
		return "welcomepage";
	}

	@PostMapping("/addAllocation")
	public String addNewDepartment(@RequestParam Integer a_id, @RequestParam Integer user_id,
			HttpServletRequest httpServletRequest, Authentication authentication) throws ResourceNotFoundException {
		projectService.saveNewAllocation(a_id, user_id);
		httpServletRequest.setAttribute("allocations", projectService.getAllProjects());
		httpServletRequest.setAttribute("mode", "ALL_ALLOCATIONS");
		httpServletRequest.setAttribute("userset", "YES");
		Employee employeeTemp = employeeService.findByemail(authentication.getName());
		if (employeeTemp.getRole().equals("admin")) {
			httpServletRequest.setAttribute("role", "admin");
		}
		if (employeeTemp.getRole().equals("manager")) {
			httpServletRequest.setAttribute("role", "manager");
		}
		return "welcomepage";
	}

	@RequestMapping("/deleteAllocation")
	public String deleteAllocation(@RequestParam Integer p_id, HttpServletRequest httpServletRequest,
			Authentication authentication) {
		projectService.deleteAllocation(p_id);
		httpServletRequest.setAttribute("allocations", projectService.getAllProjects());
		httpServletRequest.setAttribute("mode", "ALL_ALLOCATIONS");
		httpServletRequest.setAttribute("userset", "YES");
		Employee employeeTemp = employeeService.findByemail(authentication.getName());
		if (employeeTemp.getRole().equals("admin")) {
			httpServletRequest.setAttribute("role", "admin");
		}
		if (employeeTemp.getRole().equals("manager")) {
			httpServletRequest.setAttribute("role", "manager");
		}
		return "welcomepage";
	}
}
