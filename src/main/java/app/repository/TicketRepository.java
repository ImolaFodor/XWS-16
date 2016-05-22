package app.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer>{
	
	@Query("SELECT t FROM Ticket t WHERE t.ticketAssigned.id=:id)")
    public Set<Ticket> findTicketByUserId(@Param("id") int id);
}
