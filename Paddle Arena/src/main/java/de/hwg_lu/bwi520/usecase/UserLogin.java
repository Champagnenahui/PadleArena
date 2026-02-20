package de.hwg_lu.bwi520.usecase;

import java.sql.Connection;
import java.sql.SQLException;

import de.hwg_lu.bwi520.jdbc.Connectionmng;
import de.hwg_lu.bwi520.jdbc.Userjdbc;
import de.hwg_lu.bwi520.modell.User;

/**
 * UseCase-Klasse fuer die Benutzer-Anmeldung.
 * Kapselt die Geschaeftslogik des Login-Vorgangs.
 */
public class UserLogin {

    /**
     * Versucht einen Benutzer mit den gegebenen Credentials anzumelden.
     *
     * @param username Benutzername
     * @param password Passwort
     * @return Das User-Objekt bei erfolgreicher Anmeldung, sonst null
     */
    public static User anmelden(String username, String password) {
        if (username == null || username.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {
            return null;
        }

        try {
            Connection conn = Connectionmng.getSharedConnection();
            Userjdbc userJdbc = new Userjdbc(conn);
            return userJdbc.findUser(username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Versucht einen neuen Benutzer zu registrieren.
     *
     * @param username Benutzername
     * @param password Passwort
     * @return true bei erfolgreicher Registrierung, false wenn Username bereits existiert
     */
    public static boolean registrieren(String username, String password) {
        if (username == null || username.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {
            return false;
        }

        try {
            Connection conn = Connectionmng.getSharedConnection();
            Userjdbc userJdbc = new Userjdbc(conn);

            // Pruefen ob Benutzer bereits existiert
            User existing = userJdbc.findByUsername(username);
            if (existing != null) {
                return false;
            }

            userJdbc.createUser(new User(username, password));
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
