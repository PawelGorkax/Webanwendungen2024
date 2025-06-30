package de.hsrm.mi.web.projekt.ui.benutzer;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import de.hsrm.mi.web.projekt.entities.benutzer.Benutzer;
import de.hsrm.mi.web.validators.GutesPasswort;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class BenutzerFormular {

    @NotNull
    @Size(min=3, max=80, message="Bitte gebe einen Namen von min. 3 und max. 80 Feldern ein!")
    private String name;

    @NotNull
    @Email(message="Bitte gebe eine gültige E-Mail ein!")
    private String email;

    @NotNull
    @GutesPasswort
    private String passwort;
    
    @NotNull
    @Past(message="Datum muss in der Vergangenheit liegen!")    
    private LocalDate geburtsdatum;

    private Set<String> wunschListe = new HashSet<>();
    private Set<String> hassListe = new HashSet<>();

    public Set<String> getWunschListe() {
        return this.wunschListe;
    }

    public void setWunschListe(Set<String> wunschListe) {
        this.wunschListe = wunschListe;
    }

    public Set<String> getHassListe() {
        return this.hassListe;
    }

    public void setHassListe(Set<String> hassListe) {
        this.hassListe = hassListe;
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

    public void fromBenutzer(Benutzer benutzer) {
        this.name = benutzer.getName();
        this.email = benutzer.getEmail();
        this.geburtsdatum = benutzer.getGeburtsdatum();
        this.wunschListe = benutzer.getWunschListe();
        this.hassListe = benutzer.getHassListe();
    }

    public void toBenutzer(Benutzer benutzer) {
        benutzer.setName(this.name);
        benutzer.setEmail(this.email);
        benutzer.setGeburtsdatum(this.geburtsdatum);
        benutzer.setWunschListe(this.wunschListe);
        benutzer.setHassListe(this.hassListe);
    }
}
