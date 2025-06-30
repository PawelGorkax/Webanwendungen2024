package de.hsrm.mi.web.projekt.ui.tour;

import java.time.LocalDateTime;

import de.hsrm.mi.web.projekt.entities.tour.Tour;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class TourFormular {

    @NotNull
    @Positive
    private long anbieterId;

    @NotNull
    private LocalDateTime abfahrDateTime;

    @Min(0)
    private int preis;

    @Min(1)
    private int plaetze;

    @Size(max = 400)
    private String info;

    @NotNull
    @Positive
    private long startortId;

    @NotNull
    @Positive
    private long zielortId;


    public long getAnbieterId() {
        return anbieterId;
    }

    public void setAnbieterId(long anbieterId) {
        this.anbieterId = anbieterId;
    }

    public LocalDateTime getAbfahrDateTime() {
        return abfahrDateTime;
    }

    public void setAbfahrDateTime(LocalDateTime abfahrDateTime) {
        this.abfahrDateTime = abfahrDateTime;
    }

    public int getPreis() {
        return preis;
    }

    public void setPreis(int preis) {
        this.preis = preis;
    }

    public int getPlaetze() {
        return plaetze;
    }

    public void setPlaetze(int plaetze) {
        this.plaetze = plaetze;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public long getStartortId() {
        return startortId;
    }

    public void setStartortId(long startortId) {
        this.startortId = startortId;
    }

    public long getZielortId() {
        return zielortId;
    }

    public void setZielortId(long zielortId) {
        this.zielortId = zielortId;
    }

    public void fromTour(Tour tour) {
        this.anbieterId = tour.getAnbieter().getId();
        this.abfahrDateTime = tour.getAbfahrDateTime();
        this.preis = tour.getPreis();
        this.plaetze = tour.getPlaetze();
        this.info = tour.getInfo();
        this.startortId = tour.getStartOrt().getId();
        this.zielortId = tour.getZielOrt().getId();
    }

    public void toTour(Tour tour) {
        tour.setAbfahrDateTime(this.abfahrDateTime);
        tour.setPreis(this.preis);
        tour.setPlaetze(this.plaetze);
        tour.setInfo(this.info);
    }
}
