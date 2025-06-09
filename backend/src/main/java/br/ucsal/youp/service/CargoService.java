package br.ucsal.youp.service;

import br.ucsal.youp.dto.CargoDTO;
import br.ucsal.youp.exception.BadRequestException;
import br.ucsal.youp.mapper.CargoMapper;
import br.ucsal.youp.model.Cargo;
import br.ucsal.youp.repository.CargoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CargoService {

    private final CargoRepository cargoRepository;
    private final CargoMapper cargoMapper;


    public Page<Cargo> listAll(Pageable pageable) {
        return cargoRepository.findAll(pageable);
    }

    public List<Cargo> listAllNonPageable() {
        return cargoRepository.findAll();
    }

    public List<Cargo> findByNome(String nome){
        return cargoRepository.findByNome(nome);
    }

    public Cargo findByIdCargoOrThrowBadRequestException(long id){
        return cargoRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Cargo não encontrado"));
    }

    @Transactional
    public Cargo save(CargoDTO cargoDTO) {
        // Use o novo método que configura as relações
        Cargo cargo = cargoMapper.toCargo(cargoDTO);
        return cargoRepository.save(cargo);
    }

    // @Transactional
    // public Cargo updateScore(AddScore scoreDTO) {
    //     System.err.println("PASSOU AQUI");
    //     Cargo cargo = findByIdCargoOrThrowBadRequestException(scoreDTO.id());
    //     Map<Long, Map<String, Object>> resultado = executarScriptRaw(cargo);

    //     aplicarResultado(resultado, cargo);
    //     // Cargo cargo = findByIdCargoOrThrowBadRequestException(scoreDTO.id());
    //     // cargo.setScore(scoreDTO.score());
    //     cargoRepository.save(cargo);
    //     return cargo;
    // }

    // public Cargo findByEmailAndSenha(String email, String senha) {
    //     return cargoRepository.findByEmailAndSenha(email, senha);
    // }

    @Transactional
    public void delete(long id) {
        cargoRepository.delete(findByIdCargoOrThrowBadRequestException(id));
    }

    @Transactional
    public Cargo replace(CargoDTO cargoDTO) {
        // Use o mesmo método com relações
        Cargo cargo = cargoMapper.toCargo(cargoDTO);
        return cargoRepository.save(cargo);
    }

}
