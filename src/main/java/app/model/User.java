package app.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import app.model.User.Gender;

@Entity
public class User {

	public enum Role {
		ADMIN, KORISNIK
	}

	public enum Gender {
		MALE, FEMALE
	}

	@Id
	@GeneratedValue
	public int id;

	@NotNull
	private String name;

	@NotNull
	@Column(unique = true)
	private String username;

	@NotNull
	private String password;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Role role = Role.KORISNIK;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	private String email;

	@OneToMany(mappedBy = "ticketCreator", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Ticket> ticketsCreated;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Set<Project> projects;

	@JsonIgnore
	@OneToMany(mappedBy = "ticketAssigned", fetch = FetchType.EAGER)
	private Set<Ticket> ticketsAssigned;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Comment> comments;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<TicketChange> ticketChanges;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Ticket> getTicketsCreated() {
		return ticketsCreated;
	}

	public void setTicketsCreated(Set<Ticket> ticketsCreated) {
		this.ticketsCreated = ticketsCreated;
	}

	public Set<Ticket> getTicketsAssigned() {
		return ticketsAssigned;
	}

	public void setTicketsAssigned(Set<Ticket> ticketsAssigned) {
		this.ticketsAssigned = ticketsAssigned;
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

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}
	
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (gender != other.gender)
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (role != other.role)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", username=" + username + ", password=" + password + ", role="
				+ role + ", email=" + email + "]";
	}

}
