package br.ucsal.youp.dto;

import java.util.List;

public record CargoDTO(String nome, String descricao, List<RequisitoDTO> requisitos) {}
