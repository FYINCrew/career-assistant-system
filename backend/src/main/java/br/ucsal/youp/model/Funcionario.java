package br.ucsal.youp.model;
import java.util.Map;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
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

    // private double score;

    private String cargoAtual;

    private String cargoFuturo;

    private Set<String> tecnologias = new HashSet<>();

    private List<Map<String, Object>> scores = new ArrayList<>(); // ZERADO ATÉ A IA POPULAR

    // String = Tecnologia
    // Object = HashMap 
    // A IA VAI POPULAR ISSO:
    // HasmMap:
        // Map<String, Object> score_obj = new HashMap<>();
        // score_obj.put("tecnologia", "python");
        // score_obj.put("score", 30);
    // Object = score_obj

}
