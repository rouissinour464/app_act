package com.example.gestion_etudiants.exception;

public class EtudiantNotFoundException extends RuntimeException {
    public EtudiantNotFoundException(String message) {
        super(message);
    }
}
