package app.controller;

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

@RestController
@RequestMapping("/tickets")
public class TicketController {

	@Autowired
	TicketRepository ticketRepository;
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity getTickets(@PathVariable("id") int id){
		Set<Ticket> tickets = ticketRepository.findTicketByUserId(id);
		
		return new ResponseEntity(tickets, HttpStatus.OK);
	}
	

}
