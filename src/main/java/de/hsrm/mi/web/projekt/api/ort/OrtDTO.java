package de.hsrm.mi.web.projekt.api.ort;

import de.hsrm.mi.web.projekt.entities.ort.Ort;

public record OrtDTO(long id, String name, double geoBreite, double geoLaenge) {

    public static OrtDTO fromOrt(Ort ort) {
        return new OrtDTO(ort.getId(), ort.getName(), ort.getGeobreite(), ort.getGeolaenge());
    }

    public static Ort toOrt(OrtDTO dto) {
        Ort ort = new Ort();
        ort.setName(dto.name());
        ort.setGeobreite(dto.geoBreite());
        ort.setGeolaenge(dto.geoLaenge());
        return ort;
    }
}
