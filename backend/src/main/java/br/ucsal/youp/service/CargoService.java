package br.ucsal.youp.service;

import br.ucsal.youp.dto.CargoDTO;
import br.ucsal.youp.dto.HabilidadeDTO;
import br.ucsal.youp.exception.BadRequestException;
import br.ucsal.youp.mapper.CargoMapper;
import br.ucsal.youp.mapper.HabilidadeMapper;
import br.ucsal.youp.model.Cargo;
import br.ucsal.youp.model.Habilidade;
import br.ucsal.youp.repository.CargoRepository;
import br.ucsal.youp.repository.HabilidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CargoService {

    private final CargoRepository cargoRepository;

    private final HabilidadeRepository habilidadeRepository;

    public Page<Cargo> listAll(Pageable pageable){
        return cargoRepository.findAll(pageable);
    }

    public List<Cargo> listAllNonPageable() {
        return cargoRepository.findAll();
    }

    public List<Cargo> findByNome(String nome){
        return cargoRepository.findByNome(nome);
    }

    public Cargo findByIdOrThrowBadRequestException(long id){

        return cargoRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Cargo não encontrado"));
    }

    @Transactional
    public Cargo save(CargoDTO cargoDTO) {
        Cargo cargo = CargoMapper.INSTANCE.toCargo(cargoDTO);
        Set<Habilidade> habilidades = cargoDTO.habilidadesId().stream()
                .map(id -> habilidadeRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Habilidade não encontrada: " + id)))
                .collect(Collectors.toSet());
        cargo.setHabilidades(habilidades);
        return cargoRepository.save(cargo);
    }


    @Transactional
    public void delete(long id) {
        cargoRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    @Transactional
    public void replace(CargoDTO cargoDTO) {
        Cargo cargo = CargoMapper.INSTANCE.toCargo(cargoDTO);
        Set<Habilidade> habilidades = cargoDTO.habilidadesId().stream()
                .map(id -> habilidadeRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Habilidade não encontrada: " + id)))
                .collect(Collectors.toSet());
        cargo.setHabilidades(habilidades);
        cargoRepository.save(cargo);

    }
}
