package br.ucsal.youp.dto.cargo;

import java.util.List;

public record CargoResponseDTO(
    Long id,
    String nome,
    String descricao,
    String sigla,
    List<RequisitoResponseDTO> requisitos
) {
    public record RequisitoResponseDTO(
        Long id,
        String nome,
        double score
    ) {}
}
