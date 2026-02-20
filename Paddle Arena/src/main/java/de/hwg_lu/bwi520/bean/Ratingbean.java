package de.hwg_lu.bwi520.bean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.hwg_lu.bwi520.jdbc.Connectionmng;
import de.hwg_lu.bwi520.jdbc.Ratingjdbc;
import de.hwg_lu.bwi520.modell.Rating;
import de.hwg_lu.bwi520.modell.User;

public class Ratingbean {

    private List<Rating> ratings;
    private double averageRating;
    private String errorMessage;

    public Ratingbean() {
        this.ratings = new ArrayList<>();
        this.averageRating = 0.0;
        this.errorMessage = null;
    }

    public boolean createRating(User user, int ratingValue, String comment) {
        try {
            Connection conn = Connectionmng.getSharedConnection();
            Ratingjdbc ratingJdbc = new Ratingjdbc(conn);

            if (ratingValue < 1 || ratingValue > 5) {
                this.errorMessage = "Bewertung muss zwischen 1 und 5 liegen.";
                return false;
            }

            Rating rating = new Rating(0, ratingValue, comment, user);
            ratingJdbc.createRating(rating);
            this.errorMessage = null;
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            this.errorMessage = "Datenbankfehler: " + e.getMessage();
            e.printStackTrace();
            return false;
        }
    }

    public List<Rating> loadAllRatings() {
        try {
            Connection conn = Connectionmng.getSharedConnection();
            Ratingjdbc ratingJdbc = new Ratingjdbc(conn);
            this.ratings = ratingJdbc.getAllRatings();
            this.averageRating = ratingJdbc.getAverageRating();
        } catch (ClassNotFoundException | SQLException e) {
            this.errorMessage = "Datenbankfehler: " + e.getMessage();
            e.printStackTrace();
        }
        return this.ratings;
    }

    public boolean deleteRating(int ratingId) {
        try {
            Connection conn = Connectionmng.getSharedConnection();
            Ratingjdbc ratingJdbc = new Ratingjdbc(conn);
            ratingJdbc.deleteRating(ratingId);
            this.errorMessage = null;
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            this.errorMessage = "Datenbankfehler: " + e.getMessage();
            e.printStackTrace();
            return false;
        }
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
