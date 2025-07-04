package br.ucsal.youp.mapper;

import br.ucsal.youp.dto.funcionario.FuncionarioDTO;
import br.ucsal.youp.model.Funcionario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ExperienciaMapper.class, ScoreMapper.class})
public interface FuncionarioMapper {
    
    @Mapping(target = "cargoAtual", ignore = true)
    @Mapping(target = "cargoDesejado", ignore = true)
    Funcionario toFuncionario(FuncionarioDTO funcionarioDTO);
    
    // @AfterMapping
    // default void setRelations(@MappingTarget Funcionario funcionario) {
    //     if (funcionario.getExperiencias() != null) {
    //         funcionario.getExperiencias().forEach(exp -> {
    //             exp.setFuncionario(funcionario); // Isso é necessário para o JPA
    //             if (exp.getScores() != null) {
    //                 exp.getScores().forEach(score -> score.setExperiencia(exp));
    //             }
    //         });
    //     }
    // }
}