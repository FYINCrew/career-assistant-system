package br.ucsal.youp.dto;

import jakarta.validation.constraints.NotEmpty;

public record HabilidadeDTO(Long id, String nome, String tipo, String descricao) {


}
