package controller;


import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


import controller.ProjectController;
import model.AvailableProject;
import model.Department;
import model.Employee;
import model.Projects;
import service.LoginService;
import service.ProjectService;
/**
 * <h1>ProjectControllerTest</h1>
 * @author Raksha R
 * @version 1.0
 * @since 18-9-18
 */
@RunWith(SpringRunner.class)
public class ProjectControllerTest {
	
	@Mock 
	ProjectService projectService;
	@Mock 
	LoginService loginService;
	
	
	
	private MockMvc mockMvc;
	@Test
	public void contextLoads() {
	}
	
	@Before
    public void setUp() {
		 InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	        viewResolver.setPrefix("/WEB-INF/jsp/");
	        viewResolver.setSuffix(".jsp");
	        
     mockMvc = MockMvcBuilders.standaloneSetup(new ProjectController(projectService, null,null))
    		 .setViewResolvers(viewResolver)
    		 .build();
    }
	
	
	
	
	Employee getEmployee1() {
		Employee emp=new Employee("raksha", "ramegowda", "rgowda@teksystems.com", new Date(1996 - 12 - 24));
		emp.setUser_id(2);
				return emp;
	}
	
	Department getDepartment1() {
		Department dept= new Department("department1" ,getEmployee1());
		dept.setDepartment_id(1);
		 return dept;
	}
	
	
	AvailableProject getAvailableProject() {
		Department dept= getDepartment1();
		AvailableProject ap = new AvailableProject("project1",new Date(2018 - 1 - 10),new Date(2018 - 1 - 30),"DAI",dept);
		ap.setA_id(2);
		return  ap;
		
		
	}
	Projects getProject1()
	{
		Department dept= getDepartment1();
		Employee emp=getEmployee1();
		AvailableProject ap =getAvailableProject();
		
		
		Projects pro1= new Projects(ap,emp,dept);
		return pro1;
		
	}

	
	List<Projects> getProjectList(){
		
		List<Projects> toReturn = new ArrayList<>();
		toReturn.add(getProject1());
		return toReturn;
	}
	
	
	@Test
	public void testGetAllProjects() throws Exception{
		Projects p =getProject1();
		Department dept=p.getDepartment();
		//Integer id = dept.getDepartment_id();
		
		when(projectService.getAllProjects()).thenReturn(getProjectList());
		this.mockMvc.perform(get("/getAllProjects"))
		.andExpect(status().isOk())
		.andExpect(view().name("getAllProjects"))
		.andExpect(model().attribute("projectsList",hasSize(1)))
				.andExpect(model().attribute("projectsList", hasItem(
						allOf(
								hasProperty("p_id",is(1))
							
								)
						)));
		
		
		
	}
	
	
//	@Test
//	public void testRegisterNewProject() throws Exception{
//		AvailableProject ap = getAvailableProject();
//		Department dept= getDepartment1();
//		dept.setDepartment_id(1);
//		String name1=ap.getName();
//		Date sd=ap.getStart_date();
//		Date ed =ap.getEnd_date();
//		String des = ap.getDescription();
//		Integer i = dept.getDepartment_id();
//	   ap.setName("project1");
//	   ap.setDepartment(dept);
//	   ap.setStart_date(new Date(2018 - 1 - 10));
//	   ap.setEnd_date(new Date(2018 - 1 - 30));
//	   ap.setDescription("description");
//		 this.mockMvc.perform(post("/registerNewAvailableProject?name=project1&start_date=2018-1-10&end_date=2018-1-30&description=description&department_id=1"))
//     
//		.andExpect(status().isOk())
//       .andExpect(view().name("welcomepage"))
//        .andDo(print());
//	}
	
	
//	@Test
//	public void testDeleteProject() throws Exception{
//		AvailableProject ap = getAvailableProject();
//		ap.setA_id(2);
//		 this.mockMvc.perform(get("/deleteAvailableProject?a_id=2"))
//		 	.andExpect(status().isOk())
//		 	.andExpect(view().name("welcomepage"))
//		 	.andDo(print());
//	}
	
//	@Test
//	public void testShowAllProjects() throws Exception{
//		
//		 this.mockMvc.perform(get("/showAvailableProjects"))
//       .andExpect(status().isOk())
//         .andExpect(view().name("welcomepage"))
//        .andDo(print());
//	}
}
