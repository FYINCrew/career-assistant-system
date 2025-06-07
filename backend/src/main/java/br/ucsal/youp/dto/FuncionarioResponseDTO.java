package br.ucsal.youp.dto;

import java.util.List;

public record FuncionarioResponseDTO(
    Long id,
    String nome,
    String email,
    String cargoAtual,
    String cargoFuturo,
    List<ExperienciaResponseDTO> experiencias
) {
    public record ExperienciaResponseDTO(
        Long id,
        String texto,
        List<ScoreResponseDTO> scores
    ) {}
    
    public record ScoreResponseDTO(
        Long id,
        String tecnologia,
        double score
    ) {}
}
