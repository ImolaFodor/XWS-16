package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.Comment;
import app.model.Project;
import app.model.Ticket;
import app.model.TicketChange;
import app.model.User;
import app.repository.CommentRepository;
import app.repository.ProjectRepository;
import app.repository.TicketChangeRepository;
import app.repository.TicketRepository;
import app.repository.UserRepository;

@RestController
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	TicketRepository ticketRepository;
	@Autowired
	TicketChangeRepository ticketChangeRepository;
	@Autowired
	CommentRepository commentRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity getProjects() {
		return new ResponseEntity(projectRepository.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insertProject(@RequestBody Project project){
		projectRepository.save(project);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity updateProject(@RequestBody Project update){
		Project project = projectRepository.findOne(update.getId());
		if(project == null){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		
		project.setName(update.getName());
		project.setDetails(update.getDetails());
		projectRepository.save(project);
		return new ResponseEntity(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/addUser/{userId}/{projectId}")
	public ResponseEntity addUserToProject(@PathVariable("userId") int userId,
			@PathVariable("projectId") int projectId) {
		User user = userRepository.findOne(userId);
		if (user == null) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		Project project = projectRepository.findOne(projectId);
		if (project == null) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		project.getUsersOnProject().add(user);
		projectRepository.save(project);
		user.getProjects().add(project);
		userRepository.save(user);
		return new ResponseEntity(HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/{projId}")
	public ResponseEntity getProject(@PathVariable("projId") int projId) {
		Project project = projectRepository.findOne(projId);
		if (project == null) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(project, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/removeUser/{userId}/{projectId}")
	public ResponseEntity removeUserFromProject(@PathVariable("userId") int userId,
			@PathVariable("projectId") int projectId) {
		User user = userRepository.findOne(userId);
		if (user == null) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		Project project = projectRepository.findOne(projectId);
		if (project == null) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		project.getUsersOnProject().remove(user);
		projectRepository.save(project);
		user.getProjects().remove(project);
		userRepository.save(user);
		return new ResponseEntity(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/removeTicket/{ticketId}/{projectId}")
	public ResponseEntity removeTicketFromProject(@PathVariable("ticketId") int ticketId,
			@PathVariable("projectId") int projectId) {
		System.out.println("DELEEETEEE");
		Ticket ticket = ticketRepository.findOne(ticketId);
		if (ticket == null) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}

		Project project = projectRepository.findOne(projectId);
		if (project == null) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		// ticketRepository.deleteTicket(ticketId);
				for(Comment c: ticket.getComments()){
					c.setTicket(null);
					commentRepository.save(c);
					ticket.getComments().remove(c);
					commentRepository.delete(c);
				}
				
				for(TicketChange tc: ticket.getTicketChanges()){
					ticket.getTicketChanges().remove(tc);
					tc.setTicket(null);
					ticketChangeRepository.save(tc);
					ticketChangeRepository.delete(tc);
				}
		
		//project.getProjectTickets().remove(ticket);
		Ticket found = new Ticket();
		for(Ticket t: project.getProjectTickets()){
			if(t.getId() == ticket.getId()){
				found = t;
				System.out.println("DEEELEETEEEEEEEEE");
			}
		}
		ticketRepository.save(ticket);
		project.getProjectTickets().remove(found);
		projectRepository.save(project);
		ticketRepository.delete(ticket);
		return new ResponseEntity(HttpStatus.OK);
	}

}
