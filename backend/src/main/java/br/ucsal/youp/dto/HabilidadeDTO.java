package br.ucsal.youp.dto;

import jakarta.validation.constraints.NotEmpty;

public record HabilidadeDTO(Long id, @NotEmpty String nome, @NotEmpty String tipo, @NotEmpty String descricao) {


}
