package br.ucsal.youp.repository;

import br.ucsal.youp.model.Funcionario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>, JpaSpecificationExecutor<Funcionario> {

    List<Funcionario> findByNome(String nome);
    Funcionario findByEmailAndSenha(String email, String senha);

    @EntityGraph(attributePaths = {"experiencias"})
    Optional<Funcionario> findWithExperienciasById(Long id);
    
    @EntityGraph(attributePaths = {"experiencias"})
    Page<Funcionario> findAll(Pageable pageable);

}