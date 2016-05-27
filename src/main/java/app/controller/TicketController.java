package app.controller;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.Project;
import app.model.Ticket;
import app.model.Ticket.Status;
import app.model.User;
import app.reportModel.ProjectTicketsByUserReport;
import app.repository.ProjectRepository;
import app.repository.TicketRepository;
import app.repository.UserRepository;

@RestController
@RequestMapping("/tickets")
public class TicketController {

	@Autowired
	TicketRepository ticketRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ProjectRepository projectRepository;
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity getTickets(@PathVariable("id") int id){
		Set<Ticket> tickets = ticketRepository.findTicketByUserId(id);
		
		return new ResponseEntity(tickets, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}/{priority}")
	public ResponseEntity getTicketsByPriority(@PathVariable("id") int id, @PathVariable("priority") app.model.Ticket.Priority priority){
		Set<Ticket> tickets = ticketRepository.findTicketByPriority(id, priority);
		
		return new ResponseEntity(tickets, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/percentages/{pr_id}")
	public ResponseEntity getPercentagesByUserOnProject(@PathVariable("pr_id") int pr_id){
		int tot_tickets = ticketRepository.findTicketByProject(pr_id);
		Project project = projectRepository.findOne(pr_id);
		
		if(project == null){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		Set<User> allUsers = project.getUsersOnProject();
		int[] users_tickets = new int[(int)allUsers.size()];
		ArrayList<ProjectTicketsByUserReport> allUserReports = new ArrayList<ProjectTicketsByUserReport>();
		int i =0;
		for(User u: allUsers){
			ArrayList<Ticket> assignedUserTickets = (ArrayList<Ticket>) ticketRepository.findTicketByProjectAndTicketAssigned(project, u);
			users_tickets[i]= assignedUserTickets.size();
			double percentage= ((double)users_tickets[i]/(double)tot_tickets)*100;
			ProjectTicketsByUserReport ptur = new ProjectTicketsByUserReport(u, percentage, users_tickets[i]);
			allUserReports.add(ptur);
			i++;
		}
		
		return new ResponseEntity(allUserReports, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/percentagesdone/{pr_id}")
	public ResponseEntity getPercentagesByUserOnProjectDone(@PathVariable("pr_id") int pr_id){
		int tot_tickets = ticketRepository.findTicketByProject(pr_id);
		Project project = projectRepository.findOne(pr_id);
		
		if(project == null){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		Set<User> allUsers = project.getUsersOnProject();
		int[] users_tickets = new int[(int)allUsers.size()];
		ArrayList<ProjectTicketsByUserReport> allUserReports = new ArrayList<ProjectTicketsByUserReport>();
		int i =0;
		Status s = Status.DONE;
		for(User u: allUsers){
			ArrayList<Ticket> assignedUserTickets = (ArrayList<Ticket>) ticketRepository.findTicketByProjectAndTicketAssignedAndStatus(project, u, app.model.Ticket.Status.DONE);
			users_tickets[i]= assignedUserTickets.size();
			double percentage= ((double)users_tickets[i]/(double)tot_tickets)*100;
			ProjectTicketsByUserReport ptur = new ProjectTicketsByUserReport(u, percentage, users_tickets[i]);
			allUserReports.add(ptur);
			i++;
		}
		
		return new ResponseEntity(allUserReports, HttpStatus.OK);
	}
	
	

}
