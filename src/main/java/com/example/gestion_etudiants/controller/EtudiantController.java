package com.example.gestion_etudiants.controller;

import com.example.gestion_etudiants.dto.EtudiantDto;
import com.example.gestion_etudiants.exception.EtudiantNotFoundException;
import com.example.gestion_etudiants.service.EtudiantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/etudiants")
public class EtudiantController {

    private final EtudiantService service;

    public EtudiantController(EtudiantService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EtudiantDto> create(@RequestBody EtudiantDto dto) {
        EtudiantDto created = service.creer(dto);
        return ResponseEntity.created(URI.create("/api/etudiants/" + created.getId())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EtudiantDto> get(@PathVariable Long id) {
        try {
            EtudiantDto dto = service.obtenir(id);
            return ResponseEntity.ok(dto);
        } catch (EtudiantNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<EtudiantDto> list() {
        return service.lister();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EtudiantDto> update(@PathVariable Long id, @RequestBody EtudiantDto dto) {
        try {
            EtudiantDto updated = service.mettreAJour(id, dto);
            return ResponseEntity.ok(updated);
        } catch (EtudiantNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            service.supprimer(id);
            return ResponseEntity.noContent().build();
        } catch (EtudiantNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
