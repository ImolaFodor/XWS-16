package app.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.Project;
import app.model.Ticket;
import app.model.TicketChange;
import app.model.User;
import app.reportModel.TicketHistory;
import app.repository.ProjectRepository;
import app.repository.TicketChangeRepository;
import app.repository.TicketRepository;
import app.repository.UserRepository;

@RestController
@RequestMapping("/ticketchanges")
public class TicketChangeController {

	@Autowired
	TicketChangeRepository ticketChangeRepository;
	
	@Autowired
	TicketRepository ticketRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ProjectRepository projectRepository;
	
	
	@SuppressWarnings("deprecation")
	@RequestMapping(method = RequestMethod.GET, value = "/dates/{pr_id}/{user_id}/{date_from}/{date_to}/{rep_no}")
	public ResponseEntity getTicketDoneHistory(@PathVariable("pr_id") int pr_id,@PathVariable("user_id") User u, @PathVariable("date_from") Date dateFrom, @PathVariable("date_to") Date dateTo, @PathVariable("rep_no") int rep_no){
		Project project = projectRepository.findOne(pr_id);
		List<Ticket> allProjectTickets= new ArrayList<Ticket>();
		
		if(rep_no==4) {
			allProjectTickets = ticketChangeRepository.findTicketByProjectAndStatus(pr_id, app.model.Ticket.Status.DONE );
		}
		else if(rep_no==5) {
			allProjectTickets = ticketChangeRepository.findTicketByProjectAndTicketAssignedAndStatus(pr_id,app.model.Ticket.Status.DONE, u.getId());
		}
		
		
		
		List<TicketHistory> lth= new ArrayList<>();
		
		List<TicketHistory> retVal = new ArrayList<>();
		Calendar cStart = Calendar.getInstance();
		cStart.setTime(dateFrom);
		Calendar cEnd = Calendar.getInstance();
		cEnd.setTime(dateTo);
		cEnd.add(Calendar.DAY_OF_MONTH, 1);
		int num = 0;
		while(cStart.getTime().before(cEnd.getTime()) || num>50){
			
			TicketHistory ticketH = new TicketHistory();
			ticketH.setDate(cStart.getTime());
			ticketH.setlTicketCount(4);
			ArrayList<Ticket> ticketsByDate = new ArrayList<Ticket>();
			for(Ticket t: allProjectTickets){
				if(t.getDateCreated().getDay() == cStart.getTime().getDay() && t.getDateCreated().getMonth() == cStart.getTime().getMonth() 
						&& t.getDateCreated().getYear() == cStart.getTime().getYear()){
					ticketsByDate.add(t);
					
				}
			}
			ticketH.setlTicket(ticketsByDate);
			ticketH.setlTicketCount(ticketH.getlTicket().size());
			System.out.println("Date: "+ticketH.getDate().toString()+" size: "+ticketH.getlTicketCount());
			retVal.add(ticketH);
			cStart.add(Calendar.DAY_OF_MONTH, 1);
			num++;
		}
		
		return new ResponseEntity(retVal, HttpStatus.OK);
	}
	

}
