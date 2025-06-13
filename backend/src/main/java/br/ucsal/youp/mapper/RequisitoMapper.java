package br.ucsal.youp.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.ucsal.youp.dto.requisito.RequisitoDTO;
import br.ucsal.youp.model.Requisito;

@Mapper(componentModel = "spring")
public interface RequisitoMapper {
    
    @Mapping(target = "cargo", ignore = true) // Ignora o mapeamento do funcionário
    Requisito toRequisito(RequisitoDTO dto);
    
    List<Requisito> toRequisitos(List<RequisitoDTO> dtos);
}