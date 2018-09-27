package repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import model.Department;
import model.Employee;
/**
 * <h1>DepartmentRepository</h1>
 * @author Ashwin Raghunath
 * @version 1.0
 * @since 3-9-18
 */
@Repository
public interface DepartmentRepository extends CrudRepository<Department, Integer> {
	public Department findBymanager(Employee manager);
	public Department findBydepartmentname(String departmentname);
}
