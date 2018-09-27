package service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import exceptionHandling.ResourceNotFoundException;
import model.AvailableProject;
import model.Department;
import model.Employee;
import model.Projects;
import repository.AvailableProjectRepository;
import repository.EmployeeRepository;
import repository.ProjectsRepository;

/**
 * <h1>ProjectService</h1>
 * 
 * @author Ashwin Raghunath
 * @version 1.0
 * @since 3-9-18
 */
@Transactional
@Service
public class ProjectService {

	@Autowired
	private final AvailableProjectRepository availableProjectRepository;
	@Autowired
	private final ProjectsRepository projectsRepository;
	@Autowired
	private final DepartmentService departmentService;
	@Autowired
	private final EmployeeRepository employeeRepository;

	public ProjectService(AvailableProjectRepository availableProjectRepository, ProjectsRepository projectsRepository,
			DepartmentService departmentService, EmployeeRepository employeeRepository) {
		this.availableProjectRepository = availableProjectRepository;
		this.projectsRepository = projectsRepository;
		this.departmentService = departmentService;
		this.employeeRepository = employeeRepository;
	}

	public void addNewAvailableProject(String name, Date start_date, Date end_date, String description,
			Integer department_id) throws ResourceNotFoundException {

		try {
			Department department = departmentService.getDepartmentById(department_id);
			AvailableProject availableProject = new AvailableProject(name, start_date, end_date, description,
					department);
			availableProjectRepository.save(availableProject);
		} catch (Exception e) {

			throw new ResourceNotFoundException("Department not found with given ID, Enter correct ID");
		}

	}

	public List<AvailableProject> getAllAvailableProjects() {
		List<AvailableProject> list = availableProjectRepository.findAll();
		return list;
	}

	public Projects getAllProjectsByEmployee(Employee employee) {
		return projectsRepository.findByEmployee(employee);
	}

	public List<Projects> getAllProjects() {
		List<Projects> list = (List<Projects>) projectsRepository.findAll();
		return list;
	}

	public void deleteAvailableProject(Integer a_id) {
		availableProjectRepository.deleteById(a_id);
	}

	public AvailableProject getAvailableProjectById(Integer a_id) {
		return availableProjectRepository.findById(a_id).get();
	}

	public void saveNewAllocation(Integer a_id, Integer user_id) throws ResourceNotFoundException {
		try {
			AvailableProject availableProject = availableProjectRepository.findById(a_id).get();
			Department department = availableProject.getDepartment();
			Employee employee = employeeRepository.findById(user_id).get();
			Projects projectsTempProjects = new Projects(availableProject, employee, department);
			projectsRepository.save(projectsTempProjects);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Incorrect ID entered.");
		}
	}

	public void deleteAllocation(Integer p_id) {
		projectsRepository.deleteById(p_id);
	}

	public List<Projects> findAllByallocatedProject(AvailableProject availableProject) {
		return projectsRepository.findAllByallocatedProject(availableProject);
	}
}
