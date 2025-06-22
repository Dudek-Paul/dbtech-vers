package de.htwberlin.dbtech.aufgaben.ue03;

import de.htwberlin.dbtech.exceptions.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DeckungsartGateway {
    private static final Logger L = LoggerFactory.getLogger(DeckungsartGateway.class);
    private final Connection connection;

    public DeckungsartGateway(Connection connection) {
        this.connection = connection;
    }

    private Connection useConnection() {
        if (connection == null) {
            throw new DataException("Connection not set");
        }
        return connection;
    }

    public Optional<Deckungsart> find(Integer id) {
        L.info("[DeckungsartGateway.find] id: " + id);
        String sql = "SELECT * FROM deckungsart WHERE ID = ?";
        L.info("[DeckungsartGateway.find] sql: " + sql);
        try (PreparedStatement ps = useConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(
                            new Deckungsart(
                                    rs.getInt("ID"),
                                    rs.getInt("Produkt_FK"),
                                    rs.getString("KurzBez"),
                                    rs.getString("Bez")
                            )
                    );
                }
            }
        } catch (SQLException e) {
            L.error("", e);
            throw new DataException(e);
        }

        return Optional.empty();
    }
}
