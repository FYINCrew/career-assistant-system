package br.ucsal.youp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    @JsonManagedReference // Indica o lado "pai" da relação
    @OneToMany(mappedBy = "cargo", cascade = CascadeType.ALL)
    private List<Requisito> requisitos = new ArrayList<>();
    
    // Método auxiliar para adicionar requisitos
    public void addRequisito(Requisito requisito) {
        requisitos.add(requisito);
        requisito.setCargo(this);
    }
}