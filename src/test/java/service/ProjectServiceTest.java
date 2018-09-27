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

import exceptionHandling.ResourceNotFoundException;
import model.AvailableProject;
import model.Department;
import model.Employee;
import model.Projects;
import repository.AvailableProjectRepository;
import repository.EmployeeRepository;
import repository.ProjectsRepository;

public class ProjectServiceTest {

	@Mock
	AvailableProjectRepository availableProjectRepository;
	@Mock
	ProjectsRepository projectsRepository;
	@Mock
	DepartmentService departmentService;
	@Mock
	EmployeeRepository employeeRepository;
	
	@InjectMocks
	ProjectService projectService;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getAllAvailableProjectsTest() {
		when(availableProjectRepository.findAll()).thenReturn(listAvailable());
		List<AvailableProject> expected = projectService.getAllAvailableProjects();
		assertThat(2, is(expected.size()));
		assertThat("project1", is(expected.get(0).getName()));
		assertThat("description1", is(expected.get(0).getDescription()));
		assertThat("department1", is(expected.get(1).getDepartment().getDepartmentname()));
	}
	
	@Test
	public void deleteAvailableProjectTest() {
		projectService.deleteAvailableProject(anyInt());
		verify(availableProjectRepository, times(1)).deleteById(anyInt());
	}
	
	@Test
	public void deleteAllocationTest() {
		projectService.deleteAllocation(anyInt());
		verify(projectsRepository, times(1)).deleteById(anyInt());
	}
	
	@Test
	public void addNewAvailableProjectTest() throws ResourceNotFoundException {
		projectService.addNewAvailableProject("dept1",new Date(1996-03-03), new Date(1996-03-03), "description1", 1);
		verify(availableProjectRepository, times(1)).save(any(AvailableProject.class));
	}
	
	@Test
	public void getAllProjectsByEmployeeTest() {
		when(projectService.getAllProjectsByEmployee(employee())).thenReturn(project());
		Projects project = projectService.getAllProjectsByEmployee(employee());
		assertThat("project1", is(project.getAllocatedProject().getName()));
	}
	
	private List<AvailableProject> listAvailable() {
		List<AvailableProject> toReturn = new ArrayList<AvailableProject>();
		toReturn.add(available1());
		toReturn.add(available2());
		return toReturn;
	}

	private AvailableProject available1() {
		return new AvailableProject("project1", new Date(1996-03-03), new Date(1996-03-03), "description1", department());
	}

	private AvailableProject available2() {
		return new AvailableProject("project2", new Date(1996-03-03), new Date(1996-03-03), "description2", department());
	}
	
	private Department department() {
		return new Department("department1",
				new Employee("ashwin", "raghunath", "araghunath@teksystems.com", new Date(1996 - 03 - 03)));
	}
	
	private Employee employee() {
		return new Employee("ashwin", "raghunath", "araghunath@teksystems.com", new Date(1996 - 03 - 03));
	}
	
	private Projects project() {
		return new Projects(available1(), employee(), department());
	}
}
