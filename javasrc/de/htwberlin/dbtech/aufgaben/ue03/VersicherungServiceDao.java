package de.htwberlin.dbtech.aufgaben.ue03;

import de.htwberlin.dbtech.exceptions.DataException;
import de.htwberlin.dbtech.exceptions.VertragExistiertNichtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Connection;


/**
 * VersicherungJdbc
 */
public class VersicherungServiceDao implements IVersicherungService {
    private static final Logger L = LoggerFactory.getLogger(VersicherungServiceDao.class);
    private Connection connection;

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @SuppressWarnings("unused")
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

        L.info("ende createDeckung");
    }

    @Override
    public boolean existiertVertragIDInDB(Integer id) {
        L.info("vid: " + id);
        return false;
    }


}
}
