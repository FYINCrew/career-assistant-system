package br.ucsal.youp.service;


import br.ucsal.youp.dto.AddScore;
import br.ucsal.youp.dto.FuncionarioDTO;
import br.ucsal.youp.exception.BadRequestException;
import br.ucsal.youp.mapper.FuncionarioMapper;
import br.ucsal.youp.model.Funcionario;
import br.ucsal.youp.repository.FuncionarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;


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
        return funcionarioRepository.save(funcionario);
    }

    @Transactional
    public Funcionario updateScore(AddScore scoreDTO) {
        Funcionario funcionario = executarScript(findByIdFuncionarioOrThrowBadRequestException(scoreDTO.id()));
//        Funcionario funcionario = findByIdFuncionarioOrThrowBadRequestException(scoreDTO.id());
        funcionario.setScore(scoreDTO.score());
        funcionarioRepository.save(funcionario);
        return funcionario;
    }


    public Funcionario executarScript(Funcionario funcionario){
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "python3",
                    "backend/src/main/java/br/ucsal/youp/scripts/script.py"
            );
            processBuilder.inheritIO();
            Process process = processBuilder.start();


            ObjectMapper mapper = new ObjectMapper();
            String jsonInput = mapper.writeValueAsString(funcionario);


            OutputStream os = process.getOutputStream();
            os.write(jsonInput.getBytes());
            os.flush();
            os.close();


            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }


            Funcionario funcionarioAtualizado = mapper.readValue(output.toString(), Funcionario.class);
            return funcionarioAtualizado;

        } catch (Exception e) {
            e.printStackTrace();
            return funcionario;
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
        Funcionario funcionario = FuncionarioMapper.INSTANCE.toFuncionario(funcionarioDTO);
        funcionarioRepository.save(funcionario);

    }

}
