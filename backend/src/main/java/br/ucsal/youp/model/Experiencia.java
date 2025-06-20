package br.ucsal.youp.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Experiencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String texto;

    @Column(name = "score_media", nullable = true)
    private Double scoreMedia;

    @JsonBackReference // Indica o lado "filho" da relação
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "experiencia", cascade = CascadeType.ALL)
    private List<Score> scores = new ArrayList<>();
    
    // Método auxiliar para adicionar scores
    public void addScore(Score score) {
        scores.add(score);
        score.setExperiencia(this);
    }
}

