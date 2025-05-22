package br.ucsal.youp.dto;

import jakarta.validation.constraints.NotEmpty;

public record FuncionarioLoginDTO(@NotEmpty String email, @NotEmpty String senha) {
}
