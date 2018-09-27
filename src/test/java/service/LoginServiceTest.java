package service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Date;
import java.util.Optional;

import static org.mockito.Mockito.when;

import static org.hamcrest.Matchers.is;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import model.Employee;
import model.Login;
import repository.LoginRepository;

public class LoginServiceTest {

	@Mock
	LoginRepository loginRepository;
	
	@InjectMocks
	LoginService loginService;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void saveLoginTest() {
		loginRepository.save(login());
		verify(loginRepository, times(1)).save(any(Login.class));
	}
	
//	@Test
//	public void findBylogin_idTest() {
//	when(loginRepository.findById(1)).thenReturn(login());
//	Login expectedLogin = loginService.findBylogin_id(1);
//	assertThat("password1", is(expectedLogin.getPasswordString()));
//	assertThat("ashwin", is(expectedLogin.getEmployee().getFname()));
//	}
	
	@Test
	public void findByemployeeTest() {
		when(loginRepository.findByemployee(employee())).thenReturn(login());
		Login expected = loginService.findByemployee(employee());
		assertThat("ashwin", is(expected.getEmployee().getFname()));
//		assertThat(1, is(expected.getLogin_id()));
	}
	
//	@Test
//	public void delete() {
//		when(loginRepository.deleteById(1)).thenReturn(login());
//		verify(loginRepository.deleteById((Integer) any()), times(1)).save(any(Login.class));
//	}
	
	private Login login() {
		return new Login(1, employee(), "password1");
	}
	
	private Employee employee() {
		return new Employee("ashwin", "raghunath", "araghunath@teksystems.com", new Date(1996 - 03 - 03));
	}
}

