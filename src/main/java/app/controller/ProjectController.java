package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.repository.ProjectRepository;

@RestController
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	ProjectRepository projectRepository;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity getProjects(){
		return new ResponseEntity(projectRepository.findAll(), HttpStatus.OK);
	}
	

}
