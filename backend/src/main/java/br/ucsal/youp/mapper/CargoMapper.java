package br.ucsal.youp.mapper;

import br.ucsal.youp.dto.CargoDTO;
import br.ucsal.youp.model.Cargo;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {RequisitoMapper.class})
public interface CargoMapper {
    
    Cargo toCargo(CargoDTO cargoDTO);
    
    @AfterMapping
    default void setRelations(@MappingTarget Cargo cargo) {
        if (cargo.getRequisitos() != null) {
            cargo.getRequisitos().forEach(req -> {
                req.setCargo(cargo); // Isso é necessário para o JPA
            });
        }
    }
}