package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * <h1>Login</h1>
 * 
 * @author Ashwin Raghunath
 * @version 1.0
 * @since 3-9-18
 */
@Entity
public class Login {
	@Id
	private Integer login_id;

	@OneToOne
	@JoinColumn(name = "user_id")
	private Employee employee;

	private String password;

	public Login() {
	}

	public Login(Integer login_id, Employee employee, String password) {
		super();
		this.login_id = login_id;
		this.employee = employee;
		this.password = password;
	}

	public Integer getLogin_id() {
		return login_id;
	}

	public void setLogin_id(Integer login_id) {
		this.login_id = login_id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getPasswordString() {
		return password;
	}

	public void setPasswordString(String passwordString) {
		this.password = passwordString;
	}

	@Override
	public String toString() {
		return "UNAME:" + this.getEmployee().getFname() + "\tPASSWORD:" + this.getPasswordString() + "\tROLE:"
				+ this.getEmployee().getRole();

	}

}
