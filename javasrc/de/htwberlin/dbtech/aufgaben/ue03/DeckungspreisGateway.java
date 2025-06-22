package de.htwberlin.dbtech.aufgaben.ue03;

import de.htwberlin.dbtech.exceptions.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeckungspreisGateway {
    private static final Logger L = LoggerFactory.getLogger(DeckungspreisGateway.class);
    private final Connection connection;

    public DeckungspreisGateway(Connection connection) {
        this.connection = connection;
    }

    private Connection useConnection() {
        if (connection == null) {
            throw new DataException("Connection not set");
        }
        return connection;
    }

    public Optional<List<Deckungspreis>> find(Integer deckungsbetragFK) {
        L.info("[DeckungspreisGateway.find] Deckungsbetrag_FK: " + deckungsbetragFK);
        String sql = "SELECT * FROM deckungspreis WHERE Deckungsbetrag_FK = ?";
        L.info("[DeckungspreisGateway.find] sql: " + sql);
        List<Deckungspreis> deckungspreise = new ArrayList<>();
        try (PreparedStatement ps = useConnection().prepareStatement(sql)) {
            ps.setInt(1, deckungsbetragFK);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    deckungspreise.add(
                            new Deckungspreis(
                                    rs.getInt("ID"),
                                    rs.getInt("Deckungsbetrag_FK"),
                                    rs.getDate("Gueltig_Von"),
                                    rs.getDate("Gueltig_Bis"),
                                    rs.getBigDecimal("Preis")
                            )
                    );
                }
                if (deckungspreise.isEmpty()) {
                    return Optional.empty();
                }
                return Optional.of(deckungspreise);
            }
        } catch (SQLException e) {
            L.error("", e);
            throw new DataException(e);
        }
    }
}
