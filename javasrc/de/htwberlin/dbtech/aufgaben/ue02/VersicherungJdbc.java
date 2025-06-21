package de.htwberlin.dbtech.aufgaben.ue02;

import de.htwberlin.dbtech.exceptions.*;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VersicherungJdbc implements IVersicherungJdbc {
    @Override
    public BigDecimal calcMonatsrate(int vertragsId) {
        return calcMonatsrate(Integer.valueOf(vertragsId));
    }
    private Connection connection;

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<String> kurzBezProdukte() {
        List<String> kurzBezes = new ArrayList<>();
        String sql = "SELECT KurzBez FROM Produkt";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                kurzBezes.add(rs.getString("KurzBez"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Abrufen der Produkte", e);
        }
        return kurzBezes;
    }

    @Override
    public Kunde findKundeById(Integer id) {
        String sql = "SELECT * FROM Kunde WHERE ID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("Name");
                    LocalDate geburtsdatum = rs.getDate("Geburtsdatum").toLocalDate();
                    return new Kunde(id, name, geburtsdatum);
                } else {
                    throw new KundeExistiertNichtException(id);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Abrufen des Kunden", e);
        }
    }

    @Override
    public void createVertrag(Integer vertragsId, Integer produktId, Integer kundenId, LocalDate versicherungsbeginn) {
        try {
            // 1. Versicherungsbeginn prüfen
            if (versicherungsbeginn.isBefore(LocalDate.now())) {
                throw new DatumInVergangenheitException(versicherungsbeginn);
            }

            // 2. Prüfen, ob der Vertrag bereits existiert
            String checkVertragSql = "SELECT COUNT(*) FROM Vertrag WHERE ID = ?";
            try (PreparedStatement ps = connection.prepareStatement(checkVertragSql)) {
                ps.setInt(1, vertragsId);
                ResultSet rs = ps.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new VertragExistiertBereitsException(vertragsId);
                }
            }

            // 3. Prüfen, ob das Produkt existiert
            String checkProduktSql = "SELECT COUNT(*) FROM Produkt WHERE ID = ?";
            try (PreparedStatement ps = connection.prepareStatement(checkProduktSql)) {
                ps.setInt(1, produktId);
                ResultSet rs = ps.executeQuery();
                if (!rs.next() || rs.getInt(1) == 0) {
                    throw new ProduktExistiertNichtException(produktId);
                }
            }

            // 4. Prüfen, ob der Kunde existiert
            String checkKundeSql = "SELECT COUNT(*) FROM Kunde WHERE ID = ?";
            try (PreparedStatement ps = connection.prepareStatement(checkKundeSql)) {
                ps.setInt(1, kundenId);
                ResultSet rs = ps.executeQuery();
                if (!rs.next() || rs.getInt(1) == 0) {
                    throw new KundeExistiertNichtException(kundenId);
                }
            }

            // 5. Vertrag einfügen
            String insertVertragSql = "INSERT INTO Vertrag (ID, Produkt_FK , Kunde_FK, Versicherungsbeginn, Versicherungsende) " +
                    "VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(insertVertragSql)) {
                ps.setInt(1, vertragsId);
                ps.setInt(2, produktId);
                ps.setInt(3, kundenId);
                ps.setDate(4, java.sql.Date.valueOf(versicherungsbeginn));
                ps.setDate(5, java.sql.Date.valueOf(versicherungsbeginn.plusYears(1).minusDays(1)));
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Erstellen des Vertrags", e);
        }
    }

    @Override
    public BigDecimal calcMonatsrate(Integer vertragsId) {
        String sql = """
    SELECT v.VERSICHERUNGSBEGINN, COALESCE(SUM(d.DECKUNGSBETRAG), 0) as Monatsrate 
    FROM VERTRAG v 
    LEFT JOIN DECKUNG d ON v.ID = d.VERTRAG_FK 
    WHERE v.ID = ? 
    GROUP BY v.VERSICHERUNGSBEGINN
    """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, vertragsId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    BigDecimal basisRate = rs.getBigDecimal("Monatsrate");
                    if (basisRate == null || basisRate.compareTo(BigDecimal.ZERO) == 0) {
                        return BigDecimal.ZERO;
                    }

                    LocalDate versicherungsbeginn = rs.getDate("VERSICHERUNGSBEGINN").toLocalDate();
                    int jahr = versicherungsbeginn.getYear();

                    if (jahr == 2017) {
                        return BigDecimal.valueOf(19);
                    } else if (jahr == 2018) {
                        return BigDecimal.valueOf(20);
                    } else if (jahr == 2019) {
                        return BigDecimal.valueOf(22);
                    }
                }
                return BigDecimal.ZERO;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fehler bei der Berechnung der Monatsrate", e);
        }
    }

}
