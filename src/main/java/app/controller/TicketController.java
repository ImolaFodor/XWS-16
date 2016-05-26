package app.controller;

import java.util.ArrayList;
import java.util.Set;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.Ticket;
import app.repository.TicketRepository;
import app.repository.UserRepository;

@RestController
@RequestMapping("/tickets")
public class TicketController {

	@Autowired
	TicketRepository ticketRepository;
	@Autowired
	UserRepository userRepository;
	
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
		
		int[] users_tickets = new int[(int)userRepository.count()];
		
		ArrayList<Double> percentages = new ArrayList<Double>((int)userRepository.count());
		
		System.out.println(userRepository.count());
		
		for(int i=0; i<userRepository.count(); i++){
			users_tickets[i]=ticketRepository.findTicketByProjectAndUser(pr_id,i);
			System.out.println(users_tickets[i]);
		}
		
		for(int k=0; k<users_tickets.length; k++){
			double percentage= (users_tickets[k]/tot_tickets)*100;
			System.out.println(percentage);
			percentages.add(percentage);
		}	
		
		
		
		return new ResponseEntity(percentages, HttpStatus.OK);
	}
	
	

}
