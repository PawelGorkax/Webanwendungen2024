package de.hsrm.mi.web.projekt.entities.tour;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import de.hsrm.mi.web.projekt.entities.benutzer.Benutzer;
import de.hsrm.mi.web.projekt.entities.ort.Ort;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Version
    private long version;

    @NotNull
    private LocalDateTime abfahrDateTime;

    @Min(value = 0, message = "Der Preis darf nicht negativ sein")
    private int preis;

    @Min(value = 1, message = "Es muss mindestens ein Platz vorhanden sein")
    private int plaetze;

    @Size(max = 400, message = "Die Info darf maximal 400 Zeichen lang sein")
    private String info;

    @ManyToOne
    @NotNull
    private Ort startOrt;

    @ManyToOne
    @NotNull
    private Ort zielOrt;

    @ManyToOne
    @NotNull
    private Benutzer anbieter;

    
    @ManyToMany(mappedBy = "gebuchteTouren")
    private Set<Benutzer> mitfahrgaeste = new HashSet<>();

    public Set<Benutzer> getMitfahrgaeste() {
        return mitfahrgaeste;
    }

    public void setMitfahrgaeste(Set<Benutzer> mitfahrgaeste) {
        this.mitfahrgaeste = mitfahrgaeste;
    }
    
    public long getId() {
        return id;
    }

    public long getVersion() {
        return version;
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

    public Ort getStartOrt() {
        return startOrt;
    }

    public void setStartOrt(Ort startOrt) {
        this.startOrt = startOrt;
    }
    
    public Ort getZielOrt() {
        return zielOrt;
    }

    public void setZielOrt(Ort zielOrt) {
        this.zielOrt = zielOrt;
    }

    public Benutzer getAnbieter() {
        return anbieter;
    }

    public void setAnbieter(Benutzer anbieter) {
        this.anbieter = anbieter;
    }
}
