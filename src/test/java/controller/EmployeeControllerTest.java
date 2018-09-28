package controller;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import controller.EmployeeController;
import model.Employee;
import service.EmployeeService;
import service.LoginService;
import service.ProjectService;
/**
 * <h1>EmployeeControllerTest</h1>
 * @author Raksha R
 * @version 1.0
 * @since 18-9-18
 */
@RunWith(SpringRunner.class)
public class EmployeeControllerTest {

	@Mock 
	EmployeeService empService;
	@Mock 
	LoginService loginService;
	@Mock
	ProjectService projectService;
	
	private MockMvc mockMvc;
	@Test
	public void contextLoads() {
	}
	
	@Before
    public void setUp() {
		 InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	        viewResolver.setPrefix("/WEB-INF/jsp/");
	        viewResolver.setSuffix(".jsp");
	        
     mockMvc = MockMvcBuilders.standaloneSetup(new EmployeeController(empService,loginService,projectService, null,null,null))
    		 .setViewResolvers(viewResolver)
    		 .build();
    }
	
//	@Test
//	public void testAllEmployees() throws Exception{
//		
//		 this.mockMvc.perform(get("/getAllEmployees"))
//       .andExpect(status().isOk())
//         .andExpect(view().name("welcomepage"))
//        .andDo(print());
//	}
	
	
//	@Test
//	public void testUpdateEmployee() throws Exception{
//		Employee emp =getEmployee1();
//		emp.setUser_id(2);
//		//when(empService.getEmployeeById(2).get()).thenReturn(emp);
//		 this.mockMvc.perform(get("/updateEmployee?user_id=2"))
//       .andExpect(status().isOk())
//         .andExpect(view().name("welcomepage"))
//        .andDo(print());
//	}
//	
//	@Test
//	public void testDeleteEmployee() throws Exception{
//		getEmployee1();
//		 this.mockMvc.perform(get("/deleteEmployee?user_id=2"))
//       .andExpect(status().isOk())
//         .andExpect(view().name("welcomepage"))
//        .andDo(print());
//	}
	
//	@Test
//	public void saveUpdateEmployee() throws Exception{
//		Employee emp=getEmployee1();
//		emp.setLname("gowda");
//		 this.mockMvc.perform(get("/saveUpdatedEmployee?employee=emp"))
//       .andExpect(status().isOk())
//         .andExpect(view().name("welcomepage"))
//        .andDo(print());
//	}
	
	@Test
	public void saveEmployee() throws Exception{
		Employee emp=getEmployee1();
		emp.setLname("gowda");
		String pass=emp.getFname();
		 this.mockMvc.perform(post("/saveEmployee?employee=emp&password=pass"))
       .andExpect(status().isOk())
         .andExpect(view().name("welcomepage"))
        .andDo(print());
	}
	
	Employee getEmployee1() {
		Employee emp=new Employee("raksha", "ramegowda", "rgowda@teksystems.com", new Date(1996 - 12 - 24));
		emp.setUser_id(2);
				return emp;
	}
	
	
}