package br.ucsal.youp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @JsonManagedReference // Indica o lado "pai" da relação
    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL)
    private List<Experiencia> experiencias = new ArrayList<>();
    
    // Método auxiliar para adicionar experiências
    public void addExperiencia(Experiencia experiencia) {
        experiencias.add(experiencia);
        experiencia.setFuncionario(this);
    }
    
    private String cargoAtual;

    private String cargoFuturo;
}
