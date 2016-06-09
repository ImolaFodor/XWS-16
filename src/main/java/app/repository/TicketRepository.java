package app.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.model.Project;
import app.model.Ticket;
import app.model.Ticket.Status;
import app.model.User;

public interface TicketRepository extends JpaRepository<Ticket, Integer>{
	
	public Status status = Status.DONE;

	@Query("SELECT t FROM Ticket t WHERE t.ticketAssigned.id=:id)")
    public Set<Ticket> findTicketByUserId(@Param("id") int id);
	
	@Query("SELECT t FROM Ticket t WHERE t.priority=:priority and t.ticketAssigned.id=:id )")
    public Set<Ticket> findTicketByPriority(@Param("id") int id , @Param("priority") app.model.Ticket.Priority priority);
	
	@Query("SELECT t FROM Ticket t WHERE t.project.id=:id)")
    public List<Ticket> findTicketsByProject(@Param("id") int id);
	
	@Query("SELECT count(t) FROM Ticket t WHERE t.project.id=:id GROUP BY t.project.id)")
	public int findTicketByProject(@Param("id") int id);
	
	/*SELECT count(ticket_assigned_id) from ticket
	where project_id=1
	group by project_id;*/
	
	@Query("SELECT count(t) FROM Ticket t WHERE t.project.id=:id GROUP BY t.project.id)") //ukupan broj tiketa po projektu
    public int getTicketByProject(@Param("id") int id);
	
	
	
	//@Query("SELECT count(t) FROM Ticket t WHERE t.project.id=:id and t.ticketAssigned.id=:id_user GROUP BY t.project.id)") //ukupan broj tiketa po projektu za datog korisnika
    public List<Ticket> findTicketByProjectAndTicketAssigned(Project p, User ticketAssigned );
    
    public List<Ticket> findTicketByProjectAndTicketAssignedAndStatus(Project p, User ticketAssigned, app.model.Ticket.Status s );
    
    
    public Set<Ticket> findTicketByProject(Project p);
    
    public List<Ticket> findTicketByProjectAndDateCreated(Project p,Date d);
	
	/*@Query("SELECT t FROM  Ticket t WHERE t.projec.id =:id and t.ticketAssigned.id=:id_user")
	public Set<Ticket> getTicketsByProjectAndAssignedUser(@Param("id") int id, @Param("id_user") int id_user);*/
    
    @Modifying
    @Query("DELETE Ticket t where t.id=:entityId")
    void deleteTicket(@Param("entityId") int entityId);
}
