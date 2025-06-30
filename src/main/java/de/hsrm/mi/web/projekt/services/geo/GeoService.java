package de.hsrm.mi.web.projekt.services.geo;

import java.util.List;

public interface GeoService {
    List<GeoAdresse> findeAdressen(String ort);
    }