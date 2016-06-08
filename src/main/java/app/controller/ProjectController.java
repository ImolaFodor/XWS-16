package app.controller;

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
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity getProjects(){
		return new ResponseEntity(projectRepository.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value ="/addUser/{userId}/{projectId}")
	public ResponseEntity addUserToProject(@PathVariable("userId") int userId, @PathVariable("projectId") int projectId){
		User user = userRepository.findOne(userId);
		if(user == null){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		Project project = projectRepository.findOne(projectId);
		if(project == null){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		project.getUsersOnProject().add(user);
		projectRepository.save(project);
		user.getProjects().add(project);
		userRepository.save(user);
		return new ResponseEntity(HttpStatus.OK);
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{projId}")
	public ResponseEntity getProject(@PathVariable("projId") int projId){
		Project project = projectRepository.findOne(projId);
		if(project == null){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(project, HttpStatus.OK);
	}
	

}
