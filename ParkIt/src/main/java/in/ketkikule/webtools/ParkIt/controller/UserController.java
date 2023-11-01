package in.ketkikule.webtools.ParkIt.controller;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import in.ketkikule.webtools.ParkIt.exception.UserException;
import in.ketkikule.webtools.ParkIt.model.Credentials;
import in.ketkikule.webtools.ParkIt.model.User;
import in.ketkikule.webtools.ParkIt.service.UserService;
import in.ketkikule.webtools.ParkIt.validator.LoginValidator;

@Controller
@RequestMapping("/")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@PostMapping("/users")
	@ResponseBody
	public ResponseEntity<Object> createUser(@Validated @RequestBody User user) throws UserException, MessagingException, IOException {
		User newUser = userService.registerNewUser(user);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", "Successfull");
		map.put("status", HttpStatus.OK);
		map.put("data", user);
		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}

	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<Object> login(@RequestBody Credentials credentials) throws UserException {
		if (userService.doesUserExist(credentials)) {
			User user = userService.login(credentials);
			if (user != null) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("message", "Successfull");
				map.put("status", HttpStatus.OK);
				map.put("data", user);
				return new ResponseEntity<Object>(map, HttpStatus.OK);
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("message", "Incorrect Credentials");
				map.put("status", HttpStatus.UNAUTHORIZED);
				return new ResponseEntity<Object>(map, HttpStatus.UNAUTHORIZED);
			}
		}else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("message", "User does not exist");
			map.put("status", HttpStatus.UNAUTHORIZED);
			return new ResponseEntity<Object>(map, HttpStatus.UNAUTHORIZED);
		}
	}
}
