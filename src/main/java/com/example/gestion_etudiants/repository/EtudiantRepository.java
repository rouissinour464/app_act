package com.example.gestion_etudiants.repository;

import com.example.gestion_etudiants.model.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    Optional<Etudiant> findByEmail(String email);

    boolean existsByEmail(String email);
}
