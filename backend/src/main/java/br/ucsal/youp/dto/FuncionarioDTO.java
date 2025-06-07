package br.ucsal.youp.dto;
import java.util.List;

public record FuncionarioDTO(Long id, String nome, String email, String senha,
                             List<ExperienciaDTO> experiencias,
                             String cargoAtual, String cargoFuturo) {}
