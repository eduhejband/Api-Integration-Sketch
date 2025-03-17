package com.example.medicamentos_service.model;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medicamento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medicamento_seq")
    @SequenceGenerator(name = "medicamento_seq", sequenceName = "medicamento_sequence", allocationSize = 1)
    private Long id;
    private String nome;
    private boolean usoControlado;
    private String localizacao; // Novo campo para armazenar a localização
}
