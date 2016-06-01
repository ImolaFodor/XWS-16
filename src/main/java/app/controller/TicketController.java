package app.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.Project;
import app.model.Ticket;
import app.model.Ticket.Status;
import app.model.User;
import app.reportModel.ProjectTicketsByUserReport;
import app.reportModel.TicketHistory;
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
	
	TicketHistory th= new TicketHistory();
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity getTickets(@PathVariable("id") int id){
		Set<Ticket> tickets = ticketRepository.findTicketByUserId(id);
		
		return new ResponseEntity(tickets, HttpStatus.OK);
	}
	@RequestMapping(method = RequestMethod.GET, value = "/project/{id}")
	public ResponseEntity getTicketByProject(@PathVariable("id") int id){
		
		Project project = projectRepository.findOne(id);
		Set<Ticket> tickets = ticketRepository.findTicketByProject(project);
		if(project == null){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(tickets, HttpStatus.OK);
	}
	@RequestMapping(method = RequestMethod.GET, value = "/{id}/{priority}")
	public ResponseEntity getTicketsByPriority(@PathVariable("id") int id, @PathVariable("priority") app.model.Ticket.Priority priority){
		Set<Ticket> tickets = ticketRepository.findTicketByPriority(id, priority);
		
		return new ResponseEntity(tickets, HttpStatus.OK);
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity updateTicket(@RequestBody Ticket update, @PathVariable("id") int id){
		Ticket t = ticketRepository.findOne(id);
		if(t == null){
			return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		ticketRepository.save(update);
		return new ResponseEntity(HttpStatus.OK);
	}
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity updateTicket(@RequestBody Ticket update){
		ticketRepository.save(update);
		return new ResponseEntity(HttpStatus.OK);
	}
	@RequestMapping(method = RequestMethod.GET, value = "/percentages/{pr_id}")
	public ResponseEntity getPercentagesByUserOnProject(@PathVariable("pr_id") int pr_id){
		int tot_tickets = ticketRepository.getTicketByProject(pr_id);
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
		int tot_tickets = ticketRepository.getTicketByProject(pr_id);
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
	
	@RequestMapping(method = RequestMethod.GET, value = "/dates/{pr_id}/{date_from}/{date_to}")
	public ResponseEntity getTicketHistory(@PathVariable("pr_id") int pr_id, @PathVariable("date_from") String s_date_from, @PathVariable("date_to") String s_date_to){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date_from=new Date();
		try {
			date_from = format.parse(s_date_from);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		Date date_to=new Date();
		try {
			date_to = format2.parse(s_date_to);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Ticket> lt = ticketRepository.findTicketsByProject(pr_id);
		List<TicketHistory> lth= new ArrayList<>();
		
		for(Ticket t : lt){
				if(t.getDateCreated().before(date_to) && t.getDateCreated().after(date_from)){
					List<Ticket> ltd=ticketRepository.findTicketByProjectAndDateCreated(projectRepository.findOne(pr_id),t.getDateCreated());
					th= new TicketHistory(t.getDateCreated(), ltd, ltd.size());
					
					boolean sadrzi_vec=false;
					for (TicketHistory th2: lth){
						if(th2.getDate().equals(t.getDateCreated())){
							sadrzi_vec=true;
						}
					}
					
					if(!sadrzi_vec){
						lth.add(th);
					}
					
				}
		}
		System.out.println(lth);
		
		return new ResponseEntity(lth, HttpStatus.OK);
	}

}
