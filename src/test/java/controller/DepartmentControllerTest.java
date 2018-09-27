package controller;

import static org.assertj.core.api.Assertions.allOf;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.springframework.http.MediaType;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import controller.DepartmentController;
import model.Department;
import model.Employee;
//import service.LoginService;
import service.DepartmentService;


/**
 * <h1>DepartmentControllerTest</h1>
 * @author Raksha R
 * @version 1.0
 * @since 18-9-18
 */
@RunWith(SpringRunner.class)
public class DepartmentControllerTest {
	
	@Mock
	DepartmentService departmentService;
	@InjectMocks
	DepartmentController departmentController;

	
	
	
	private MockMvc mockMvc;
	
	@Test
	public void contextLoads() {
	}
	
	@Before
    public void setUp() {
		 InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	        viewResolver.setPrefix("/WEB-INF/jsp/");
	        viewResolver.setSuffix(".jsp");
	        
     mockMvc = MockMvcBuilders.standaloneSetup(new DepartmentController(departmentService, null, null))
    		 .setViewResolvers(viewResolver)
    		 .build();
    }

	
//	@Test
//	public void testShowDept() throws Exception{
//		
//		 this.mockMvc.perform(get("/showDepartments"))
//       .andExpect(status().isOk())
//         .andExpect(view().name("welcomepage"))
//        .andDo(print());
//	}
//	
	
//	@Test
//	public void testAddDept() throws Exception{
//		
//		this.mockMvc.perform(post("/addNewDepartment?departmentname=department1&user_id=2"))
//         .andExpect(status().isOk())
//         .andExpect(view().name("welcomepage"))
//         .andDo(print());
//	}
	
//	@Test
//	public void testDeleteDept() throws Exception{
//		getDepartment1();
//		 this.mockMvc.perform(get("/deleteDepartment?department_id=1"))
//		 	.andExpect(status().isOk())
//		 	.andExpect(view().name("welcomepage"))
//		 	.andDo(print());
//	}
	
	@Test
	public void testGetAllDepartments() throws Exception {
		when(departmentService.getAllDepartments()).thenReturn(getDepartmentList());
		
		this.mockMvc.perform(get("/getAllDepartments"))
				.andExpect(status().isOk())
				.andExpect(view().name("getAllDepartments"))
				.andExpect(model().attribute("departmentList", hasSize(1)))
				.andExpect(model().attribute("departmentList", hasItem(
						allOf(
								hasProperty("departmentname",is("department1"))
							
								)
						)));
		
		
		 //	.andExpect(status().isOk());
		 //	.andExpect(jsonPath("$",hasSize(1)));
		
		
	}
	
	
	List<Department> getDepartmentList(){
		List<Department> toReturn = new ArrayList<>();
		toReturn.add(getDepartment1());
		return toReturn;
	}
	
		
	
	
	Department getDepartment1() {
		Department dept= new Department("department1" ,getEmployee1());
		dept.setDepartment_id(1);
		 return dept;
	}
	
	
	Employee getEmployee1() {
		Employee emp=new Employee("raksha", "ramegowda", "rgowda@teksystems.com", new Date(1996 - 12 - 24));
		emp.setUser_id(2);
				return emp;
	}
	
	
	@Test
	public void testAddDepartments() throws Exception {
	
		String path="{\r\n" + 
				"	\"department_id\": 1,\r\n" + 
				"	\"departmentname\": \"sales\",\r\n" + 
				"	\"manager\": {\r\n" + 
				"		\"user_id\": 1,\r\n" + 
				"		\"fname\": \"raksha\",\r\n" + 
				"		\"lname\": \"gowda\",\r\n" + 
				"		\"email\": \"rgowsa@mail.com\",\r\n" + 
				"		\"dob\": \"2012-04-21T18:25:43-05:00\",\r\n" + 
				"		\"role\": \"manager\"\r\n" + 
				"	}\r\n" + 
				"}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/addDepartment").accept(MediaType.APPLICATION_JSON).content(path).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse());
	
}
	
	
		
		
		
	}
