package de.hwg_lu.bwi520.modell;

import java.time.LocalDateTime;

public class Booking {

    private int bookingId;
    private User user;
    private Courts court;
    private LocalDateTime start;
    private LocalDateTime ende;

    public Booking() {
    }

    public Booking(int bookingId, User user, Courts court, LocalDateTime start, LocalDateTime ende) {
        this.bookingId = bookingId;
        this.user = user;
        this.court = court;
        this.start = start;
        this.ende = ende;
    }

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
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnde() {
        return ende;
    }

    public void setEnde(LocalDateTime ende) {
        this.ende = ende;
    }

    @Override
    public String toString() {
        return "Booking [bookingId=" + bookingId + ", user=" + user
                + ", court=" + court + ", start=" + start + ", ende=" + ende + "]";
    }
}
