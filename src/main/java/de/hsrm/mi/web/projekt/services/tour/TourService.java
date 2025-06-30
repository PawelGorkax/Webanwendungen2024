package de.hsrm.mi.web.projekt.services.tour;

import java.util.List;
import java.util.Optional;

import de.hsrm.mi.web.projekt.entities.tour.Tour;

public interface TourService {
    List<Tour> findAll();
    Optional<Tour> findById(long id);
    Tour speichereTourangebot(long anbieterId, Tour tour, long startortId, long zielortId);
    void deleteById(long id);
}
