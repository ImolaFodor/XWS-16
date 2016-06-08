package app.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Project {

	@Id
	@GeneratedValue
	private int id;
	
	@NotNull
	private String label;
	
	private int ticketsNum =0;
	@NotNull
	private String name;

	private String details;

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Ticket> projectTickets;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<User> usersOnProject;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Set<Ticket> getProjectTickets() {
		return projectTickets;
	}

	public void setProjectTickets(Set<Ticket> projectTickets) {
		this.projectTickets = projectTickets;
	}

	public Set<User> getUsersOnProject() {
		return usersOnProject;
	}

	public void setUsersOnProject(Set<User> usersOnProject) {
		this.usersOnProject = usersOnProject;
	}
	

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getTicketsNum() {
		return ticketsNum;
	}

	public void setTicketsNum(int ticketsNum) {
		this.ticketsNum = ticketsNum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((details == null) ? 0 : details.hashCode());
		result = prime * result + id;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ticketsNum;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (details == null) {
			if (other.details != null)
				return false;
		} else if (!details.equals(other.details))
			return false;
		if (id != other.id)
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (ticketsNum != other.ticketsNum)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", label=" + label + ", ticketsNum=" + ticketsNum + ", name=" + name + ", details="
				+ details + "]";
	}

}
