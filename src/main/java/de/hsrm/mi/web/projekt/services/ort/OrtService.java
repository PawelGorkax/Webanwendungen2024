package de.hsrm.mi.web.projekt.services.ort;

import java.util.List;
import java.util.Optional;

import de.hsrm.mi.web.projekt.entities.ort.Ort;

public interface OrtService {
    List<Ort> findeOrtsvorschlaegeFuerAdresse(String ort);
    List<Ort> findAll();
    Optional<Ort> findById(long id);
    
    Ort save(Ort ort);
    void deleteById(long id);
}
