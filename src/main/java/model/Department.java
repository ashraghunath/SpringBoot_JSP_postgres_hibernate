package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * <h1>Department</h1>
 * 
 * @author Ashwin Raghunath
 * @version 1.0
 * @since 3-9-18
 */
@Entity
@Table(name = "Department")
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "department_id")
	private Integer department_id;

	private String departmentname;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Employee manager;

	public Department() {

	}

	public Department(String departmentname, Employee manager) {
		super();
		this.departmentname = departmentname;
		this.manager = manager;
	}

	public Integer getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(Integer department_id) {
		this.department_id = department_id;
	}

	public String getDepartmentname() {
		return departmentname;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}

	public Employee getEmployee() {
		return manager;
	}

	public void setEmployee(Employee manager) {
		this.manager = manager;
	}

}
