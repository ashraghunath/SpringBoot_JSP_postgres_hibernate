package model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * <h1>AvailableProject</h1>
 * 
 * @author Ashwin Raghunath
 * @version 1.0
 * @since 3-9-18
 */
@Entity
@Table(name = "AvailableProject")
public class AvailableProject {
	public AvailableProject() {
	}

	public AvailableProject(String name, Date start_date, Date end_date, String description, Department department) {
		this.name = name;
		this.start_date = start_date;
		this.end_date = end_date;
		this.description = description;
		this.department = department;
	}

	@Id
	@SequenceGenerator(name = "seq-gen", sequenceName = "MY_SEQ_GEN", initialValue = 1, allocationSize = 12)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq-gen")
	private Integer a_id;

	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;

	private String name;
	private Date start_date;
	private Date end_date;
	private String description;

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Integer getA_id() {
		return a_id;
	}

	public void setA_id(Integer a_id) {
		this.a_id = a_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
