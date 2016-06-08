package app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.Project;
import app.model.User;
import app.repository.ProjectRepository;
import app.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	ProjectRepository projectRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity getProjects(){
		return new ResponseEntity(userRepository.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "getFreeUsers/{projectId}")
	public ResponseEntity getFreeUsers(@PathVariable("projectId") int projectId){
		
		Project project = projectRepository.findOne(projectId);
		if(project == null){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		List<User> allUsers = userRepository.findAll();
		ArrayList<User> freeUsers = new ArrayList<User>();
		
		boolean isFree = true;
		for(User u: allUsers){
			for(Project p: u.getProjects()){
				
			}
		}
		
		return null;
	}
	
	@RequestMapping(method =RequestMethod.GET, value = "/getAllUsers/")
	public ResponseEntity getAllUsers(){
		
		return new ResponseEntity(userRepository.findAll(), HttpStatus.OK);
	}

}
