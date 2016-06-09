package app.repository;

import java.util.List;

import org.jboss.logging.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
	
	@Query(value = "select * from project p, project_users_on_project pu where p.id = pu.project_id and pu.users_on_project_id =?1", nativeQuery = true)
	List<Project> getProjectsByUserId(int id);
}
