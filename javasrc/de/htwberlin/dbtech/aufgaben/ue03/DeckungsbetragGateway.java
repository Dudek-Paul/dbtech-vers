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

public class DeckungsbetragGateway {
    private static final Logger L = LoggerFactory.getLogger(DeckungsbetragGateway.class);
    private final Connection connection;

    public DeckungsbetragGateway(Connection connection) {
        this.connection = connection;
    }

    private Connection useConnection() {
        if (connection == null) {
            throw new DataException("Connection not set");
        }
        return connection;
    }

    public Optional<List<Deckungsbetrag>> find(Integer deckungsartFK) {
        L.info("[DeckungsbetragGateway.find] Deckungsart_FK: " + deckungsartFK);
        String sql = "SELECT * FROM deckungsbetrag WHERE Deckungsart_FK = ?";
        L.info(sql);
        List<Deckungsbetrag> deckungsbetraege = new ArrayList<>();
        try (PreparedStatement ps = useConnection().prepareStatement(sql)) {
            ps.setInt(1, deckungsartFK);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    deckungsbetraege.add(
                            new Deckungsbetrag(
                                    rs.getInt("ID"),
                                    rs.getInt("Deckungsart_FK"),
                                    rs.getBigDecimal("Deckungsbetrag")
                            )
                    );
                }
                if (deckungsbetraege.isEmpty()) {
                    return Optional.empty();
                }
                return Optional.of(deckungsbetraege);
            }
        } catch (SQLException e) {
            L.error("", e);
            throw new DataException(e);
        }
    }
}
