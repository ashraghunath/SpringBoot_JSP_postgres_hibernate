package repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import model.Employee;
/**
 * <h1>EmployeeRepository</h1>
 * @author Ashwin Raghunath
 * @version 1.0
 * @since 3-9-18
 */
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer>{
		
	public Employee findByemail(String email);
}
