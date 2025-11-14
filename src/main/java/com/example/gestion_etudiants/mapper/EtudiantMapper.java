package com.example.gestion_etudiants.mapper;

import com.example.gestion_etudiants.dto.EtudiantDto;
import com.example.gestion_etudiants.model.Etudiant;

public class EtudiantMapper {

    public static EtudiantDto toDto(Etudiant e) {
        if (e == null)
            return null;
        return EtudiantDto.builder()
                .id(e.getId())
                .nom(e.getNom())
                .prenom(e.getPrenom())
                .email(e.getEmail())
                .filiere(e.getFiliere())
                .age(e.getAge())
                .build();
    }

    public static Etudiant toEntity(EtudiantDto d) {
        if (d == null)
            return null;
        return Etudiant.builder()
                .id(d.getId())
                .nom(d.getNom())
                .prenom(d.getPrenom())
                .email(d.getEmail())
                .filiere(d.getFiliere())
                .age(d.getAge())
                .build();
    }
}
