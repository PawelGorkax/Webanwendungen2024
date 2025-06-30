package de.hsrm.mi.web.projekt.services.ort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.entities.ort.Ort;
import de.hsrm.mi.web.projekt.entities.ort.OrtRepository;
import de.hsrm.mi.web.projekt.services.geo.GeoAdresse;
import de.hsrm.mi.web.projekt.services.geo.GeoService;

@Service
public class OrtServiceImpl implements OrtService {

    private final OrtRepository ortRepository;
    private final GeoService geoService;
    
        public OrtServiceImpl(OrtRepository ortRepository, GeoService geoService) {
        this.ortRepository = ortRepository;
        this.geoService = geoService;
    }

    @Override
    public List<Ort> findeOrtsvorschlaegeFuerAdresse(String ort) {
        List<GeoAdresse> geoAdressen = geoService.findeAdressen(ort);
        return geoAdressen.stream()
                .map(geoAdresse -> new Ort(geoAdresse.display_name(), geoAdresse.lat(), geoAdresse.lon()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Ort> findAll() {
        return ortRepository.findAll();
    }

    @Override
    public Optional<Ort> findById(long id) {
        return ortRepository.findById(id);
    }

    @Override
    public Ort save(Ort ort) {
        return ortRepository.save(ort);
    }

    @Override
    public void deleteById(long id) {
        ortRepository.deleteById(id);
    }
}
