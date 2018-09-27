package service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Employee;
import model.Login;
import repository.LoginRepository;

/**
 * <h1>LoginService</h1>
 * 
 * @author Ashwin Raghunath
 * @version 1.0
 * @since 3-9-18
 */

@Service
public class LoginService {
	@Autowired
	private final LoginRepository loginRepository;

	public LoginService(LoginRepository loginRepository) {
		this.loginRepository = loginRepository;
	}

	public void savelogin(Login login) {
		loginRepository.save(login);
	}

	public Optional<Login> getLoginById(Integer user_id) {
		return loginRepository.findById(user_id);
	}

	public Login findBylogin_id(Integer login_id) {
		return loginRepository.findById(login_id).get();

	}

	public void delete(Integer login_id) {
		loginRepository.deleteById(login_id);
	}

	public Login findByemployee(Employee employee) {
		return loginRepository.findByemployee(employee);
	}
}
