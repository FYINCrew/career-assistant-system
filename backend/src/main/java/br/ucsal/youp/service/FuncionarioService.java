package br.ucsal.youp.service;


import br.ucsal.youp.dto.AddScore;
import br.ucsal.youp.dto.FuncionarioDTO;
import br.ucsal.youp.exception.BadRequestException;
import br.ucsal.youp.mapper.FuncionarioMapper;
import br.ucsal.youp.model.Funcionario;
import br.ucsal.youp.repository.CargoRepository;
import br.ucsal.youp.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    private final CargoRepository cargoRepository;

    public Page<Funcionario> listAll(Pageable pageable){
        return funcionarioRepository.findAll(pageable);
    }

    public List<Funcionario> listAllNonPageable() {
        return funcionarioRepository.findAll();
    }

    public List<Funcionario> findByNome(String nome){
        return funcionarioRepository.findByNome(nome);
    }

    public Funcionario findByIdFuncionarioOrThrowBadRequestException(long id){

        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Funcionário não encontrado"));
    }



    @Transactional
    public Funcionario save(FuncionarioDTO funcionarioDTO) {
        Funcionario funcionario = FuncionarioMapper.INSTANCE.toFuncionario(funcionarioDTO);
        funcionario.setCargoAtual(cargoRepository.findById(funcionarioDTO.cargoAtualId()).
                orElseThrow(() -> new RuntimeException("Cargo não encontrado")));
        funcionario.setCargoFuturo(cargoRepository.findById(funcionarioDTO.cargoFuturoId()).
                orElseThrow(() -> new RuntimeException("Cargo não encontrado")));
        return funcionarioRepository.save(funcionario);
    }

    @Transactional
    public void updateScore(AddScore scoreDTO) {
        executarScript();
        Funcionario funcionario = findByIdFuncionarioOrThrowBadRequestException(scoreDTO.id());
        funcionario.setScore(scoreDTO.score());
        funcionarioRepository.save(funcionario);
    }

    public void executarScript(){
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python3",
                    "src/main/java/br/ucsal/youp/scripts/script.py");
            processBuilder.inheritIO();
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            System.out.println("Script finalizado com código: " + exitCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Funcionario findByEmailAndSenha(String email, String senha) {
        return funcionarioRepository.findByEmailAndSenha(email, senha);
    }

    @Transactional
    public void delete(long id) {
        funcionarioRepository.delete(findByIdFuncionarioOrThrowBadRequestException(id));
    }

    @Transactional
    public void replace(FuncionarioDTO funcionarioDTO) {
//       Funcionario savedFuncionario = findByIdOrThrowBadRequestException(funcionarioDTO.id());
        Funcionario funcionario = FuncionarioMapper.INSTANCE.toFuncionario(funcionarioDTO);
        funcionarioRepository.save(funcionario);

    }

}
