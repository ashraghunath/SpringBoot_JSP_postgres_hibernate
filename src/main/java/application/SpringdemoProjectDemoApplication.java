package application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//import config.SecurityConfiguration;
import repository.AvailableProjectRepository;
import repository.LoginRepository;

@SpringBootApplication
@ComponentScan(basePackages = {"Controller", "service", "config", "exceptionHandling" })
@EnableJpaRepositories(basePackages = { "repository" })
@EntityScan(basePackages = { "model" })
public class SpringdemoProjectDemoApplication {

	@Autowired
	public static AvailableProjectRepository availableProjectRepository;
	
	@Autowired
	public static LoginRepository loginRepository;
	public static void main(String[] args) {
	
		SpringApplication.run(SpringdemoProjectDemoApplication.class, args);

		
	}
}
