package de.hsrm.mi.web.projekt.ui.benutzer;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.hsrm.mi.web.projekt.entities.benutzer.Benutzer;
import de.hsrm.mi.web.projekt.services.benutzer.BenutzerService;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/admin/benutzer")
@SessionAttributes(names = {"profil", "benutzer", "MAXWUNSCH"})
public class BenutzerController {

    private final static int MAXWUNSCH = 5;
    private final BenutzerService benutzerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public BenutzerController(BenutzerService benutzerService) {
        this.benutzerService = benutzerService;
    }

    @ModelAttribute("profil")
    public BenutzerFormular initProfil() {
        return new BenutzerFormular();
    }

    @ModelAttribute("benutzer")
    public Benutzer initBenutzer() {
        return new Benutzer();
    }

    @ModelAttribute("MAXWUNSCH")
    public int initMaxWunsch() {
        return MAXWUNSCH;
    }

    @GetMapping("/{benutzerId}")
    public String benutzerBearbeiten(@PathVariable("benutzerId") long benutzerId, Model m) {
        if (benutzerId == 0) {
            m.addAttribute("titel", "Neues Benutzerprofil");
            m.addAttribute("profil", new BenutzerFormular());
            m.addAttribute("benutzer", new Benutzer());
        } else {
            Optional<Benutzer> optionalBenutzer = benutzerService.holeBenutzerMitId(benutzerId);
            if (optionalBenutzer.isPresent()) {
                Benutzer benutzer = optionalBenutzer.get();
                BenutzerFormular profil = new BenutzerFormular();
                profil.fromBenutzer(benutzer);
                m.addAttribute("titel", "Benutzerprofil " + benutzerId + " bearbeiten");
                m.addAttribute("profil", profil);
                m.addAttribute("benutzer", benutzer);
            } else {
                return "redirect:/admin/benutzer";
            }
        }
        return "benutzerbearbeiten";
    }

    @PostMapping("/{benutzerId}")
    public String postMethode(@PathVariable("benutzerId") long benutzerId, 
                              @Valid @ModelAttribute("profil") BenutzerFormular profil, 
                              BindingResult formularError,
                              @SessionAttribute("benutzer") Benutzer benutzer,
                              Model m) {
        if (formularError.hasErrors()) {
            return "benutzerbearbeiten";
        }
        if (profil.getPasswort() != null && !profil.getPasswort().isEmpty()) {
            String encodedPasswort = passwordEncoder.encode(profil.getPasswort());
            benutzer.setPasswort(encodedPasswort);
        } else if (benutzer.getPasswort() == null || benutzer.getPasswort().isEmpty()) {
            formularError.rejectValue("passwort", "benutzer.passwort.ungesetzt", "Passwort wurde noch nicht gesetzt");
            return "benutzerbearbeiten";
        }

        profil.toBenutzer(benutzer);
        Benutzer gespeicherterBenutzer = benutzerService.speichereBenutzer(benutzer);
        m.addAttribute("benutzer", gespeicherterBenutzer);

        return "redirect:/admin/benutzer/" + gespeicherterBenutzer.getId();
    }

    @GetMapping()
    public String benutzerListe(Model m) {
        List<Benutzer> benutzerListe = benutzerService.holeAlleBenutzer().stream()
                .sorted(Comparator.comparing(Benutzer::getName)
                .thenComparing(Benutzer::getEmail))
                .collect(Collectors.toList());
        m.addAttribute("benutzerListe", benutzerListe);
        return "benutzerliste";
    }

    @GetMapping("/{benutzerId}/del")
    public String benutzerLoeschen(@PathVariable("benutzerId") long benutzerId) {
        benutzerService.loescheBenutzerMitId(benutzerId);
        return "redirect:/admin/benutzer";
    }

    @GetMapping("/{id}/hx/feld/{feldname}")
    public String holeBenutzerFeld(@PathVariable("id") long id, @PathVariable("feldname") String feldname, Model m) {
        Benutzer benutzer = benutzerService.holeBenutzerMitId(id).orElseThrow(() -> new IllegalArgumentException("Ungültige Benutzer-ID"));
        String wert;
        switch (feldname) {
            case "name" -> wert = benutzer.getName();
            case "email" -> wert = benutzer.getEmail();
            default -> throw new IllegalArgumentException("Ungültiger Feldname");
        }
        m.addAttribute("benutzerid", id);
        m.addAttribute("feldname", feldname);
        m.addAttribute("wert", wert);
        return "fragments/benutzerliste-zeile :: feldbearbeiten";
    }

    @PostMapping("/{id}/hx/feld/update/{feldname}")
    public String updateField(@RequestParam Long benutzerid, @RequestParam String feldname, @RequestParam String wert, Model m) {
        Optional<Benutzer> optionalBenutzer = benutzerService.holeBenutzerMitId(benutzerid);
        if (optionalBenutzer.isPresent()) {
            Benutzer benutzer = optionalBenutzer.get();
            switch (feldname) {
                case "name" -> benutzer.setName(wert);
                case "email" -> benutzer.setEmail(wert);
                default -> throw new IllegalArgumentException("nicht gültig: " + feldname);
            }
            benutzerService.speichereBenutzer(benutzer);
            m.addAttribute("benutzerid", benutzerid);
            m.addAttribute("feldname", feldname);
            m.addAttribute("wert", wert);
        }
        return "redirect:/admin/benutzer";
    }
}
