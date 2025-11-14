package com.example.gestion_etudiants.service.impl;

import com.example.gestion_etudiants.dto.EtudiantDto;
import com.example.gestion_etudiants.exception.EmailAlreadyUsedException;
import com.example.gestion_etudiants.exception.EtudiantNotFoundException;
import com.example.gestion_etudiants.mapper.EtudiantMapper;
import com.example.gestion_etudiants.model.Etudiant;
import com.example.gestion_etudiants.repository.EtudiantRepository;
import com.example.gestion_etudiants.service.EtudiantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EtudiantServiceImpl implements EtudiantService {

    private final EtudiantRepository repository;

    public EtudiantServiceImpl(EtudiantRepository repository) {
        this.repository = repository;
    }

    @Override
    public EtudiantDto creer(EtudiantDto dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyUsedException("Email déjà utilisé");
        }
        Etudiant saved = repository.save(EtudiantMapper.toEntity(dto));
        return EtudiantMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public EtudiantDto obtenir(Long id) {
        Etudiant e = repository.findById(id)
                .orElseThrow(() -> new EtudiantNotFoundException("Étudiant introuvable"));
        return EtudiantMapper.toDto(e);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EtudiantDto> lister() {
        return repository.findAll().stream().map(EtudiantMapper::toDto).toList();
    }

    @Override
    public EtudiantDto mettreAJour(Long id, EtudiantDto dto) {
        Etudiant e = repository.findById(id)
                .orElseThrow(() -> new EtudiantNotFoundException("Étudiant introuvable"));

        if (dto.getNom() != null)
            e.setNom(dto.getNom());
        if (dto.getPrenom() != null)
            e.setPrenom(dto.getPrenom());
        if (dto.getEmail() != null && !dto.getEmail().equals(e.getEmail())) {
            if (repository.existsByEmail(dto.getEmail()))
                throw new EmailAlreadyUsedException("Email déjà utilisé");
            e.setEmail(dto.getEmail());
        }
        if (dto.getFiliere() != null)
            e.setFiliere(dto.getFiliere());
        if (dto.getAge() != null)
            e.setAge(dto.getAge());

        return EtudiantMapper.toDto(repository.save(e));
    }

    @Override
    public void supprimer(Long id) {
        Etudiant e = repository.findById(id)
                .orElseThrow(() -> new EtudiantNotFoundException("Étudiant introuvable"));
        repository.delete(e);
    }
}
