package app.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Ticket {

	public enum Priority {
		BLOCKER, CRITICAL, MAJOR, MINOR, TRIVIAL
	}

	public enum Status {
		TO_DO, IN_PROGRESS, VERIFY, DONE
	}

	@Id
	@GeneratedValue
	private int id;

	@NotNull
	private String name;

	@NotNull
	private String description;
	@NotNull
	@Enumerated(EnumType.STRING)
	private Priority priority=Priority.TRIVIAL;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Status status= Status.TO_DO;

	@NotNull
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn
	@JsonManagedReference
	private Project project;

	@NotNull
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn
	@JsonManagedReference
	private User ticketCreator;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn
	@JsonManagedReference
	private User ticketAssigned;

	@OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Comment> comments;

	@OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<TicketChange> ticketChanges;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getTicketCreator() {
		return ticketCreator;
	}

	public void setTicketCreator(User ticketCreator) {
		this.ticketCreator = ticketCreator;
	}

	public User getTicketAssigned() {
		return ticketAssigned;
	}

	public void setTicketAssigned(User ticketAssigned) {
		this.ticketAssigned = ticketAssigned;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Set<TicketChange> getTicketChanges() {
		return ticketChanges;
	}

	public void setTicketChanges(Set<TicketChange> ticketChanges) {
		this.ticketChanges = ticketChanges;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + ((project == null) ? 0 : project.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((ticketAssigned == null) ? 0 : ticketAssigned.hashCode());
		result = prime * result + ((ticketChanges == null) ? 0 : ticketChanges.hashCode());
		result = prime * result + ((ticketCreator == null) ? 0 : ticketCreator.hashCode());
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
		Ticket other = (Ticket) obj;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (priority != other.priority)
			return false;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		if (status != other.status)
			return false;
		if (ticketAssigned == null) {
			if (other.ticketAssigned != null)
				return false;
		} else if (!ticketAssigned.equals(other.ticketAssigned))
			return false;
		if (ticketChanges == null) {
			if (other.ticketChanges != null)
				return false;
		} else if (!ticketChanges.equals(other.ticketChanges))
			return false;
		if (ticketCreator == null) {
			if (other.ticketCreator != null)
				return false;
		} else if (!ticketCreator.equals(other.ticketCreator))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", name=" + name + ", description=" + description + ", project=" + project
				+ ", ticketCreator=" + ticketCreator + ", ticketAssigned=" + ticketAssigned + ", comments=" + comments
				+ ", ticketChanges=" + ticketChanges + ", priority=" + priority + ", status=" + status + "]";
	}

}
