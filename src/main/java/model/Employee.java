package model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * <h1>Employee</h1>
 * 
 * @author Ashwin Raghunath
 * @version 1.0
 * @since 3-9-18
 */
@Entity
@Table(name = "Employee")
public class Employee {

	@Id
	@SequenceGenerator(name = "seq-gen", sequenceName = "MY_SEQ_GEN", initialValue = 1, allocationSize = 12)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq-gen")
	@Column(name = "user_id")
	private Integer user_id;
	
	@Size(min=3, message ="first name must be atleast 3 characters")
	private String fname;
	private String lname;
	@Pattern(regexp="[A-Za-z0-9._%-+]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}", message="Enter valid email")
	private String email;
	@Past
	private Date dob;
	private String role;
	@OneToOne
	@JoinColumn(name="id")
	Resume file;
	
	
	public Resume getFile() {
		return file;
	}

	public void setFile(Resume file) {
		this.file = file;
	}

	public Employee() {

	}

	public Employee(String fname, String lname, String email, Date dob) {
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.dob = dob;
		this.role = "";
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Employee [user_id=" + user_id + ", fname=" + fname + ", lname=" + lname + ", email=" + email + ", dob="
				+ dob + ", role=" + role + "]";
	}

}
