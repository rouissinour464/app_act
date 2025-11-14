package com.example.gestion_etudiants.service;

import com.example.gestion_etudiants.dto.EtudiantDto;
import java.util.List;

public interface EtudiantService {
    EtudiantDto creer(EtudiantDto dto);

    EtudiantDto obtenir(Long id);

    List<EtudiantDto> lister();

    EtudiantDto mettreAJour(Long id, EtudiantDto dto);

    void supprimer(Long id);
}
