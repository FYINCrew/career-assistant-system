package br.ucsal.youp.dto;

import java.util.List;

public record CargoDTO(Long id, String nome, String descricao, List<RequisitoDTO> requisitos) {}
