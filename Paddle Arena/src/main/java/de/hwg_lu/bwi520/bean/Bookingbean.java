package de.hwg_lu.bwi520.bean;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import de.hwg_lu.bwi520.jdbc.Bookingjdbc;
import de.hwg_lu.bwi520.jdbc.Connectionmng;
import de.hwg_lu.bwi520.jdbc.Courtsjdbc;
import de.hwg_lu.bwi520.modell.Booking;
import de.hwg_lu.bwi520.modell.Courts;
import de.hwg_lu.bwi520.modell.User;

public class Bookingbean {

    private static final int MAX_BUCHUNGSDAUER_STUNDEN = 2;

    private List<Booking> bookings;
    private List<Courts> courts;
    private String errorMessage;

    public Bookingbean() {
        this.bookings = new ArrayList<>();
        this.courts = new ArrayList<>();
        this.errorMessage = null;
    }

    public boolean createBooking(User user, int courtId, LocalDateTime start, LocalDateTime ende) {
        try {
            Connection conn = Connectionmng.getSharedConnection();
            Bookingjdbc bookingJdbc = new Bookingjdbc(conn);
            Courtsjdbc courtsJdbc = new Courtsjdbc(conn);

            
            Courts court = courtsJdbc.getCourtById(courtId);
            if (court == null) {
                this.errorMessage = "Platz nicht gefunden.";
                return false;
            }

            
            if (ende.isBefore(start) || ende.isEqual(start)) {
                this.errorMessage = "Endzeit muss nach Startzeit liegen.";
                return false;
            }

            
            Duration dauer = Duration.between(start, ende);
            if (dauer.toMinutes() > MAX_BUCHUNGSDAUER_STUNDEN * 60) {
                this.errorMessage = "Maximale Buchungsdauer betraegt " + MAX_BUCHUNGSDAUER_STUNDEN
                        + " Stunden. Deine Buchung ist " + dauer.toHours() + "h " + (dauer.toMinutes() % 60) + "min lang.";
                return false;
            }

            
            List<Booking> alleBookings = bookingJdbc.getAllBookings();
            for (Booking bestehend : alleBookings) {
                if (bestehend.getCourt().getCourtId() == courtId) {
                    if (start.isBefore(bestehend.getEnde()) && ende.isAfter(bestehend.getStart())) {
                        this.errorMessage = "Der Platz \"" + court.getName()
                                + "\" ist in diesem Zeitraum bereits gebucht ("
                                + bestehend.getStart().toLocalTime() + " - "
                                + bestehend.getEnde().toLocalTime() + ").";
                        return false;
                    }
                }
            }

            Booking booking = new Booking(0, user, court, start, ende);
            bookingJdbc.createBooking(booking);
            this.errorMessage = null;
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            this.errorMessage = "Datenbankfehler: " + e.getMessage();
            e.printStackTrace();
            return false;
        }
    }

    public List<Booking> loadAllBookings() {
        try {
            Connection conn = Connectionmng.getSharedConnection();
            Bookingjdbc bookingJdbc = new Bookingjdbc(conn);
            this.bookings = bookingJdbc.getAllBookings();
        } catch (ClassNotFoundException | SQLException e) {
            this.errorMessage = "Datenbankfehler: " + e.getMessage();
            e.printStackTrace();
        }
        return this.bookings;
    }

    public List<Booking> loadBookingsByUser(String username) {
        try {
            Connection conn = Connectionmng.getSharedConnection();
            Bookingjdbc bookingJdbc = new Bookingjdbc(conn);
            this.bookings = bookingJdbc.getBookingsByUser(username);
        } catch (ClassNotFoundException | SQLException e) {
            this.errorMessage = "Datenbankfehler: " + e.getMessage();
            e.printStackTrace();
        }
        return this.bookings;
    }

    public boolean deleteBooking(int bookingId) {
        try {
            Connection conn = Connectionmng.getSharedConnection();
            Bookingjdbc bookingJdbc = new Bookingjdbc(conn);
            bookingJdbc.deleteBooking(bookingId);
            this.errorMessage = null;
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            this.errorMessage = "Datenbankfehler: " + e.getMessage();
            e.printStackTrace();
            return false;
        }
    }

    public List<Courts> loadAllCourts() {
        try {
            Connection conn = Connectionmng.getSharedConnection();
            Courtsjdbc courtsJdbc = new Courtsjdbc(conn);
            this.courts = courtsJdbc.getAllCourts();
        } catch (ClassNotFoundException | SQLException e) {
            this.errorMessage = "Datenbankfehler: " + e.getMessage();
            e.printStackTrace();
        }
        return this.courts;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public List<Courts> getCourts() {
        return courts;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

