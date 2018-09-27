package repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import model.AvailableProject;
import model.Employee;
import model.Projects;
/**
 * <h1>ProjectsRepository</h1>
 * @author Ashwin Raghunath
 * @version 1.0
 * @since 3-9-18
 */
@Repository
public interface ProjectsRepository extends CrudRepository<Projects, Integer>{
	public Projects findByEmployee(Employee employee);
	public List<Projects> findAllByallocatedProject(AvailableProject availableProject);
}
