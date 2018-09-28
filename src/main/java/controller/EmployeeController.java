package controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import exceptionHandling.ResourceAllocatedException;
import model.Employee;
import model.Resume;
import repository.AvailableProjectRepository;
import repository.DepartmentRepository;
import repository.EmployeeRepository;
import repository.LoginRepository;
import repository.ProjectsRepository;
import repository.ResumeRepository;
import service.EmployeeService;
import service.LoginService;
import service.ProjectService;

/**
 * <h1>EmployeeController</h1>
 * 
 * @author Ashwin Raghunath
 * @version 1.0
 * @since 3-9-18
 */
@ComponentScan(basePackages = { "service", "config", "exceptionHandling", "repository", "model" })
@Controller
@EnableJpaRepositories(basePackageClasses = { EmployeeRepository.class, LoginRepository.class,
		AvailableProjectRepository.class, ProjectsRepository.class, DepartmentRepository.class })
public class EmployeeController {

	@Autowired
	private final EmployeeService employeeService;
	@Autowired
	private final LoginService loginService;
	@Autowired
	private final ProjectService projectService;
	@Autowired
	private final DepartmentRepository departmentRepository;
	@Autowired
	ResumeRepository fileRepository;
	@Autowired
	private InMemoryUserDetailsManager inMemoryUserDetailsManager;

	public EmployeeController(EmployeeService employeeService, LoginService loginService, ProjectService projectService,
			DepartmentRepository departmentRepository, ResumeRepository fileRepository, InMemoryUserDetailsManager inMemoryUserDetailsManager) {
		this.employeeService = employeeService;
		this.loginService = loginService;
		this.projectService = projectService;
		this.departmentRepository = departmentRepository;
		this.fileRepository = fileRepository;
		this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
	}

	@GetMapping("/getEmployeeById")
	public Employee getEmployeeById(@RequestParam Integer user_id) {
		return employeeService.getEmployeeById(user_id);
	}

	@GetMapping("/all")
	public List<Employee> getAll(){
		return employeeService.getAllEmployees();
	}
	
	@GetMapping("/getAllEmployees")
	public String getAllEmployees(HttpServletRequest httpServletRequest, Authentication authentication) {
		Employee employeeTemp = employeeService.findByemail(authentication.getName());
		
		
		httpServletRequest.setAttribute("mode", "ALL_EMPLOYEES");
		httpServletRequest.setAttribute("userset", "YES");
		
		if (employeeTemp.getRole().equals("admin")) {
			httpServletRequest.setAttribute("role", "admin");
			httpServletRequest.setAttribute("employees", employeeService.getAllEmployees());
		}
		if(employeeTemp.getRole().equals("employee")) {
			httpServletRequest.setAttribute("employees", employeeService.getAllEmployees());
		}
		if (employeeTemp.getRole().equals("manager")) {
			httpServletRequest.setAttribute("role", "manager");
			httpServletRequest.setAttribute("employees", employeeService.getAllRegularEmployees());
		}
		return "welcomepage";
	}

	@PostMapping("/saveEmployee")
	public String registerEmployee(@ModelAttribute("employee") @Valid  Employee employee, @RequestParam("uploadfile") MultipartFile file, BindingResult bindingResult, String password,
			HttpServletRequest httpServletRequest) {
		try {
			if(bindingResult.hasErrors()) {
				httpServletRequest.setAttribute("mode", "MODE_REGISTER");
				httpServletRequest.setAttribute("beanError", bindingResult.getFieldError().getDefaultMessage());
				return "welcomepage";
			}
			Resume filemode = new Resume(file.getOriginalFilename(), file.getContentType(), file.getBytes());
	    	fileRepository.save(filemode);
	    	employee.setFile(filemode);
			employeeService.saveEmployee(employee, password);
			UserBuilder builder = User.withDefaultPasswordEncoder();
			UserDetails user = builder.username(employee.getEmail())
					.password(password).roles(employee.getRole()).build();
			inMemoryUserDetailsManager.createUser(user);
//		ApplicationContext ctx = new AnnotationConfigApplicationContext(SecurityConfiguration.class);
//		InMemoryUserDetailsManager userDetailsManager = (InMemoryUserDetailsManager)ctx.getBean("userDetailsManager");
//		UserBuilder builder = User.withDefaultPasswordEncoder();
//		UserDetails user = builder.username(employee.getEmail())
//				.password(password).roles(employee.getRole()).build();
//		userDetailsManager.createUser(user);
			httpServletRequest.setAttribute("mode", "MODE_HOME");
			return "welcomepage";
		} catch (Exception e) {
			httpServletRequest.setAttribute("mode", "MODE_REGISTER");
			httpServletRequest.setAttribute("userexists", "YES");
			httpServletRequest.setAttribute("userset", "YES");
			return "welcomepage";
		}
	}

	@GetMapping("/saveUpdatedEmployee")
	public String updatedEmployee(@ModelAttribute Employee employee, BindingResult bindingResult,
			HttpServletRequest httpServletRequest, Authentication authentication) {
		employeeService.updateEmployee(employee);
		httpServletRequest.setAttribute("employees", employeeService.getAllEmployees());
		httpServletRequest.setAttribute("mode", "ALL_EMPLOYEES");
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

	@RequestMapping("/deleteEmployee")
	public String delete(@RequestParam Integer user_id, HttpServletRequest httpServletRequest,
			Authentication authentication) throws ResourceAllocatedException {

		Employee employeeTemp1 = employeeService.getEmployeeById(user_id);
		if (projectService.getAllProjectsByEmployee(employeeTemp1) == null) {
			if (departmentRepository.findBymanager(employeeTemp1) == null) {
				loginService.delete(user_id);
				employeeService.deleteEmployee(user_id);
				httpServletRequest.setAttribute("employees", employeeService.getAllEmployees());
				httpServletRequest.setAttribute("mode", "ALL_EMPLOYEES");
				httpServletRequest.setAttribute("userset", "YES");
				Employee employeeTemp = employeeService.findByemail(authentication.getName());
				if (employeeTemp.getRole().equals("admin")) {
					httpServletRequest.setAttribute("role", "admin");
				}
				if (employeeTemp.getRole().equals("manager")) {
					httpServletRequest.setAttribute("role", "manager");
				}
				return "welcomepage";
			} else {
				httpServletRequest.setAttribute("userset", "YES");
				throw new ResourceAllocatedException("Manager is associated with a department");
			}
		} else {
			httpServletRequest.setAttribute("userset", "YES");
			httpServletRequest.setAttribute("mode", "MODE_EMAIL");
			httpServletRequest.setAttribute("employee", employeeService.getEmployeeById(user_id));
			return "welcomepage";
		}
	}

	@RequestMapping("/updateEmployee")
	public String updateEmployee(@RequestParam Integer user_id, HttpServletRequest httpServletRequest,
			Authentication authentication) {
		httpServletRequest.setAttribute("employee", employeeService.getEmployeeById(user_id));
		httpServletRequest.setAttribute("mode", "MODE_UPDATE");
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
	
	@RequestMapping("/downloadfile")
	public ResponseEntity<byte[]>resumedownload(@RequestParam String id,HttpServletRequest httpServletRequest,Authentication authentication)
	{	
		Long a = new Long( id);
     Optional<Resume> fileOptional = fileRepository.findById(a);
		
		if(fileOptional.isPresent()) {
			Resume file = fileOptional.get();
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
					.body(file.getPic());	
		}
		
		return ResponseEntity.status(200).body(null);
	}

}
