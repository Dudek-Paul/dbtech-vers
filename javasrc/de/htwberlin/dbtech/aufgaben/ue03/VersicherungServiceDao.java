package de.htwberlin.dbtech.aufgaben.ue03;

import de.htwberlin.dbtech.exceptions.DataException;
import de.htwberlin.dbtech.exceptions.DeckungsartExistiertNichtException;
import de.htwberlin.dbtech.exceptions.VertragExistiertNichtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Optional;


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
        Optional<Vertrag> vertrag = new VertragGateway(useConnection()).find(vertragsId);
        if (vertrag.isEmpty()) {
            throw new VertragExistiertNichtException(vertragsId);
        }

        // Wenn deckungsartId kein gültiger Primärschlüssel für Deckungsarten ist.
        Optional<Deckungsart> deckungsart = new DeckungsartGateway(useConnection()).find(deckungsartId);
        if (deckungsart.isEmpty()) {
            throw new DeckungsartExistiertNichtException(deckungsartId);
        }

        // ...

        // speichern nicht vergessen für den letzten Test

        L.info("ende createDeckung");
    }



}
