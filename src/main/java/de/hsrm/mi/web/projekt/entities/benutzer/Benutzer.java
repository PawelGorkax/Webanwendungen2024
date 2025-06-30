package de.hsrm.mi.web.projekt.entities.benutzer;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Version;

import de.hsrm.mi.web.projekt.entities.tour.Tour;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "benutzer")
public class Benutzer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Version
    private long version;

    @Size(min=3, max=80, message="Bitte gebe einen Namen von min. 3 und max. 80 Feldern ein!")
    @NotNull
    private String name;

    @Email(message="Bitte gebe eine gültige E-Mail ein!")
    @NotNull
    @Column(unique = true)
    private String email;

    @NotBlank
    private String passwort;

    @Past(message="Datum muss in der Vergangenheit liegen!")
    @NotNull
    private LocalDate geburtsdatum;

    @ElementCollection
    @NotNull
    private Set<String> wunschListe = new HashSet<>();

    @ElementCollection
    @NotNull
    private Set<String> hassListe = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> rollen = new HashSet<>();


   @OneToMany(mappedBy = "anbieter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <Tour> tourList;

    @ManyToMany
    private Set<Tour> gebuchteTouren = new HashSet<>();
    public Set<Tour> getGebuchteTouren() {
        return gebuchteTouren;
    }

    public void setGebuchteTouren(Set<Tour> gebuchteTouren) {
        this.gebuchteTouren = gebuchteTouren;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public LocalDate getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(LocalDate geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public Set<String> getWunschListe() {
        return wunschListe;
    }

    public void setWunschListe(Set<String> wunschListe) {
        this.wunschListe = wunschListe;
    }

    public Set<String> getHassListe() {
        return hassListe;
    }

    public void setHassListe(Set<String> hassListe) {
        this.hassListe = hassListe;
    }

    public List<Tour> getTourListe(){
         return tourList;
    }

    public void setTourList(List<Tour> tourList) {
         this.tourList = tourList;
    }

    public Set<String> getRollen() {
        return rollen;
    }

    public void setRollen(Set<String> rollen) {
        this.rollen = rollen;
    }

    
}
