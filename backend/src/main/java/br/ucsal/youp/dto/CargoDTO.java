package br.ucsal.youp.dto;

import br.ucsal.youp.model.Habilidade;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.Set;

public record CargoDTO(Long id, @NotEmpty String nome, @NotEmpty Set<Long> habilidadesId) {
}
