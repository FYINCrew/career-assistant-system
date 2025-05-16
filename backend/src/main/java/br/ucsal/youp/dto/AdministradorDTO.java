package br.ucsal.youp.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record AdministradorDTO(Long id, String nome, String email, String senha) {


}
