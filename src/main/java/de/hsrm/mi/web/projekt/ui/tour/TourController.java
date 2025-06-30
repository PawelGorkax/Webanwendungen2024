package de.hsrm.mi.web.projekt.ui.tour;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

import de.hsrm.mi.web.projekt.entities.benutzer.Benutzer;
import de.hsrm.mi.web.projekt.entities.ort.Ort;
import de.hsrm.mi.web.projekt.entities.tour.Tour;
import de.hsrm.mi.web.projekt.services.benutzer.BenutzerService;
import de.hsrm.mi.web.projekt.services.ort.OrtService;
import de.hsrm.mi.web.projekt.services.tour.TourService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/tour")
@SessionAttributes(names = {"tourFormular", "tour"})
public class TourController {

    private final TourService tourService;
    private final BenutzerService benutzerService;
    private final OrtService ortService;

    public TourController(TourService tourService, BenutzerService benutzerService, OrtService ortService) {
        this.tourService = tourService;
        this.benutzerService = benutzerService;
        this.ortService = ortService;
    }

    @GetMapping("/admin")
    public String redirectToTourListe() {
        return "redirect:/admin/tour";
    }

    @GetMapping
    public String tourListe(Model model) {
        List<Tour> touren = tourService.findAll();
        model.addAttribute("touren", touren);
        return "tourliste";
    }

    @GetMapping("/{tourId}")
    public String tourBearbeiten(@PathVariable("tourId") long tourId, Model model) {
        if (tourId == 0) {
            model.addAttribute("titel", "Neue Tour");
            model.addAttribute("tourFormular", new TourFormular());
            model.addAttribute("tour", new Tour());
        } else {
            Optional<Tour> optionalTour = tourService.findById(tourId);
            if (optionalTour.isPresent()) {
                Tour tour = optionalTour.get();
                TourFormular tourFormular = new TourFormular();
                tourFormular.fromTour(tour);
                model.addAttribute("titel", "Tour " + tourId + " bearbeiten");
                model.addAttribute("tourFormular", tourFormular);
                model.addAttribute("tour", tour);
            } else {
                return "redirect:/admin/tourNotFound";
            }
        }
        
        List<Benutzer> benutzerListe = benutzerService.holeAlleBenutzer();
        List<Ort> ortListe = ortService.findAll();
        model.addAttribute("benutzerListe", benutzerListe);
        model.addAttribute("ortListe", ortListe);
        return "tourbearbeiten";
    }

    @PostMapping("/{tourId}")
    public String tourSpeichern(@PathVariable("tourId") long tourId, 
                                @Valid @ModelAttribute("tourFormular") TourFormular tourFormular, 
                                BindingResult bindingResult, 
                                @SessionAttribute("tour") Tour tour, 
                                Model model) {
        if (bindingResult.hasErrors()) {
            return "tourbearbeiten";
        }

        if (tourId == 0) {
            Benutzer currentUser = getCurrentUser();
            tour.setAnbieter(currentUser);
        }

        tourFormular.toTour(tour);
        Tour gespeicherteTour = tourService.speichereTourangebot(tour.getAnbieter().getId(), tour, tourFormular.getStartortId(), tourFormular.getZielortId());
        model.addAttribute("tour", gespeicherteTour);

        return "redirect:/admin/tour";
    }

    @GetMapping("/{tourId}/del")
    public String tourLoeschen(@PathVariable("tourId") long tourId) {
        tourService.deleteById(tourId);
        return "redirect:/admin/tour";
    }

    private Benutzer getCurrentUser() {
        String currentUsername = getCurrentUsername();
        return benutzerService.findByEmail(currentUsername).orElseThrow(() -> new RuntimeException("Benutzer nicht gefunden"));
    }

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
