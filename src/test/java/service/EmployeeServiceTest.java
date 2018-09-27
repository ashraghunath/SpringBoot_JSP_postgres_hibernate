package service;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import model.Employee;
import repository.EmployeeRepository;
import repository.LoginRepository;

public class EmployeeServiceTest {

	@Mock
	EmployeeRepository employeeRepository;
	@Mock
	LoginRepository loginRepository;

	@InjectMocks
	EmployeeService employeeService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getAllEmployeesTest() {
		when(employeeRepository.findAll()).thenReturn(listEmployees());
		List<Employee> expected = employeeService.getAllEmployees();
		assertThat(2, is(expected.size()));
		assertThat("ashwin", is(expected.get(0).getFname()));
		assertThat("raghunath", is(expected.get(0).getLname()));
		assertThat("shreyas", is(expected.get(1).getFname()));
		assertThat("nayak", is(expected.get(1).getLname()));
	}

	@Test
	public void saveEmployeeTest() {
		employeeRepository.save(employee1());
		verify(employeeRepository, times(1)).save(any(Employee.class));
	}
	
	@Test
	public void findByEmailTest() {
		when(employeeRepository.findByemail("araghunath@teksystems.com")).thenReturn(employee1());
		Employee expectedEmployee = employeeService.findByemail("araghunath@teksystems.com");
		assertThat("ashwin", is(expectedEmployee.getFname()));
		assertThat("raghunath", is(expectedEmployee.getLname()));
		
	}
	
	@Test
	public void getEmployeeById() {
		when(employeeService.getEmployeeById(anyInt())).thenReturn(employee1());
		Employee employee = employeeService.getEmployeeById(1);
		assertThat("ashwin", is(employee.getFname()));
	}
	
	@Test
	public void deleteEmployeeTest() {
		employeeService.deleteEmployee(anyInt());
		verify(employeeRepository, times(1)).deleteById(anyInt());
	}
	private List<Employee> listEmployees() {
		List<Employee> toReturn = new ArrayList<Employee>();
		toReturn.add(employee1());
		toReturn.add(employee2());
		return toReturn;
	}

	private Employee employee1() {
		return new Employee("ashwin", "raghunath", "araghunath@teksystems.com", new Date(1996 - 03 - 03));
	}

	private Employee employee2() {
		return new Employee("shreyas", "nayak", "shnayak@teksystems.com", new Date(1996 - 03 - 03));
	}

}
