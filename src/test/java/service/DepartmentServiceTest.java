package service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import model.Department;
import model.Employee;
import repository.DepartmentRepository;
import static org.hamcrest.Matchers.is;

public class DepartmentServiceTest {

	@Mock
	DepartmentRepository departmentRepository;

	@Mock
	EmployeeService employeeService;

	@InjectMocks
	DepartmentService departmentService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getAllDepartmentsTest() {
		when(departmentRepository.findAll()).thenReturn(listDepartments());

		List<Department> expected = departmentService.getAllDepartments();
		assertThat(2, is(expected.size()));
		assertThat("department1", is(expected.get(0).getDepartmentname()));
		assertThat("ashwin", is(expected.get(0).getEmployee().getFname()));
		assertThat("raghunath", is(expected.get(0).getEmployee().getLname()));
		assertThat("department2", is(expected.get(1).getDepartmentname()));
		assertThat("shreyas", is(expected.get(1).getEmployee().getFname()));
		assertThat("nayak", is(expected.get(1).getEmployee().getLname()));
	}

	@Test
	public void saveDepartmentTest() {
		departmentService.saveDepartment(department1());
		verify(departmentRepository, times(1)).save(any(Department.class));
	}

//	
	
	@Test
	public void deleteDepartmentTest() {
		departmentService.deleteDepartment(anyInt());
		verify(departmentRepository, times(1)).deleteById(anyInt());
	}


	private List<Department> listDepartments() {
		List<Department> toReturn = new ArrayList<Department>();
		toReturn.add(department1());
		toReturn.add(department2());
		return toReturn;
	}

	private Department department1() {
		return new Department("department1",
				new Employee("ashwin", "raghunath", "araghunath@teksystems.com", new Date(1996 - 03 - 03)));
	}

	private Department department2() {
		return new Department("department2",
				new Employee("shreyas", "nayak", "shnayak@teksystems.com", new Date(1996 - 03 - 03)));
	}
	
//	@Test
//	public void getDepartmentByIdTest() {
//		when(departmentRepository.findById(anyInt())).thenReturn(department1());
//		Department department = departmentService.getDepartmentById(1);
//		assertThat("department1", is(department.getDepartmentname()));
//		
//	}
}
