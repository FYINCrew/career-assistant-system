package br.ucsal.youp.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;
import java.util.Set;


@Builder
public record FuncionarioDTO(Long id, @NotEmpty String nome, @NotEmpty String email, @NotEmpty String senha,
                             @NotEmpty String experiencia, @NotEmpty String cargoAtual,
                             @NotEmpty String cargoFuturo, @NotEmpty Set<String> tecnologias, Double score) {
}
