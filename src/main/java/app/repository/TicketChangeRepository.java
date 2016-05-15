package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.TicketChange;

public interface TicketChangeRepository extends JpaRepository<TicketChange, Integer>{

}
