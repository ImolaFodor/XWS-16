package app.reportModel;

import app.model.User;

public class ProjectTicketsByUserReport {
	private java.util.Date date;
	private User user;
	private double percentege;
	private int numberOfTickets;
	
	public ProjectTicketsByUserReport(User user, double percentege, int numberOfTickets) {
		super();
		this.user = user;
		this.percentege = percentege;
		this.numberOfTickets = numberOfTickets;
	}
	public ProjectTicketsByUserReport() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public double getPercentege() {
		return percentege;
	}
	public void setPercentege(double percentege) {
		this.percentege = percentege;
	}
	public int getNumberOfTickets() {
		return numberOfTickets;
	}
	public void setNumberOfTickets(int numberOfTickets) {
		this.numberOfTickets = numberOfTickets;
	}
	public java.util.Date getDate() {
		return date;
	}
	public void setDate(java.util.Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "ProjectTicketsByUserReport [date=" + date + ", user=" + user + ", percentege=" + percentege
				+ ", numberOfTickets=" + numberOfTickets + "]";
	}
	
	
	
}
