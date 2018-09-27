package config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import model.Login;
import repository.AvailableProjectRepository;
import repository.DepartmentRepository;
import repository.EmployeeRepository;
import repository.LoginRepository;
import repository.ProjectsRepository;

/**
 * <h1>SecurityConfiguration</h1>
 * 
 * @author Ashwin Raghunath
 * @version 1.0
 * @since 3-9-18
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses = { EmployeeRepository.class, LoginRepository.class,
		AvailableProjectRepository.class, ProjectsRepository.class, DepartmentRepository.class })
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoginRepository loginRepository;

	public SecurityConfiguration(LoginRepository loginRepository) {
		this.loginRepository = loginRepository;
	}


	@Bean
	public InMemoryUserDetailsManager userDetailsManager() {
		try {
			@SuppressWarnings("deprecation")
			UserBuilder builder = User.withDefaultPasswordEncoder();
			List<Login> passList = (List<Login>) loginRepository.findAll();
			List<UserDetails> userDetails = new ArrayList<UserDetails>();
			for (Login employee : passList) {
				UserDetails user = builder.username(employee.getEmployee().getEmail())
						.password(employee.getPasswordString()).roles(employee.getEmployee().getRole()).build();
				userDetails.add(user);
				
			}
			return new InMemoryUserDetailsManager(userDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
//	
//
//	public String runThis() {
//		ApplicationContext ctx = new AnnotationConfigApplicationContext(SecurityConfiguration.class);
//		InMemoryUserDetailsManager userDetailsManager = (InMemoryUserDetailsManager)ctx.getBean("userDetailsManager");
//		System.out.println(userDetailsManager.toString());
//		return "success";
//
//	}
//	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/home/**", "/css/**", "/js/**", "/fonts/**")
				.permitAll().antMatchers("/showDepartments").hasAnyRole("admin", "manager", "employee")
				.antMatchers("/showAllocations").hasRole("manager").antMatchers("/getAllEmployees")
				.hasAnyRole("admin", "manager", "employee").antMatchers("/welcome").permitAll().and().formLogin()
				.loginPage("/login").defaultSuccessUrl("/details").failureUrl("/loginError").permitAll().and().httpBasic().disable().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").permitAll()
				.and().exceptionHandling().accessDeniedPage("/accessDenied");
	}

}
