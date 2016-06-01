package app.reportModel;

import java.util.Date;
import java.util.List;

import app.model.Ticket;

public class TicketHistory {
	public TicketHistory() {
		super();
		// TODO Auto-generated constructor stub
	}
	private Date date;
	private List<Ticket> lTicket;
	private int lTicketCount;
	
	public int getlTicketCount() {
		return lTicket.size();
	}
	public void setlTicketCount(int lTicketCount) {
		this.lTicketCount = lTicketCount;
	}
	public TicketHistory(Date date, List<Ticket> lTicket, int lTicketCount) {
		super();
		this.date = date;
		this.lTicket = lTicket;
		this.lTicketCount = lTicketCount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<Ticket> getlTicket() {
		return lTicket;
	}
	public void setlTicket(List<Ticket> lTicket) {
		this.lTicket = lTicket;
	}
}

