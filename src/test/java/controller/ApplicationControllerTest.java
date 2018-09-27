package controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import controller.ApplicationController;
import service.LoginService;

/**
 * <h1>ApplicationControllerTest</h1>
 * @author Raksha R
 * @version 1.0
 * @since 18-9-18
 */
@RunWith(SpringRunner.class)
//@SpringBootTest
//@WebMvcTest(controllers = ApplicationController.class)

public class ApplicationControllerTest {


	private MockMvc mockMvc;
	
	

	@Test
	public void contextLoads() {
	}
	
	@Before
    public void setUp() {
     mockMvc = MockMvcBuilders.standaloneSetup(new ApplicationController(null, null, null)).build();
    }
	
	@Test
	public void testwelcome() throws Exception{
		
		 this.mockMvc.perform(get("/welcome"))
       .andExpect(status().isOk())
         .andExpect(view().name("welcomepage"))
        .andDo(print());
	}
	@Test
	public void testregister() throws Exception{
		
		 this.mockMvc.perform(get("/register"))
       .andExpect(status().isOk())
         .andExpect(view().name("welcomepage"))
        .andDo(print());
	}

	@Test
	public void testregisterDept() throws Exception{
		
		 this.mockMvc.perform(get("/registerDepartment"))
       .andExpect(status().isOk())
         .andExpect(view().name("welcomepage"))
        .andDo(print());
	}
	
	@Test
	public void testregisterAvailProj() throws Exception{
		
		 this.mockMvc.perform(get("/registerAvailableProject"))
       .andExpect(status().isOk())
         .andExpect(view().name("welcomepage"))
        .andDo(print());
	}
	
	@Test
	public void testlogin() throws Exception{
		
		 this.mockMvc.perform(get("/login"))
       .andExpect(status().isOk())
         .andExpect(view().name("welcomepage"))
        .andDo(print());
	}
	
	
//	@Test
//	public void testsamplelogin() throws Exception{
//		
//		this.mockMvc.perform(get("/samplelogin").param("user_id", "50").andExpect(status().isOk());
//				
//	}
//}
	
//	@Test
//	public void testdetails() throws Exception{
//		
//		 this.mockMvc.perform(get("/details"))
//       .andExpect(status().isOk())
//         .andExpect(view().name("welcomepage"))
//        .andDo(print());
//	}
	
	@Test
	public void testaccess() throws Exception{
		
		 this.mockMvc.perform(get("/accessDenied"))
       .andExpect(status().isOk())
         .andExpect(view().name("welcomepage"))
        .andDo(print());
	}
	
	
}