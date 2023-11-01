package in.ketkikule.webtools.ParkIt.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import in.ketkikule.webtools.ParkIt.dao.UserDAO;
import in.ketkikule.webtools.ParkIt.model.Credentials;



public class LoginValidator implements Validator {
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Credentials credentials = (Credentials) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "error.username", "Username is Empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.password", "Password is Empty");
		if (!this.userDAO.doesUserExists(credentials.getUserid())) {
			errors.reject("error.doesNotExist", "User does not exist");
		}
	}

}
