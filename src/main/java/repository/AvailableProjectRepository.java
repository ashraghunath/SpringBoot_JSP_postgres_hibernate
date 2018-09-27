package repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import model.AvailableProject;
/**
 * <h1>AvailableProjectRepository</h1>
 * @author Ashwin Raghunath
 * @version 1.0
 * @since 3-9-18
 */
@Repository
public interface AvailableProjectRepository extends CrudRepository<AvailableProject, Integer>{
	@Override
	List<AvailableProject> findAll();
	
	@Override
	Optional<AvailableProject> findById(Integer id);
	
	
}