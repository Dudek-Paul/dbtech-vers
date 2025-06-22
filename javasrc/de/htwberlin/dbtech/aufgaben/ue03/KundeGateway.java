package de.htwberlin.dbtech.aufgaben.ue03;

import de.htwberlin.dbtech.exceptions.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class KundeGateway {
    private static final Logger L = LoggerFactory.getLogger(KundeGateway.class);
    private final Connection connection;

    public KundeGateway(Connection connection) {
        this.connection = connection;
    }

    private Connection useConnection() {
        if (connection == null) {
            throw new DataException("Connection not set");
        }
        return connection;
    }

    public Optional<Kunde> find(Integer id) {
        L.info("[KundeGateway.find] id: " + id);
        String sql = "SELECT * FROM kunde WHERE ID = ?";
        L.info("[KundeGateway.find] sql: " + sql);
        try (PreparedStatement ps = useConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(
                            new Kunde(
                                    rs.getInt("ID"),
                                    rs.getString("Name"),
                                    rs.getDate("Geburtsdatum")
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
