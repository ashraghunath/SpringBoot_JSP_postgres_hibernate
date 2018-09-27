package controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import model.Employee;
import service.AWSService;
import service.EmployeeService;
import service.ProjectService;

/**
 * <h1>ApplicationController</h1>
 * 
 * @author Ashwin Raghunath
 * @version 1.0
 * @since 3-9-18
 */
//@EnableWebMvc
@Controller
public class ApplicationController implements WebMvcConfigurer {

	@Autowired
	private final EmployeeService employeeService;
	@Autowired
	private final ProjectService projectService;
	@Autowired
	private final AWSService awsService;

	@Autowired
	public ApplicationController(EmployeeService employeeService, ProjectService projectService,
			AWSService awsService) {
		this.employeeService = employeeService;
		this.projectService = projectService;
		this.awsService = awsService;
	}

	@GetMapping("/")
	public String welcome(HttpServletRequest httpServletRequest, Authentication authentication) {
		if (authentication != null)
			httpServletRequest.setAttribute("userset", "YES");
		httpServletRequest.setAttribute("role", "employee");
		httpServletRequest.setAttribute("mode", "MODE_HOME");
		return "welcomepage";
	}

	@GetMapping("/register")
	public String Register(HttpServletRequest httpServletRequest) {
		  if (!httpServletRequest.getParameterMap().containsKey("employee")) {
		httpServletRequest.setAttribute("employee", new Employee());
		httpServletRequest.setAttribute("mode", "MODE_REGISTER");
			return "welcomepage";
		  }
		httpServletRequest.setAttribute("mode", "MODE_REGISTER");
		return "welcomepage";
	}

	@GetMapping("/registerDepartment")
	public String registerDepartment(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute("mode", "MODE_DREGISTER");
		httpServletRequest.setAttribute("userset", "YES");
		httpServletRequest.setAttribute("employees", employeeService.getAllEmployees());
		return "welcomepage";
	}


	@GetMapping("/login")
	public String login(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute("mode", "MODE_LOGIN");
		return "welcomepage";
	}

	@RequestMapping("/details")
	public String loggedin(HttpServletRequest httpServletRequest, Authentication authentication) {
		Employee employeeTemp = employeeService.findByemail(authentication.getName());
		if (projectService.getAllProjectsByEmployee(employeeTemp) != null) {
			httpServletRequest.setAttribute("allocations", projectService.getAllProjectsByEmployee(employeeTemp));
			httpServletRequest.setAttribute("mode", "MODE_EMPLOYEEDETAILS");
			httpServletRequest.setAttribute("userset", "YES");
			return "welcomepage";
		} else {
			httpServletRequest.setAttribute("allocations", employeeTemp);
			httpServletRequest.setAttribute("mode", "MODE_EMPLOYEEDETAILSNULL");
			httpServletRequest.setAttribute("userset", "YES");
			return "welcomepage";
		}
	}

	@GetMapping("/loginError")
	public String loginError(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute("mode", "MODE_LOGINERROR");
		return "welcomepage";
	}

	@GetMapping("/accessDenied")
	public String accessDenied(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute("mode", "MODE_ACCESSDENIED");
		return "welcomepage";
	}

	

	@GetMapping("/sendEmail")
	public String sendEmail(Integer user_id, HttpServletRequest httpServletRequest, Authentication authentication) {

		final String FROM = "ashwinr2296@gmail.com";
		final String TO = "araghunath@teksystems.com";
		final String SUBJECT = "De-allocation ";
		final String TEXTBODY = "Please de-allocate the employee "
				+ employeeService.getEmployeeById(user_id).getFname();

		awsService.sendEmail(FROM, TO, SUBJECT, TEXTBODY);

		httpServletRequest.setAttribute("userset", "YES");
		httpServletRequest.setAttribute("mode", "ALL_EMPLOYEES");
		httpServletRequest.setAttribute("employees", employeeService.getAllEmployees());
		httpServletRequest.setAttribute("emailSent", "yes");
		Employee employeeTemp = employeeService.findByemail(authentication.getName());
		if (employeeTemp.getRole().equals("admin")) {
			httpServletRequest.setAttribute("role", "admin");
		}
		return "welcomepage";
	}

}
