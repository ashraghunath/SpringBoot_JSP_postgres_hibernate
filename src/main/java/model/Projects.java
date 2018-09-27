package model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/**
 * <h1>Projects</h1>
 * @author Ashwin Raghunath
 * @version 1.0
 * @since 3-9-18
 */
@Entity
@Table(name = "Projects")
public class Projects {

	@Id
	@SequenceGenerator(name = "seq-gen", sequenceName = "MY_SEQ_GEN", initialValue = 1, allocationSize = 12)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq-gen")
	@Column(name = "p_id")
	private Integer p_id;

	@ManyToOne
	@JoinColumn(name = "a_id")
	private AvailableProject allocatedProject;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Employee employee;

	public AvailableProject getAllocatedProject() {
		return allocatedProject;
	}

	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;

	public Projects() {
		
	}
	
	public Projects(AvailableProject allocatedProject, Employee employee, Department department) {
		super();
		this.allocatedProject = allocatedProject;
		this.employee = employee;
		this.department = department;
	}

	public void setAllocatedProject(AvailableProject allocatedProject) {
		this.allocatedProject = allocatedProject;
	}

	public Integer getP_id() {
		return p_id;
	}

	public void setP_id(Integer p_id) {
		this.p_id = p_id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}
