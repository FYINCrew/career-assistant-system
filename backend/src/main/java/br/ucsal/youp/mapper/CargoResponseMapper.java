package br.ucsal.youp.mapper;
import java.util.List;

import org.mapstruct.Mapper;

import br.ucsal.youp.dto.cargo.CargoResponseDTO;
import br.ucsal.youp.model.Cargo;

@Mapper(componentModel = "spring")
public interface CargoResponseMapper {
    CargoResponseDTO toResponseDTO(Cargo cargo);
    
    List<CargoResponseDTO> toListResponseDTO(List<Cargo> cargos);
}

