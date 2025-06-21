package de.htwberlin.dbtech.aufgaben.ue03;

/*
  @author Ingo Classen
 */

import de.htwberlin.dbtech.exceptions.DataException;
import de.htwberlin.dbtech.exceptions.DeckungsartExistiertNichtException;
import de.htwberlin.dbtech.exceptions.VertragExistiertNichtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * VersicherungJdbc
 */
public class VersicherungService implements IVersicherungService {
    private static final Logger L = LoggerFactory.getLogger(VersicherungService.class);
    private Connection connection;

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    private Connection useConnection() {
        if (connection == null) {
            throw new DataException("Connection not set");
        }
        return connection;
    }

    @Override
    public void createDeckung(Integer vertragsId, Integer deckungsartId, BigDecimal deckungsbetrag) {
        L.info("vertragsId: " + vertragsId + " deckungsartId: " + deckungsartId + " deckungsbetrag: " + deckungsbetrag);

        // Wenn vertragsId kein gültiger Primärschlüssel für Verträge ist.
        if (!existiertVertragIDInDB(vertragsId)) {
            throw new VertragExistiertNichtException(vertragsId);
        }

        // Wenn deckungsartId kein gültiger Primärschlüssel für Deckungsarten ist.
        if (!existiertDeckungsartInDB(deckungsartId)) {
            throw new DeckungsartExistiertNichtException(deckungsartId);
        }



        L.info("ende createDeckung");
    }

    public boolean existiertVertragIDInDB(Integer id) {
        L.info("vid: " + id);
        String sql = "select ID from Vertrag where ID=?";
        L.info(sql);
        try (PreparedStatement ps = useConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            L.error("", e);
            throw new DataException(e);
        }
    }

    private boolean existiertDeckungsartInDB(Integer id) {
        L.info("deckungsartId: " + id);
        String sql = "select ID from Deckungsart where ID=?";
        L.info(sql);
        try (PreparedStatement ps = useConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            L.error("", e);
            throw new DataException(e);
        }
    }


}