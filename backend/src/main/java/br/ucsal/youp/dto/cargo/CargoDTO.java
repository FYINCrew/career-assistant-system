package br.ucsal.youp.dto.cargo;

import br.ucsal.youp.dto.requisito.RequisitoDTO;

import java.util.List;

public record CargoDTO(Long id, String nome, String descricao, List<RequisitoDTO> requisitos) {}
