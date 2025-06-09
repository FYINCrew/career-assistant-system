package br.ucsal.youp.dto;

import java.util.List;

public record CargoResponseDTO(
    Long id,
    String nome,
    String descricao,
    List<RequisitoResponseDTO> requisitos
) {
    public record RequisitoResponseDTO(
        Long id,
        String nome,
        double score
    ) {}
}
