package br.ucsal.youp.dto;

import br.ucsal.youp.model.Cargo;
import br.ucsal.youp.model.Funcionario;
import br.ucsal.youp.model.Habilidade;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;

@Builder
public record TrilhaCarreiraDTO(Long id, String nome, List<Habilidade> habilidades,
                                Cargo cargo, Funcionario funcionario, Double score) {
}
