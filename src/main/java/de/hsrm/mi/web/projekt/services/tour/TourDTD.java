package de.hsrm.mi.web.projekt.services.tour;

import java.time.LocalDateTime;

import de.hsrm.mi.web.projekt.entities.tour.Tour;
import de.hsrm.mi.web.projekt.services.geo.GeoDistanz;

public record TourDTD(
    long id,
    LocalDateTime abfahrDateTime,
    int preis,
    int plaetze,
    int buchungen,
    String startOrtName,
    long startOrtId,
    String zielOrtName,
    long zielOrtId,
    String anbieterName,
    long anbieterId,
    double distanz,
    String info
) {
    public static TourDTD fromTour(Tour t) {
        int buchungen = t.getMitfahrgaeste().size();
        double distanz = GeoDistanz.calculateDistance(
            t.getStartOrt().getGeobreite(),
            t.getStartOrt().getGeolaenge(),
            t.getZielOrt().getGeobreite(),
            t.getZielOrt().getGeolaenge()
        );
        return new TourDTD(
            t.getId(),
            t.getAbfahrDateTime(),
            t.getPreis(),
            t.getPlaetze(),
            buchungen,
            t.getStartOrt().getName(),
            t.getStartOrt().getId(),
            t.getZielOrt().getName(),
            t.getZielOrt().getId(),
            t.getAnbieter().getName(),
            t.getAnbieter().getId(),
            distanz,
            t.getInfo()
        );
    }
}
