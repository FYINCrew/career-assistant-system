package br.ucsal.youp.repository;

import br.ucsal.youp.model.Cargo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {

    List<Cargo> findByNome(String nome);
    // Cargo findByIdCargo(Long Id);

    @EntityGraph(attributePaths = {"requisitos"})
    Optional<Cargo> findWithRequisitosById(Long id);
    
    @EntityGraph(attributePaths = {"requisitos"})
    Page<Cargo> findAll(Pageable pageable);

}