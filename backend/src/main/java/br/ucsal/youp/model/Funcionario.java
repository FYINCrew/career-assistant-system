package br.ucsal.youp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    // Indica se possui ensino superior completo
    @Column(name = "ensino_superior")
    private Boolean ensinoSuperior;

    @Column(name = "tempo_experiencia")
    private Long tempoExperiencia; // Tempo de experiência em anos



    @JsonManagedReference // Indica o lado "pai" da relação
    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL)
    private List<Experiencia> experiencias = new ArrayList<>();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cargo_atual_id")
    @JsonBackReference
    private Cargo cargoAtual;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cargo_desejado_id")
    @JsonBackReference
    private Cargo cargoDesejado;

    // Método auxiliar para adicionar experiências
    public void addExperiencia(Experiencia experiencia) {
        experiencias.add(experiencia);
        experiencia.setFuncionario(this);
    }
}