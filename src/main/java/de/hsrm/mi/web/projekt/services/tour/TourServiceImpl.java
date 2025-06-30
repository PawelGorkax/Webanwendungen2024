package de.hsrm.mi.web.projekt.services.tour;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.entities.benutzer.Benutzer;
import de.hsrm.mi.web.projekt.entities.benutzer.BenutzerRepository;
import de.hsrm.mi.web.projekt.entities.ort.OrtRepository;
import de.hsrm.mi.web.projekt.entities.tour.Tour;
import de.hsrm.mi.web.projekt.entities.tour.TourRepository;
import de.hsrm.mi.web.projekt.messaging.FrontendNachrichtEvent;
import de.hsrm.mi.web.projekt.messaging.FrontendNachrichtService;

@Service
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;
    private final OrtRepository ortRepository;
    private final BenutzerRepository benutzerRepository;
    private final FrontendNachrichtService frontendNachrichtService;

    public TourServiceImpl(TourRepository tourRepository,
                            OrtRepository ortRepository,
                            BenutzerRepository benutzerRepository, 
                            FrontendNachrichtService frontendNachrichtService) {
        this.tourRepository = tourRepository;
        this.ortRepository = ortRepository;
        this.benutzerRepository = benutzerRepository;
        this.frontendNachrichtService = frontendNachrichtService;
    }

    @Override
    public List<Tour> findAll() {
        return tourRepository.findAll();
    }

    @Override
    public Optional<Tour> findById(long id) {
        return tourRepository.findById(id);
    }

    @Override
    public Tour speichereTourangebot(long anbieterId, Tour tour, long startortId, long zielortId) {
        Benutzer anbieter = benutzerRepository.findById(anbieterId).orElseThrow();
        tour.setAnbieter(anbieter);
        tour.setStartOrt(ortRepository.findById(startortId).orElseThrow());
        tour.setZielOrt(ortRepository.findById(zielortId).orElseThrow());
        Tour savedTour = tourRepository.save(tour);
        frontendNachrichtService.sendEvent(new FrontendNachrichtEvent
                                            (FrontendNachrichtEvent.EventType.TOUR, savedTour.getId(),
                                             FrontendNachrichtEvent.OperationType.CREATE));
        return savedTour;
    }

    @Override
    public void deleteById(long id) {
        Optional<Tour> tourOpt = tourRepository.findById(id);
        if (tourOpt.isPresent()) {
            Tour tour = tourOpt.get();
            String currentUsername = getCurrentUsername();
            if (tour.getAnbieter().getEmail().equals(currentUsername)) {
                tourRepository.deleteById(id);
                frontendNachrichtService.sendEvent(new FrontendNachrichtEvent
                                                    (FrontendNachrichtEvent.EventType.TOUR, id,
                                                     FrontendNachrichtEvent.OperationType.DELETE));
            } else {
                throw new SecurityException("Nicht berechtigt, diese Tour zu löschen");
            }
        }
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
