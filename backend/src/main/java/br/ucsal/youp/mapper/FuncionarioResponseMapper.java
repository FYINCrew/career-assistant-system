package br.ucsal.youp.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.ucsal.youp.dto.funcionario.FuncionarioResponseDTO;
import br.ucsal.youp.model.Experiencia;
import br.ucsal.youp.model.Funcionario;
import br.ucsal.youp.model.Score;

@Mapper(componentModel = "spring")
public interface FuncionarioResponseMapper {
    
    @Mapping(target = "cargoAtual", source = "cargoAtual")
    @Mapping(target = "cargoDesejado", source = "cargoDesejado")
    FuncionarioResponseDTO toResponseDTO(Funcionario funcionario);
    
    List<FuncionarioResponseDTO> toListResponseDTO(List<Funcionario> funcionarios);
    
    @Mapping(target = "scores", source = "scores")
    FuncionarioResponseDTO.ExperienciaResponseDTO experienciaToDTO(Experiencia experiencia);
    
    FuncionarioResponseDTO.ScoreResponseDTO scoreToDTO(Score score);
}
