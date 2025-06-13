package br.ucsal.youp.dto.experiencia;

import br.ucsal.youp.dto.score.ScoreDTO;

import java.util.List;

public record ExperienciaDTO(String texto, List<ScoreDTO> scores) {}

