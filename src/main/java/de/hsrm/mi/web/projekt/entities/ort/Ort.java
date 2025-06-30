package de.hsrm.mi.web.projekt.entities.ort;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Ort {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Version
    private long version;

    @NotNull
    @Size(min = 1, message = "Der Name darf nicht leer sein")
    private String name;

    private double geobreite;
    private double geolaenge;

    public Ort(){
    }

    public Ort(String name, double geobreite, double geolaenge) {
        this.name = name;
        this.geobreite = geobreite;
        this.geolaenge = geolaenge;
    }
    
    public long getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

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
}
