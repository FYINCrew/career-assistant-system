package br.ucsal.youp.dto;


import br.ucsal.youp.model.Cargo;
import br.ucsal.youp.model.Habilidade;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;
import java.util.Set;


@Builder
public record FuncionarioDTO(Long id,  @NotEmpty String nome, @NotEmpty String email, @NotEmpty String senha,
                             @NotEmpty String experiencia, Long cargoAtualId,
                             Long cargoFuturoId, Double score) {
}
