package br.ucsal.youp.mapper;

import br.ucsal.youp.dto.AdministradorDTO;
import br.ucsal.youp.model.Administrador;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class AdministradorMapper {

    public static final AdministradorMapper INSTANCE = Mappers.getMapper(AdministradorMapper.class);
    public abstract Administrador toAdministrador(AdministradorDTO administradorDTO);
}
