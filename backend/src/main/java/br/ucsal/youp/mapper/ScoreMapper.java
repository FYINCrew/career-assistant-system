package br.ucsal.youp.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.ucsal.youp.dto.ScoreDTO;
import br.ucsal.youp.model.Score;

@Mapper(componentModel = "spring")
public interface ScoreMapper {
    
    @Mapping(target = "experiencia", ignore = true) // Ignora o mapeamento da experiência
    Score toScore(ScoreDTO dto);
    
    List<Score> toScores(List<ScoreDTO> dtos);
}