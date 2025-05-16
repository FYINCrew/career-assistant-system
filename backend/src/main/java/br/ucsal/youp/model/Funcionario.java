package br.ucsal.youp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "cargo_atual_id")
    private Cargo cargoAtual;

    private String senha;

    private String experiencia;

    @OneToMany
    private List<Habilidade> habilidades;

    @OneToOne(mappedBy = "funcionario")
    private TrilhaCarreira trilhaCarreira;




}
