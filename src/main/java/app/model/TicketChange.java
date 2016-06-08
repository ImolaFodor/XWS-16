package app.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import app.model.Ticket.Priority;
import app.model.Ticket.Status;

@Entity
public class TicketChange {

	@Id
    @GeneratedValue
    private int id;
	
	private String text;
	
	private Date date_time;
	
	private boolean isNameChanged;
	
	private String newName;
	
	private boolean isDescriptionChanged;
	
	private String newDescription;
	
	private boolean isPriorityChanged;
	
	@Enumerated(EnumType.STRING)
	private Priority newPriority;
	
	private boolean isStatusChanged;
	
	@Enumerated(EnumType.STRING)
	private Status newStatus;
	
	private boolean isAssignedChanged;
	
	private String newUserAssigned;
	
	@NotNull
	@ManyToOne
	@JoinColumn
	private User user;
	
	@NotNull
	@ManyToOne
	@JoinColumn
	@JsonIgnore
	private Ticket ticket;
	
	public TicketChange() {
		this.isAssignedChanged = false;
		this.isDescriptionChanged = false;
		this.isNameChanged = false;
		this.isPriorityChanged = false;
		this.isStatusChanged = false;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDatetime() {
		return date_time;
	}

	public void setDatetime(Date datetime) {
		this.date_time = datetime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	
	
	public Date getDate_time() {
		return date_time;
	}

	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}

	public boolean isNameChanged() {
		return isNameChanged;
	}

	public void setNameChanged(boolean isNameChanged) {
		this.isNameChanged = isNameChanged;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public boolean isDescriptionChanged() {
		return isDescriptionChanged;
	}

	public void setDescriptionChanged(boolean isDescriptionChanged) {
		this.isDescriptionChanged = isDescriptionChanged;
	}

	public String getNewDescription() {
		return newDescription;
	}

	public void setNewDescription(String newDescription) {
		this.newDescription = newDescription;
	}

	public boolean isPrioritiyChanged() {
		return isPriorityChanged;
	}

	public void setPriorityChanged(boolean isPrioritiyChanged) {
		this.isPriorityChanged = isPrioritiyChanged;
	}

	public Priority getNewPriority() {
		return newPriority;
	}

	public void setNewPriority(Priority newPriority) {
		this.newPriority = newPriority;
	}

	public boolean isStatusChanged() {
		return isStatusChanged;
	}

	public void setStatusChanged(boolean isStatusChanged) {
		this.isStatusChanged = isStatusChanged;
	}

	public Status getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(Status newStatus) {
		this.newStatus = newStatus;
	}

	public boolean isAssignedChanged() {
		return isAssignedChanged;
	}

	public void setAssignedChanged(boolean isAssignedChanged) {
		this.isAssignedChanged = isAssignedChanged;
	}

	public String getNewUserAssigned() {
		return newUserAssigned;
	}

	public void setNewUserAssigned(String newUserAssigned) {
		this.newUserAssigned = newUserAssigned;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date_time == null) ? 0 : date_time.hashCode());
		result = prime * result + id;
		result = prime * result + (isAssignedChanged ? 1231 : 1237);
		result = prime * result + (isDescriptionChanged ? 1231 : 1237);
		result = prime * result + (isNameChanged ? 1231 : 1237);
		result = prime * result + (isPriorityChanged ? 1231 : 1237);
		result = prime * result + (isStatusChanged ? 1231 : 1237);
		result = prime * result + ((newDescription == null) ? 0 : newDescription.hashCode());
		result = prime * result + ((newName == null) ? 0 : newName.hashCode());
		result = prime * result + ((newPriority == null) ? 0 : newPriority.hashCode());
		result = prime * result + ((newStatus == null) ? 0 : newStatus.hashCode());
		result = prime * result + ((newUserAssigned == null) ? 0 : newUserAssigned.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		TicketChange other = (TicketChange) obj;
		if (date_time == null) {
			if (other.date_time != null)
				return false;
		} else if (!date_time.equals(other.date_time))
			return false;
		if (id != other.id)
			return false;
		if (isAssignedChanged != other.isAssignedChanged)
			return false;
		if (isDescriptionChanged != other.isDescriptionChanged)
			return false;
		if (isNameChanged != other.isNameChanged)
			return false;
		if (isPriorityChanged != other.isPriorityChanged)
			return false;
		if (isStatusChanged != other.isStatusChanged)
			return false;
		if (newDescription == null) {
			if (other.newDescription != null)
				return false;
		} else if (!newDescription.equals(other.newDescription))
			return false;
		if (newName == null) {
			if (other.newName != null)
				return false;
		} else if (!newName.equals(other.newName))
			return false;
		if (newPriority != other.newPriority)
			return false;
		if (newStatus != other.newStatus)
			return false;
		if (newUserAssigned == null) {
			if (other.newUserAssigned != null)
				return false;
		} else if (!newUserAssigned.equals(other.newUserAssigned))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TicketChange [id=" + id + ", text=" + text + ", date_time=" + date_time + ", isNameChanged="
				+ isNameChanged + ", newName=" + newName + ", isDescriptionChanged=" + isDescriptionChanged
				+ ", newDescription=" + newDescription + ", isPriorityChanged=" + isPriorityChanged + ", newPriority="
				+ newPriority + ", isStatusChanged=" + isStatusChanged + ", newStatus=" + newStatus
				+ ", isAssignedChanged=" + isAssignedChanged + ", newUserAssigned=" + newUserAssigned + "]";
	}
	
	
	
}

