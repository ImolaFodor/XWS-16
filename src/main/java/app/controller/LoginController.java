package app.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.User;
import app.repository.UserRepository;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	UserRepository userRepository;

	@RequestMapping(method = RequestMethod.POST, value = "/loginUser/{username}/{password}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity login(@PathVariable("username") String username, @PathVariable("password") String password) {

		System.out.println("user:" + username);
		User userTry = userRepository.findByUsername(username);

		if (userTry == null) {
			return new ResponseEntity("NO_USER", HttpStatus.BAD_REQUEST);
		} else {
			if (userTry.getPassword().equals(password)) {

				final Collection<GrantedAuthority> authorities = new ArrayList<>();
				authorities.add(new SimpleGrantedAuthority(userTry.getRole().name()));
				final Authentication authentication = new PreAuthenticatedAuthenticationToken(userTry, null,
						authorities);
				SecurityContextHolder.getContext().setAuthentication(authentication);

				return new ResponseEntity(HttpStatus.OK);
			} else {
				return new ResponseEntity("BAD_PASSWORD", HttpStatus.BAD_REQUEST);
			}
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/registerUser", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity registerUser(@RequestBody User user) {
		if (userRepository.findByUsername(user.getUsername()) != null) {
			return new ResponseEntity("USER_EXISTS", HttpStatus.BAD_REQUEST);
		}
		// Generate token
		userRepository.save(user);
		return new ResponseEntity(HttpStatus.CREATED);

	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(method = RequestMethod.GET, value = "/me")
	public ResponseEntity getProfile() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return new ResponseEntity<>(authentication.getPrincipal(), HttpStatus.OK);
	}

}
