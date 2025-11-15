package com.example.demo.service;

import com.example.demo.dto.EtudiantDTO;
import com.example.demo.exception.EmailAlreadyUsedException;
import com.example.demo.model.Etudiant;
import com.example.demo.repository.EtudiantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EtudiantServiceTest {

    @Mock
    private EtudiantRepository repository;

    @InjectMocks
    private EtudiantService service;

    private Etudiant etudiant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        etudiant = new Etudiant(1L, "Nour", "Rouissi", "nour@mail.com", "Informatique", 23);
    }

    @Test
    void testUpdateEtudiant_nomEtPrenom() {
        EtudiantDTO dto = new EtudiantDTO();
        dto.setNom("Amine");
        dto.setPrenom("Ali");

        when(repository.findById(1L)).thenReturn(Optional.of(etudiant));
        when(repository.existsByEmail(anyString())).thenReturn(false);

        service.updateEtudiant(1L, dto);

        assertEquals("Amine", etudiant.getNom());
        assertEquals("Ali", etudiant.getPrenom());
    }

    @Test
    void testUpdateEtudiant_emailChange_ok() {
        EtudiantDTO dto = new EtudiantDTO();
        dto.setEmail("nouveau@mail.com");

        when(repository.findById(1L)).thenReturn(Optional.of(etudiant));
        when(repository.existsByEmail("nouveau@mail.com")).thenReturn(false);

        service.updateEtudiant(1L, dto);

        assertEquals("nouveau@mail.com", etudiant.getEmail());
    }

    @Test
    void testUpdateEtudiant_emailDejaUtilise_exception() {
        EtudiantDTO dto = new EtudiantDTO();
        dto.setEmail("existing@mail.com");

        when(repository.findById(1L)).thenReturn(Optional.of(etudiant));
        when(repository.existsByEmail("existing@mail.com")).thenReturn(true);

        assertThrows(EmailAlreadyUsedException.class, () -> service.updateEtudiant(1L, dto));
    }

    @Test
    void testUpdateEtudiant_filiere() {
        EtudiantDTO dto = new EtudiantDTO();
        dto.setFiliere("Mathématiques");

        when(repository.findById(1L)).thenReturn(Optional.of(etudiant));
        when(repository.existsByEmail(anyString())).thenReturn(false);

        service.updateEtudiant(1L, dto);

        assertEquals("Mathématiques", etudiant.getFiliere());
    }

    @Test
    void testUpdateEtudiant_age() {
        EtudiantDTO dto = new EtudiantDTO();
        dto.setAge(25);

        when(repository.findById(1L)).thenReturn(Optional.of(etudiant));
        when(repository.existsByEmail(anyString())).thenReturn(false);

        service.updateEtudiant(1L, dto);

        assertEquals(25, etudiant.getAge());
    }

    @Test
    void testUpdateEtudiant_tousChamps() {
        EtudiantDTO dto = new EtudiantDTO();
        dto.setNom("Amine");
        dto.setPrenom("Ali");
        dto.setEmail("nouveau@mail.com");
        dto.setFiliere("Mathématiques");
        dto.setAge(25);

        when(repository.findById(1L)).thenReturn(Optional.of(etudiant));
        when(repository.existsByEmail("nouveau@mail.com")).thenReturn(false);

        service.updateEtudiant(1L, dto);

        assertEquals("Amine", etudiant.getNom());
        assertEquals("Ali", etudiant.getPrenom());
        assertEquals("nouveau@mail.com", etudiant.getEmail());
        assertEquals("Mathématiques", etudiant.getFiliere());
        assertEquals(25, etudiant.getAge());
    }
}

