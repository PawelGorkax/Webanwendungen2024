package de.hsrm.mi.web.projekt.services.benutzer;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.hsrm.mi.web.projekt.entities.benutzer.Benutzer;
import de.hsrm.mi.web.projekt.entities.benutzer.BenutzerRepository;

@Service
public class BenutzerServiceImpl implements BenutzerService {

    private final BenutzerRepository benutzerRepository;

    public BenutzerServiceImpl(BenutzerRepository benutzerRepository) {
        this.benutzerRepository = benutzerRepository;
    }

    @Override
    public List<Benutzer> holeAlleBenutzer() {
        return benutzerRepository.findAll();
    }

    @Override
    public Optional<Benutzer> holeBenutzerMitId(long id) {
        return benutzerRepository.findById(id);
    }

    @Override
    public Benutzer speichereBenutzer(Benutzer b) {
        return benutzerRepository.save(b);
    }

    @Override
    public void loescheBenutzerMitId(long id) {
        benutzerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Benutzer aktualisiereBenutzerAttribut(long id, String feldname, String wert) {
        Optional<Benutzer> optionalBenutzer = benutzerRepository.findById(id);
        if (optionalBenutzer.isPresent()) {
            Benutzer benutzer = optionalBenutzer.get();
            switch (feldname) {
                case "name" -> benutzer.setName(wert);
                case "email" -> benutzer.setEmail(wert);
                default -> throw new IllegalArgumentException("Ungültiger Feldname: " + feldname);
            }
            return benutzerRepository.save(benutzer);
        } else {
            throw new IllegalArgumentException("Ungültige Benutzer-ID: " + id);
        }
    }

    @Override
    public Optional<Benutzer> findByEmail(String email) {
        return benutzerRepository.findByEmail(email);
    }
}