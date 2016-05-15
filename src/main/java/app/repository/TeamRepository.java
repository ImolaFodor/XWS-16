package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.Team;

public interface TeamRepository extends JpaRepository<Team, Integer>{

}
