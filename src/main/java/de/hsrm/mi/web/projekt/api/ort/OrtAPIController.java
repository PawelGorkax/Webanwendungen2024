package de.hsrm.mi.web.projekt.api.ort;

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

import de.hsrm.mi.web.projekt.entities.ort.Ort;
import de.hsrm.mi.web.projekt.services.ort.OrtService;

@RestController
@RequestMapping("/api/ort")
public class OrtAPIController {
    @Autowired
    private OrtService ortService;

    @GetMapping
    public List<OrtDTO> getAlleOrte() {
        return ortService.findAll().stream().map(OrtDTO::fromOrt).collect(Collectors.toList());
    }
  @GetMapping("/{id}")
    public ResponseEntity<OrtDTO> getOrtById(@PathVariable long id) {
        Optional<Ort> optionalOrt = ortService.findById(id);
        if (optionalOrt.isPresent()) {
            OrtDTO ortDTO = OrtDTO.fromOrt(optionalOrt.get());
            return ResponseEntity.ok(ortDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
