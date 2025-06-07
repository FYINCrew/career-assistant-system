package br.ucsal.youp.service;


import br.ucsal.youp.dto.AddScore;
import br.ucsal.youp.dto.FuncionarioDTO;
import br.ucsal.youp.exception.BadRequestException;
import br.ucsal.youp.mapper.FuncionarioMapper;
import br.ucsal.youp.model.Experiencia;
import br.ucsal.youp.model.Funcionario;
import br.ucsal.youp.model.Score;
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
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioMapper funcionarioMapper;


    public Page<Funcionario> listAll(Pageable pageable) {
        return funcionarioRepository.findAll(pageable); // Agora funcionará corretamente
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
        // Use o novo método que configura as relações
        Funcionario funcionario = funcionarioMapper.toFuncionario(funcionarioDTO);
        return funcionarioRepository.save(funcionario);
    }

    @Transactional
    public Funcionario updateScore(AddScore scoreDTO) {
        System.err.println("PASSOU AQUI");
        Funcionario funcionario = findByIdFuncionarioOrThrowBadRequestException(scoreDTO.id());
        Map<Long, Map<String, Object>> resultado = executarScriptRaw(funcionario);

        aplicarResultado(resultado, funcionario);
        // Funcionario funcionario = findByIdFuncionarioOrThrowBadRequestException(scoreDTO.id());
        // funcionario.setScore(scoreDTO.score());
        funcionarioRepository.save(funcionario);
        return funcionario;
    }

    public Map<Long, Map<String, Object>> executarScriptRaw(Funcionario funcionario) {
        Map<Long, Map<String, Object>> resultado = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            String experienciasJson = mapper.writeValueAsString(funcionario); // Envie o objeto completo

            ProcessBuilder processBuilder = new ProcessBuilder(
                "C:\\Users\\prjlu\\Desktop\\tcc\\career-assistant-system\\venv\\Scripts\\python.exe",
                "C:/Users/prjlu/Desktop/tcc/career-assistant-system/backend/src/main/java/br/ucsal/youp/scripts/script.py"
            );
            
            processBuilder.redirectErrorStream(true);
            
            Process process = processBuilder.start();

            try (OutputStream os = process.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
                osw.write(experienciasJson);
            } 

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                System.out.println(line);

                if (line.startsWith("{") && line.contains("\"id\"") && line.contains("\"score\"")) {
                    try {
                        Map<String, Object> map = mapper.readValue(line, Map.class);

                        Long id = Long.parseLong(map.get("id").toString());
                        double totalScore = Double.parseDouble(map.get("total_score").toString());

                        List<Map<String, Object>> scoreList = (List<Map<String, Object>>) map.get("scores");

                        Map<String, Object> dadosExperiencia = new HashMap<>();
                        dadosExperiencia.put("total_score", totalScore);
                        dadosExperiencia.put("scores", scoreList);

                        resultado.put(id, dadosExperiencia);

                    } catch (Exception e) {
                        System.err.println("Erro ao processar linha JSON: " + line);
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }

    public void aplicarResultado(Map<Long, Map<String, Object>> resultado, Funcionario funcionario) {
        for (Experiencia exp : funcionario.getExperiencias()) {
            Map<String, Object> dados = resultado.get(exp.getId());
            if (dados != null) {
                exp.setTotal_score((Double) dados.get("total_score"));

                List<Map<String, Object>> novosScores = (List<Map<String, Object>>) dados.get("scores");

                Map<String, Score> existentes = exp.getScores().stream()
                    .collect(Collectors.toMap(Score::getTecnologia, s -> s));

                for (Map<String, Object> novo : novosScores) {
                    String tecnologia = (String) novo.get("tecnologia");
                    double valorScore = Double.parseDouble(novo.get("score").toString());

                    Score existente = existentes.get(tecnologia);
                    if (existente != null) {
                        existente.setScore(valorScore);  // corrigido aqui, usar setter
                    } else {
                        Score novoScore = new Score(null, tecnologia, valorScore, exp);
                        exp.getScores().add(novoScore);
                    }
                }
            }
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
    public Funcionario replace(FuncionarioDTO funcionarioDTO) {
        // Use o mesmo método com relações
        Funcionario funcionario = funcionarioMapper.toFuncionario(funcionarioDTO);
        return funcionarioRepository.save(funcionario);
    }

}
