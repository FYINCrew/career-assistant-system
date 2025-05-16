package br.ucsal.youp.dto;


import br.ucsal.youp.model.Cargo;
import br.ucsal.youp.model.Habilidade;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;


@Builder
public record FuncionarioDTO(Long id,  String nome, String email, String senha,
                             String experiencia, Cargo cargoAtual,
                             List<Habilidade> habilidades) {
}
