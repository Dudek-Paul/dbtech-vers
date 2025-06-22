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

public class AblehnungsregelGateway {
    private static final Logger L = LoggerFactory.getLogger(AblehnungsregelGateway.class);
    private final Connection connection;

    public AblehnungsregelGateway(Connection connection) {
        this.connection = connection;
    }

    private Connection useConnection() {
        if (connection == null) {
            throw new DataException("Connection not set");
        }
        return connection;
    }

    public Optional<List<Ablehnungsregel>> find(Integer deckungsartFK) {
        L.info("[AblehnungsregelGateway.find] Deckungsart_FK: " + deckungsartFK);
        String sql = "SELECT * FROM ablehnungsregel WHERE Deckungsart_FK = ?";
        L.info("[AblehnungsregelGateway.find] sql: " + sql);
        List<Ablehnungsregel> ablehnungsregel = new ArrayList<>();
        try (PreparedStatement ps = useConnection().prepareStatement(sql)) {
            ps.setInt(1, deckungsartFK);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ablehnungsregel.add(
                            new Ablehnungsregel(
                                    rs.getInt("Deckungsart_FK"),
                                    rs.getInt("LfdNr"),
                                    rs.getString("R_Betrag"),
                                    rs.getString("R_Alter")
                            )
                    );
                }
                if (ablehnungsregel.isEmpty()) {
                    return Optional.empty();
                }
                return Optional.of(ablehnungsregel);
            }
        } catch (SQLException e) {
            L.error("", e);
            throw new DataException(e);
        }
    }
}
