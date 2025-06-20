package br.ucsal.youp.dto.funcionario;

import br.ucsal.youp.dto.cargo.CargoDTO;

import java.util.List;

public record FuncionarioResponseDTO(
    Long id,
    String nome,
    String email,
    Boolean ensinoSuperior,
    Long tempoExperiencia,
    CargoDTO cargoAtual,
    CargoDTO cargoDesejado,
    List<ExperienciaResponseDTO> experiencias

) {
    public record ExperienciaResponseDTO(
        Long id,
        String texto,
        List<ScoreResponseDTO> scores,
        double scoreMedia
    ) {}
    
    public record ScoreResponseDTO(
        Long id,
        String tecnologia,
        double score
    ) {}
}
