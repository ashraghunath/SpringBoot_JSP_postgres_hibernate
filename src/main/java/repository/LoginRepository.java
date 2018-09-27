package repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import model.Employee;
import model.Login;

/**
 * <h1>LoginRepository</h1>
 * @author Ashwin Raghunath
 * @version 1.0
 * @since 3-9-18
 */
@Repository
public interface LoginRepository extends CrudRepository<Login, Integer>{
	
	public Login findByemployee(Employee employee);
	
}
