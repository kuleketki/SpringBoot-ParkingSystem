package in.ketkikule.webtools.ParkIt.service;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import in.ketkikule.webtools.ParkIt.dao.UserDAO;
import in.ketkikule.webtools.ParkIt.model.Credentials;
import in.ketkikule.webtools.ParkIt.model.User;

@Service
public class UserService {
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private JavaMailSender javaMailSender;

	public User registerNewUser(User user) throws MessagingException, IOException {
		sendEmail(user.getUserid());
		return this.userDAO.saveUser(user);
	}

	public User login(Credentials credentials) {
		return this.userDAO.areCredentialsValid(credentials);
	}

	public User getUserById(int id) {
		return this.userDAO.getUserById(id);
	}

	public boolean doesUserExist(Credentials credentials) {
		return this.userDAO.doesUserExists(credentials.getUserid());
	}

	public void sendEmail(String email) throws MessagingException, IOException {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(email);
		msg.setSubject("Welcome to Parking App");
		msg.setText("Welcome to ParkIT. You are our valued customer.\n Please feel free to contact customer care in case of queries.");
		
		

		javaMailSender.send(msg);

	}

}
