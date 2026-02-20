package de.hwg_lu.bwi520.bean;

import java.sql.Connection;
import java.sql.SQLException;

import de.hwg_lu.bwi520.jdbc.Connectionmng;
import de.hwg_lu.bwi520.jdbc.Userjdbc;
import de.hwg_lu.bwi520.modell.User;

public class Userbean {

    private User user;
    private String errorMessage;

    public Userbean() {
        this.user = null;
        this.errorMessage = null;
    }

    public boolean login(String username, String password) {
        try {
            Connection conn = Connectionmng.getSharedConnection();
            Userjdbc userJdbc = new Userjdbc(conn);
            User found = userJdbc.findUser(username, password);
            if (found != null) {
                this.user = found;
                this.errorMessage = null;
                return true;
            } else {
                this.errorMessage = "Benutzername oder Passwort falsch.";
                return false;
            }
        } catch (ClassNotFoundException | SQLException e) {
            this.errorMessage = "Datenbankfehler: " + e.getMessage();
            e.printStackTrace();
            return false;
        }
    }

    public boolean register(String username, String password) {
        try {
            Connection conn = Connectionmng.getSharedConnection();
            Userjdbc userJdbc = new Userjdbc(conn);
            // Pruefen ob Benutzer bereits existiert
            User existing = userJdbc.findByUsername(username);
            if (existing != null) {
                this.errorMessage = "Benutzername bereits vergeben.";
                return false;
            }
            User newUser = new User(username, password);
            userJdbc.createUser(newUser);
            this.user = newUser;
            this.errorMessage = null;
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            this.errorMessage = "Datenbankfehler: " + e.getMessage();
            e.printStackTrace();
            return false;
        }
    }

    public void logout() {
        this.user = null;
    }

    public boolean isLoggedIn() {
        return this.user != null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
