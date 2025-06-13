package br.ucsal.youp.dto.funcionario;
import br.ucsal.youp.dto.experiencia.ExperienciaDTO;

import java.util.List;

public record FuncionarioDTO(Long id,
    String nome,
    String email,
    String senha,
    List<ExperienciaDTO> experiencias,
    Long cargoAtualId,
    Long cargoDesejadoId,
    Long tempoExperiencia,
    Boolean ensinoSuperior
    ) {}
