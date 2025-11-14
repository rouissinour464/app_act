package com.example.gestion_etudiants.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EtudiantDto {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String filiere;
    private Integer age;
}
