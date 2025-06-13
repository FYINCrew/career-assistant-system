package br.ucsal.youp.dto.funcionario;

import jakarta.validation.constraints.NotEmpty;

public record FuncionarioLoginDTO(@NotEmpty String email, @NotEmpty String senha) {
}
