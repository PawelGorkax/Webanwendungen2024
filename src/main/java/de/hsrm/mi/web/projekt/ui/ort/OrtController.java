package de.hsrm.mi.web.projekt.ui.ort;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.hsrm.mi.web.projekt.entities.ort.Ort;
import de.hsrm.mi.web.projekt.services.ort.OrtService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/ort")
@SessionAttributes(names = {"ortFormular", "ort"})
public class OrtController {

    private final OrtService ortService;

    public OrtController(OrtService ortService) {
        this.ortService = ortService;
    }

    @GetMapping
    public String ortListe(Model model) {
        List<Ort> orte = ortService.findAll();
        model.addAttribute("orte", orte);
        return "ortliste";
    }

    @GetMapping("/{ortId}")
    public String ortBearbeiten(@PathVariable("ortId") long ortId, Model model) {
        if (ortId == 0) {
            model.addAttribute("titel", "Neuer Ort");
            model.addAttribute("ortFormular", new OrtFormular());
            model.addAttribute("ort", new Ort());
        } else {
            Optional<Ort> optionalOrt = ortService.findById(ortId);
            if (optionalOrt.isPresent()) {
                Ort ort = optionalOrt.get();
                OrtFormular ortFormular = new OrtFormular();
                ortFormular.fromOrt(ort);
                model.addAttribute("titel", "Ort " + ortId + " bearbeiten");
                model.addAttribute("ortFormular", ortFormular);
                model.addAttribute("ort", ort);
            } else {
                return "redirect:/admin/ortNotFound/";
            }
        }
        return "ortbearbeiten";
    }

    @PostMapping("/{ortId}")
    public String ortSpeichern(@PathVariable("ortId") long ortId, 
                               @Valid @ModelAttribute("ortFormular") OrtFormular ortFormular, 
                               BindingResult bindingResult, 
                               @SessionAttribute("ort") Ort ort, 
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "ortbearbeiten";
        }

        if (ortId == 0 && ortFormular.getGeobreite() == 0.0 && ortFormular.getGeolaenge() == 0.0) {
            List<Ort> vorschlaege = ortService.findeOrtsvorschlaegeFuerAdresse(ortFormular.getName());
            if (!vorschlaege.isEmpty()) {
                Ort vorschlag = vorschlaege.get(0);
                ortFormular.setGeobreite(vorschlag.getGeobreite());
                ortFormular.setGeolaenge(vorschlag.getGeolaenge());
                model.addAttribute("info", "Formular automatisch ausgefüllt. Bitte überprüfen und bestätigen.");
            } else {
                model.addAttribute("infoerror", "Keine Vorschläge für den eingegebenen Ort gefunden.");
            }
            model.addAttribute("ortFormular", ortFormular);
            return "ortbearbeiten";
        }

        ortFormular.toOrt(ort);
        ortService.save(ort);
        return "redirect:/admin/ort";
    }

    @GetMapping("/{ortId}/del")
    public String ortLoeschen(@PathVariable("ortId") long ortId) {
        ortService.deleteById(ortId);
        return "redirect:/admin/ort";
    }
}
