package de.hsrm.mi.web.projekt.api.tour;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.web.projekt.entities.tour.Tour;
import de.hsrm.mi.web.projekt.services.tour.TourDTD;
import de.hsrm.mi.web.projekt.services.tour.TourService;

@RestController
@RequestMapping("/api/tour")
public class TourApiController {

    @Autowired
    private TourService tourService;

    @GetMapping
    public List<TourDTD> getAllTours() {
        return tourService.findAll().stream()
            .map(TourDTD::fromTour)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourDTD> getTourById(@PathVariable long id) {
        Optional<Tour> optionalTour = tourService.findById(id);
        if (optionalTour.isPresent()) {
            TourDTD tourDTO = TourDTD.fromTour(optionalTour.get());
            return ResponseEntity.ok(tourDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
