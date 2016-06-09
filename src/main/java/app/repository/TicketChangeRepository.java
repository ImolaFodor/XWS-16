package app.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.model.Ticket;
import app.model.TicketChange;

public interface TicketChangeRepository extends JpaRepository<TicketChange, Integer>{
	
	@Query("SELECT t.ticket FROM TicketChange t WHERE t.ticket.project.id=:id and t.ticket.status=:status )")
    public List<Ticket> findTicketByProjectAndStatus(@Param("id") int id , @Param("status") app.model.Ticket.Status status);
	
	@Query("SELECT t.ticket FROM TicketChange t WHERE t.ticket.project.id=:id and t.ticket.status=:status and t.ticket.ticketAssigned.id=:user_id )")
    public List<Ticket> findTicketByProjectAndTicketAssignedAndStatus(@Param("id") int id , @Param("status") app.model.Ticket.Status status,@Param("user_id") int user_id );
	
	@Query("SELECT t FROM TicketChange t WHERE t.newStatus=:status and t.ticket.project.id =:id")
	public List<TicketChange> findTicketChangeByProjectAndStatus(@Param("id") int id , @Param("status") app.model.Ticket.Status status);
	
	@Query("SELECT t FROM TicketChange t WHERE t.newStatus=:status and t.ticket.project.id =:id and t.ticket.ticketAssigned.id=:userId")
	public List<TicketChange> findTicketChangeByProjectAndStatusAndUser(@Param("id") int id ,@Param("userId") int userId, @Param("status") app.model.Ticket.Status status);
}
