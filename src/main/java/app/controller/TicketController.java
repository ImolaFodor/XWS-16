package app.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.Comment;
import app.model.Project;
import app.model.Ticket;
import app.model.Ticket.Status;
import app.model.TicketChange;
import app.model.User;
import app.reportModel.ProjectTicketsByUserReport;
import app.reportModel.TicketHistory;
import app.repository.CommentRepository;
import app.repository.ProjectRepository;
import app.repository.TicketChangeRepository;
import app.repository.TicketRepository;
import app.repository.UserRepository;

@RestController
@RequestMapping("/tickets")
public class TicketController {

	@Autowired
	TicketRepository ticketRepository;
	@Autowired
	TicketChangeRepository ticketChangeRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	CommentRepository commentRepository;

	TicketHistory th = new TicketHistory();

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity getTickets(@PathVariable("id") int id) {
		Set<Ticket> tickets = ticketRepository.findTicketByUserId(id);

		return new ResponseEntity(tickets, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/project/{id}")
	public ResponseEntity getTicketByProject(@PathVariable("id") int id) {

		Project project = projectRepository.findOne(id);
		Set<Ticket> tickets = ticketRepository.findTicketByProject(project);
		if (project == null) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(tickets, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}/{priority}")
	public ResponseEntity getTicketsByPriority(@PathVariable("id") int id,
			@PathVariable("priority") app.model.Ticket.Priority priority) {
		Set<Ticket> tickets = ticketRepository.findTicketByPriority(id, priority);

		return new ResponseEntity(tickets, HttpStatus.OK);
	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/{user_id}")
	public ResponseEntity updateTicket(@RequestBody Ticket update, @PathVariable("id") int id,
			@PathVariable("id") User user_id) {
		Ticket t = ticketRepository.findOne(id);
		// User u = userRepository.findOne(user_id);
		if (t == null) {
			return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
		}

		for (Comment comment : update.getComments()) {
			comment.setTicket(update);
		}

		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = (User) authentication.getPrincipal();
		System.out.println("TICKET CHANGE FUNC");
		TicketChange ticketChange = getTicketChange(t, update);
		if (ticketChange != null) {
			ticketChange.setUser(loggedUser);
			ticketChange.setDate_time(new Date());
			update.getTicketChanges().add(ticketChange);
		}

		for (TicketChange tc : update.getTicketChanges()) {
			tc.setTicket(update);
		}
		ticketRepository.save(update);

		return new ResponseEntity(HttpStatus.OK);
	}

	private TicketChange getTicketChange(Ticket oldT, Ticket newT) {
		System.out.println("TICKET CHANGE FUNC");
		boolean isAnythingChanged = false;
		TicketChange ticketChange = new TicketChange();
		if (!oldT.getName().equals(newT.getName())) {
			isAnythingChanged = true;
			ticketChange.setNameChanged(true);
			ticketChange.setNewName(newT.getName());
		}

		if (oldT.getDescription() != null) {
			if (!oldT.getDescription().equals(newT.getDescription())) {
				isAnythingChanged = true;
				ticketChange.setDescriptionChanged(true);
				ticketChange.setNewDescription(newT.getDescription());
			}
		}

		if (!oldT.getPriority().toString().equals(newT.getPriority().toString())) {
			isAnythingChanged = true;
			ticketChange.setPriorityChanged(true);
			ticketChange.setNewPriority(newT.getPriority());
		}

		if (!oldT.getStatus().equals(newT.getStatus())) {
			isAnythingChanged = true;
			ticketChange.setStatusChanged(true);
			ticketChange.setNewStatus(newT.getStatus());
		}
		if (oldT.getTicketAssigned() != null && newT.getTicketAssigned() != null) {
			if (oldT.getTicketAssigned().getId() != newT.getTicketAssigned().getId()) {

				isAnythingChanged = true;
				ticketChange.setAssignedChanged(true);
				ticketChange.setNewUserAssigned(newT.getTicketAssigned().getName());
			}
		}
		if (oldT.getTicketAssigned() == null && newT.getTicketAssigned() != null) {
			isAnythingChanged = true;
			ticketChange.setAssignedChanged(true);
			ticketChange.setNewUserAssigned(newT.getTicketAssigned().getName());
		}
		if (oldT.getTicketAssigned() != null && newT.getTicketAssigned() == null) {
			isAnythingChanged = true;
			ticketChange.setAssignedChanged(true);
			ticketChange.setNewUserAssigned("");
		}

		if (isAnythingChanged) {
			System.out.println(ticketChange);
			return ticketChange;
		} else {
			return null;
		}

	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity saveTicket(@RequestBody Ticket update) {
		Project project = projectRepository.findOne(update.getProject().getId());
		project.setTicketsNum(project.getTicketsNum() + 1);
		projectRepository.save(project);
		ticketRepository.save(update);
		return new ResponseEntity(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/percentages/{pr_id}")
	public ResponseEntity getPercentagesByUserOnProject(@PathVariable("pr_id") int pr_id) {
		
		int tot_tickets = 0;
		try{
			tot_tickets = ticketRepository.findTicketByProject(pr_id);
		}catch(Exception e){
			tot_tickets = 0;
		}
		Project project = projectRepository.findOne(pr_id);

		if (project == null) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		Set<User> allUsers = project.getUsersOnProject();
		int[] users_tickets = new int[(int) allUsers.size()];
		ArrayList<ProjectTicketsByUserReport> allUserReports = new ArrayList<ProjectTicketsByUserReport>();
		int i = 0;
		for (User u : allUsers) {
			ArrayList<Ticket> assignedUserTickets = (ArrayList<Ticket>) ticketRepository
					.findTicketByProjectAndTicketAssigned(project, u);
			users_tickets[i] = assignedUserTickets.size();
			double percentage = ((double) users_tickets[i] / (double) tot_tickets) * 100;
			ProjectTicketsByUserReport ptur = new ProjectTicketsByUserReport(u, percentage, users_tickets[i]);
			allUserReports.add(ptur);
			i++;
		}

		return new ResponseEntity(allUserReports, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/percentagesdone/{pr_id}")
	public ResponseEntity getPercentagesByUserOnProjectDone(@PathVariable("pr_id") int pr_id) {
		int tot_tickets = 0;
		try{
			tot_tickets = ticketRepository.findTicketByProject(pr_id);
		}catch(Exception e){
			tot_tickets = 0;
		}
		Project project = projectRepository.findOne(pr_id);

		if (project == null) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		Set<User> allUsers = project.getUsersOnProject();
		int[] users_tickets = new int[(int) allUsers.size()];
		ArrayList<ProjectTicketsByUserReport> allUserReports = new ArrayList<ProjectTicketsByUserReport>();
		int i = 0;
		Status s = Status.DONE;
		for (User u : allUsers) {
			ArrayList<Ticket> assignedUserTickets = (ArrayList<Ticket>) ticketRepository
					.findTicketByProjectAndTicketAssignedAndStatus(project, u, app.model.Ticket.Status.DONE);
			users_tickets[i] = assignedUserTickets.size();
			double percentage = ((double) users_tickets[i] / (double) tot_tickets) * 100;
			ProjectTicketsByUserReport ptur = new ProjectTicketsByUserReport(u, percentage, users_tickets[i]);
			allUserReports.add(ptur);
			i++;
		}

		return new ResponseEntity(allUserReports, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/projectDone/{projectId}/{date_from}/{date_to}")
	public ResponseEntity getDonePercentegeProject(@PathVariable("projectId") int projectId,
			@PathVariable("date_from") Date dateFrom, @PathVariable("date_to") Date dateTo) {
		Project project = projectRepository.findOne(projectId);

		if (project == null) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}

		int allTicketsNum  =0;
		try{
			 allTicketsNum = ticketRepository.getTicketByProject(projectId);
			
		}catch(Exception e){
			 allTicketsNum =0;
		}
		List<TicketChange> allProjectTChanges = ticketChangeRepository
				.findTicketChangeByProjectAndStatus(project.getId(), Status.DONE);
		Calendar cStart = Calendar.getInstance();
		cStart.setTime(dateFrom);
		Calendar cEnd = Calendar.getInstance();
		cEnd.setTime(dateTo);
		cEnd.add(Calendar.DAY_OF_MONTH, 1);
		Calendar cCurr = Calendar.getInstance();
		ArrayList<ProjectTicketsByUserReport> retVal = new ArrayList<ProjectTicketsByUserReport>();
		retVal = getProjectTicketsByReport(allProjectTChanges, cStart, cEnd, allTicketsNum);

		return new ResponseEntity(retVal, HttpStatus.OK);

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/projectDone/{projectId}/{userId}/{date_from}/{date_to}")
	public ResponseEntity getDonePercentegeProjectUser(@PathVariable("projectId") int projectId, @PathVariable("userId") int userId,
			@PathVariable("date_from") Date dateFrom, @PathVariable("date_to") Date dateTo) {
		Project project = projectRepository.findOne(projectId);

		if (project == null) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		
		int allTicketsNum  =0;
		try{
			 allTicketsNum = ticketRepository.getTicketByProject(projectId);
			
		}catch(Exception e){
			 allTicketsNum =0;
		}
		List<TicketChange> allProjectTChanges = ticketChangeRepository
				.findTicketChangeByProjectAndStatusAndUser(project.getId(), userId, Status.DONE);
		Calendar cStart = Calendar.getInstance();
		cStart.setTime(dateFrom);
		Calendar cEnd = Calendar.getInstance();
		cEnd.setTime(dateTo);
		cEnd.add(Calendar.DAY_OF_MONTH, 1);
		Calendar cCurr = Calendar.getInstance();
		ArrayList<ProjectTicketsByUserReport> retVal = new ArrayList<ProjectTicketsByUserReport>();
		retVal = getProjectTicketsByReport(allProjectTChanges, cStart, cEnd, allTicketsNum);

		return new ResponseEntity(retVal, HttpStatus.OK);

	}

	@SuppressWarnings("deprecation")
	@RequestMapping(method = RequestMethod.GET, value = "/dates/{pr_id}/{date_from}/{date_to}")
	public ResponseEntity getTicketHistory(@PathVariable("pr_id") int pr_id, @PathVariable("date_from") Date dateFrom,
			@PathVariable("date_to") Date dateTo) {

		List<Ticket> allProjectTickets = ticketRepository.findTicketsByProject(pr_id);
		List<TicketHistory> lth = new ArrayList<>();

		List<TicketHistory> retVal = new ArrayList<>();
		Calendar cStart = Calendar.getInstance();
		cStart.setTime(dateFrom);
		Calendar cEnd = Calendar.getInstance();
		cEnd.setTime(dateTo);
		cEnd.add(Calendar.DAY_OF_MONTH, 1);
		int num = 0;
		while (cStart.getTime().before(cEnd.getTime()) || num > 50) {

			TicketHistory ticketH = new TicketHistory();
			ticketH.setDate(cStart.getTime());
			ArrayList<Ticket> ticketsByDate = new ArrayList<Ticket>();
			for (Ticket t : allProjectTickets) {
				if (t.getDateCreated().getDay() == cStart.getTime().getDay()
						&& t.getDateCreated().getMonth() == cStart.getTime().getMonth()
						&& t.getDateCreated().getYear() == cStart.getTime().getYear()) {
					ticketsByDate.add(t);

				}
			}
			ticketH.setlTicket(ticketsByDate);
			ticketH.setlTicketCount(ticketH.getlTicket().size());
			System.out.println("Date: " + ticketH.getDate().toString() + " size: " + ticketH.getlTicketCount());
			retVal.add(ticketH);
			cStart.add(Calendar.DAY_OF_MONTH, 1);
			num++;
		}

		return new ResponseEntity(retVal, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/comment/{id}")
	public ResponseEntity deleteComment(@PathVariable("id") int id) {
		Comment comment = commentRepository.findOne(id);
		if (comment == null) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}

		commentRepository.delete(comment);
		return new ResponseEntity(HttpStatus.OK);

	}

	private ArrayList<ProjectTicketsByUserReport> getProjectTicketsByReport(List<TicketChange> allProjectTChanges,
			Calendar cStart, Calendar cEnd, int allTicketsNum) {
		Calendar cCurr = Calendar.getInstance();
		ArrayList<ProjectTicketsByUserReport> retVal = new ArrayList<ProjectTicketsByUserReport>();
		while (cStart.getTime().before(cEnd.getTime())) {
			ProjectTicketsByUserReport ptbur = new ProjectTicketsByUserReport();
			ptbur.setDate(cStart.getTime());
			ArrayList<TicketChange> tcDate = new ArrayList<TicketChange>();

			for (TicketChange t : allProjectTChanges) {
				cCurr.setTime(t.getDate_time());
				if (cCurr.get(Calendar.DAY_OF_MONTH) == cStart.get(Calendar.DAY_OF_MONTH)
						&& cCurr.get(Calendar.MONTH) == cStart.get(Calendar.MONTH)
						&& cCurr.get(Calendar.YEAR) == cStart.get(Calendar.YEAR)) {
					tcDate.add(t);
				}
			}
			double percentage = ((double) tcDate.size() / (double) allTicketsNum) * 100;
			ptbur.setNumberOfTickets(tcDate.size());
			ptbur.setPercentege(percentage);
			retVal.add(ptbur);
			cStart.add(Calendar.DAY_OF_MONTH, 1);
		}

		return retVal;
	}

}
