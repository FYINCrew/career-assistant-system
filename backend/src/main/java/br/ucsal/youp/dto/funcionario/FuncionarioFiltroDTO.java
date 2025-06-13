package br.ucsal.youp.dto.funcionario;

public record FuncionarioFiltroDTO(String nome, String email, Long cargoId, Long tempoExperiencia, Boolean ensinoSuperior) {
}
