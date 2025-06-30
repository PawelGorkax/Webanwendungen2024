package de.hsrm.mi.web.projekt.ui.ort;

import de.hsrm.mi.web.projekt.entities.ort.Ort;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class OrtFormular {

    @NotNull
    @Size(min = 1, message = "Der Name darf nicht leer sein")
    private String name;

    private double geobreite;
    private double geolaenge;

    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGeobreite() {
        return geobreite;
    }

    public void setGeobreite(double geobreite) {
        this.geobreite = geobreite;
    }

    public double getGeolaenge() {
        return geolaenge;
    }

    public void setGeolaenge(double geolaenge) {
        this.geolaenge = geolaenge;
    }

    public void fromOrt(Ort ort) {
        this.name = ort.getName();
        this.geobreite = ort.getGeobreite();
        this.geolaenge = ort.getGeolaenge();
    }

    public void toOrt(Ort ort) {
        ort.setName(this.name);
        ort.setGeobreite(this.geobreite);
        ort.setGeolaenge(this.geolaenge);
    }
}
