package de.htwberlin.dbtech.aufgaben.ue03;

import de.htwberlin.dbtech.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.time.temporal.ChronoUnit;
import java.util.List;
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
        L.info("[VersicherungServiceDao.createDeckung] vertragsId: " + vertragsId + " deckungsartId: " + deckungsartId + " deckungsbetrag: " + deckungsbetrag);

        // Wenn vertragsId kein gültiger Primärschlüssel für Verträge ist.
        Optional<Vertrag> vertragEintrag = new VertragGateway(useConnection()).find(vertragsId);
        if (vertragEintrag.isEmpty()) {
            throw new VertragExistiertNichtException(vertragsId);
        }

        // Wenn deckungsartId kein gültiger Primärschlüssel für Deckungsarten ist.
        Optional<Deckungsart> deckungsartEintrag = new DeckungsartGateway(useConnection()).find(deckungsartId);
        if (deckungsartEintrag.isEmpty()) {
            throw new DeckungsartExistiertNichtException(deckungsartId);
        }

        // Wenn die Deckungsart nicht für das Produkt des Vertrags passt.
        if (!vertragEintrag.get().getProduktFK().equals(deckungsartEintrag.get().getProduktFK())) {
            throw new DeckungsartPasstNichtZuProduktException(vertragsId, deckungsartId);
        }

        // Wenn der Deckungsbetrag für die gegebene Deckung nicht angeboten wird.
        Optional<List<Deckungsbetrag>> deckungsbetragEinträge = new DeckungsbetragGateway(useConnection()).find(deckungsartId);
        if (deckungsbetragEinträge.isEmpty()) {
            throw new UngueltigerDeckungsbetragException(deckungsbetrag);
        }

        // Wenn der Deckungsbetrag für die gegebene Deckung nicht angeboten wird.
        Optional<Deckungsbetrag> gefundenderDeckungsbetrag = Optional.empty();
        for (Deckungsbetrag deckungsbetragEintrag : deckungsbetragEinträge.get()) {
            if (deckungsbetrag.equals(deckungsbetragEintrag.getDeckungsbetrag())) {
                gefundenderDeckungsbetrag = Optional.of(deckungsbetragEintrag);
                break;
            }
        }
        if (gefundenderDeckungsbetrag.isEmpty()) {
            throw new UngueltigerDeckungsbetragException(deckungsbetrag);
        }

        // Wenn für ein Deckungsbetrag kein Deckungspreis vorliegt.
        Optional<List<Deckungspreis>> deckungspreisEinträge = new DeckungspreisGateway(useConnection()).find(gefundenderDeckungsbetrag.get().getId());
        if (deckungspreisEinträge.isEmpty()) {
            throw new DeckungspreisNichtVorhandenException(deckungsbetrag);
        }

        // Wenn das Vertragsdatum nicht zum Deckungspreisdatum passt.
        Date versicherungsbeginn = vertragEintrag.get().getVersicherungsbeginn();
        Date versicherungsende = vertragEintrag.get().getVersicherungsende();
        boolean versicherungsbeginnOK = false;
        boolean versicherungsendeOK = false;
        for (Deckungspreis deckungspreis : deckungspreisEinträge.get()) {
            Date gueltigVon = deckungspreis.getGueltigVon();
            Date gueltigBis = deckungspreis.getGueltigBis();
            if (versicherungsbeginn.compareTo(gueltigVon) >= 0 && versicherungsbeginn.compareTo(gueltigBis) <= 0) {
                versicherungsbeginnOK = true;
            }
            if (versicherungsende.compareTo(gueltigVon) >= 0 && versicherungsende.compareTo(gueltigBis) <= 0) {
                versicherungsendeOK = true;
            }
        }
        if (!versicherungsbeginnOK || !versicherungsendeOK) {
            throw new DeckungspreisNichtVorhandenException(deckungsbetrag);
        }

        // Ablehnungsregel: Alter
        Optional<List<Ablehnungsregel>> ablehnungsregelEinträge = new AblehnungsregelGateway(useConnection()).find(deckungsartId);
        if (ablehnungsregelEinträge.isPresent()) {
            Optional<Kunde> kundeEintrag = new KundeGateway(useConnection()).find(vertragEintrag.get().getKundeFK());
            Date geburtsdatum = kundeEintrag.get().getGeburtsdatum();
            for (Ablehnungsregel ablehnungsregelEintrag : ablehnungsregelEinträge.get()) {
                String ablehnungsregelAlterString = ablehnungsregelEintrag.getrAlter();
                String ablehnungsregelBetragString = ablehnungsregelEintrag.getrBetrag();
                long differenceInYears = ChronoUnit.YEARS.between(geburtsdatum.toLocalDate(), versicherungsbeginn.toLocalDate());
                if (ablehnungsregelAlterString.contains("<")) {
                    if (ablehnungsregelBetragString.equals("- -") && differenceInYears < Long.parseLong(ablehnungsregelAlterString.split(" ")[1])) {
                        throw new DeckungsartNichtRegelkonformException(deckungsartId);
                    }
                } else if (ablehnungsregelAlterString.contains(">")) {
                    if (ablehnungsregelBetragString.equals("- -")) {
                        if (differenceInYears > Long.parseLong(ablehnungsregelAlterString.split(" ")[1])) {
                            throw new DeckungsartNichtRegelkonformException(deckungsartId);
                        }
                    } else {
                        long ablehnungsregelAlter = Long.parseLong(ablehnungsregelAlterString.split(" ")[1]);
                        BigDecimal ablehnungsregelBetrag = new BigDecimal(ablehnungsregelBetragString.split(" ")[1]);
                        if (deckungsbetrag.compareTo(ablehnungsregelBetrag) >= 0) {
                            if (differenceInYears > ablehnungsregelAlter) {
                                throw new DeckungsartNichtRegelkonformException(deckungsartId);
                            }
                        }
                    }
                }
            }
        }

        Deckung deckung = new Deckung(vertragsId, deckungsartId, deckungsbetrag);
        new DeckungGateway(useConnection()).safe(deckung);

        L.info("[VersicherungService.createDeckung] ende");
    }



}
