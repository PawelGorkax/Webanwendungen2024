package de.hsrm.mi.web.projekt.services.benutzer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.entities.benutzer.Benutzer;
import de.hsrm.mi.web.projekt.entities.benutzer.BenutzerRepository;
import jakarta.transaction.Transactional;

@Service
public class BenutzerUserDetailsService implements UserDetailsService {

    @Autowired
    private BenutzerRepository benutzerRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Benutzer benutzer = benutzerRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Benutzer nicht gefunden"));

        benutzer.getWunschListe().size();
        String role = benutzer.getWunschListe().contains("MACHT") ? "CHEF" : "USER";

        return User.withUsername(benutzer.getEmail())
            .password(benutzer.getPasswort())
            .roles(role)
            .build();
    }


}
