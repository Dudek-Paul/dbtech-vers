package de.htwberlin.dbtech.aufgaben.ue03;

import de.htwberlin.dbtech.exceptions.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeckungGateway {
    private static final Logger L = LoggerFactory.getLogger(DeckungGateway.class);
    private final Connection connection;

    public DeckungGateway(Connection connection) {
        this.connection = connection;
    }

    private Connection useConnection() {
        if (connection == null) {
            throw new DataException("Connection not set");
        }
        return connection;
    }

    public void safe(Deckung deckung) {
        L.info("[DeckungGateway.safe] deckung: " + deckung);
        String sql = "insert into Deckung (Vertrag_FK, Deckungsart_FK, Deckungsbetrag) values (?,?,?)";
        L.info("[DeckungGateway.safe] sql: " + sql);
        try (PreparedStatement ps = useConnection().prepareStatement(sql)) {
            ps.setInt(1, deckung.getVertragFK());
            ps.setInt(2, deckung.getDeckungsartFK());
            ps.setBigDecimal(3, deckung.getDeckungsbetrag());
            ps.executeUpdate();
        } catch (SQLException e) {
            L.error("", e);
            throw new DataException(e);
        }
    }
}
