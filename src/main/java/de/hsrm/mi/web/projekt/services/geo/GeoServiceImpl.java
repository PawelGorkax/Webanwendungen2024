package de.hsrm.mi.web.projekt.services.geo;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GeoServiceImpl implements GeoService {

    private final WebClient webClient = WebClient.create("https://nominatim.openstreetmap.org");


    @Override
    public List<GeoAdresse> findeAdressen(String ort) {
        if (ort == null) {
            System.err.println("Der übergebene Ort ist null");
            return List.of();
        }

        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/search")
                            .queryParam("q", ort)
                            .queryParam("format", "json")
                            .queryParam("countrycodes", "de")
                            .build())
                    .retrieve()
                    .bodyToFlux(GeoAdresse.class)
                    .filter(geoAdresse -> List.of("city", "town", "village").contains(geoAdresse.addresstype()))
                    .collectList()
                    .block();
        } catch (Exception e) {
            System.err.println("Fehler beim Abrufen der Geo-Daten: " + e.getMessage());
            return List.of();
        }
    }
}
