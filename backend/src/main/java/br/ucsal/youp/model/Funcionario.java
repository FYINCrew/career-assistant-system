package br.ucsal.youp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String senha;

    private String experiencia;

    private double score;

    @ManyToOne
    @JoinColumn(name = "cargo_atual_id")
    private Cargo cargoAtual;

    @ManyToOne
    @JoinColumn(name = "cargo_futuro_id")
    private Cargo cargoFuturo;

//    @OneToOne(mappedBy = "funcionario")
//    private TrilhaCarreira trilhaCarreira;




}
