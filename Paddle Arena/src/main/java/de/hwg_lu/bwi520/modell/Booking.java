package de.hwg_lu.bwi520.modell;

import java.time.LocalDateTime;

public class Booking {

	public Booking(int bookingId, User user, Courts court, LocalDateTime start, LocalDateTime ende) {
		super();
		this.bookingId = bookingId;
		this.user = user;
		this.court = court;
		Start = start;
		Ende = ende;
	}
	private int bookingId;
	public int getBookingId() {
		return bookingId;
	}
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Courts getCourt() {
		return court;
	}
	public void setCourt(Courts court) {
		this.court = court;
	}
	public LocalDateTime getStart() {
		return Start;
	}
	public void setStart(LocalDateTime start) {
		Start = start;
	}
	public LocalDateTime getEnde() {
		return Ende;
	}
	public void setEnde(LocalDateTime ende) {
		Ende = ende;
	}
	private User user;
	private Courts court;
	private LocalDateTime Start;
	private LocalDateTime Ende;
	
}
