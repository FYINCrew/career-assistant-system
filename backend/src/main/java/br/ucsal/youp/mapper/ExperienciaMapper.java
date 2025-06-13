package br.ucsal.youp.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.ucsal.youp.dto.experiencia.ExperienciaDTO;
import br.ucsal.youp.model.Experiencia;

@Mapper(componentModel = "spring", uses = ScoreMapper.class)
public interface ExperienciaMapper {
    
    @Mapping(target = "funcionario", ignore = true) // Ignora o mapeamento do funcionário
    Experiencia toExperiencia(ExperienciaDTO dto);
    
    List<Experiencia> toExperiencias(List<ExperienciaDTO> dtos);
}