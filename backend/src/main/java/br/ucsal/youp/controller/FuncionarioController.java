package br.ucsal.youp.controller;

import br.ucsal.youp.dto.funcionario.FuncionarioFiltroDTO;
import br.ucsal.youp.dto.score.AddScore;
import br.ucsal.youp.dto.funcionario.FuncionarioDTO;
import br.ucsal.youp.dto.funcionario.FuncionarioLoginDTO;
import br.ucsal.youp.dto.funcionario.FuncionarioResponseDTO;
import br.ucsal.youp.mapper.FuncionarioResponseMapper;
import br.ucsal.youp.model.Funcionario;
import br.ucsal.youp.service.FuncionarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("funcionarios")
@Log4j2
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;
    private final FuncionarioResponseMapper responseMapper;


    // @GetMapping
    // public ResponseEntity<Page<Funcionario>> list(Pageable pageable){
    //     return ResponseEntity.ok(funcionarioService.listAll(pageable));
    // }

    // @GetMapping(path = "/all")
    // public ResponseEntity<List<Funcionario>> listAll(){
    //     return ResponseEntity.ok(funcionarioService.listAllNonPageable());
    // }

    // @GetMapping(path = "/{id}")
    // public ResponseEntity<Funcionario> findById(@PathVariable long id){
    //     return ResponseEntity.ok(funcionarioService.findByIdFuncionarioOrThrowBadRequestException(id));
    // }

    @GetMapping
    public ResponseEntity<Page<FuncionarioResponseDTO>> list(Pageable pageable, @ModelAttribute FuncionarioFiltroDTO filtro) {
        Page<Funcionario> funcionarios = funcionarioService.listAll(filtro, pageable);
        return ResponseEntity.ok(funcionarios.map(responseMapper::toResponseDTO));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<FuncionarioResponseDTO>> listAll() {
        return ResponseEntity.ok(
            responseMapper.toListResponseDTO(funcionarioService.listAllNonPageable())
        );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<FuncionarioResponseDTO> findById(@PathVariable long id) {
        Funcionario funcionario = funcionarioService.findByIdFuncionarioOrThrowBadRequestException(id);
        return ResponseEntity.ok(responseMapper.toResponseDTO(funcionario));
    }

    // @GetMapping(path = "/find")
    // public ResponseEntity<List<Funcionario>> findByNome(@RequestParam String nome){
    //     return ResponseEntity.ok(funcionarioService.findByNome(nome));
    // }

//     @PostMapping
//     public ResponseEntity<Funcionario> save(@RequestBody @Valid FuncionarioDTO funcionarioDTO) {
//         return new ResponseEntity<>(funcionarioService.save(funcionarioDTO), HttpStatus.CREATED);
//     }

//     @PostMapping("/login")
//     public ResponseEntity<Funcionario> login(@RequestBody FuncionarioLoginDTO loginRequest) {
//         Funcionario funcionario = funcionarioService.findByEmailAndSenha(loginRequest.email(), loginRequest.senha());
//         if (funcionario != null) {
//             return ResponseEntity.ok(funcionario);
//         } else {
//             return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//         }
//     }

//     @DeleteMapping(path = "/{id}")
//     public ResponseEntity<Void> delete(@PathVariable long id){
//         funcionarioService.delete(id);
//         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//     }

//     @PutMapping
//     public ResponseEntity<Void> replace(@RequestBody FuncionarioDTO funcionarioDTO){
//         funcionarioService.replace(funcionarioDTO);
//         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//     }

//     @PutMapping("/ai_score")
//     public ResponseEntity<Funcionario> setScore(@RequestBody AddScore scoreDTO){
// //        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//         System.err.println("CHAMOU AQUI");
//         return ResponseEntity.ok(funcionarioService.updateScore(scoreDTO));
//     }

    @PostMapping
    public ResponseEntity<FuncionarioResponseDTO> save(@RequestBody @Valid FuncionarioDTO funcionarioDTO) {
        Funcionario savedFuncionario = funcionarioService.save(funcionarioDTO);
        return new ResponseEntity<>(responseMapper.toResponseDTO(savedFuncionario), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<FuncionarioResponseDTO> login(@RequestBody FuncionarioLoginDTO loginRequest) {
        Funcionario funcionario = funcionarioService.findByEmailAndSenha(loginRequest.email(), loginRequest.senha());
        if (funcionario != null) {
            return ResponseEntity.ok(responseMapper.toResponseDTO(funcionario));
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        funcionarioService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<FuncionarioResponseDTO> replace(@RequestBody @Valid FuncionarioDTO funcionarioDTO) {
        Funcionario updatedFuncionario = funcionarioService.replace(funcionarioDTO);
        return ResponseEntity.ok(responseMapper.toResponseDTO(updatedFuncionario));
    }

    @PutMapping("/ai_score")
    public ResponseEntity<FuncionarioResponseDTO> setScore(@RequestBody AddScore scoreDTO) {
        Funcionario updatedFuncionario = funcionarioService.updateScore(scoreDTO);
        return ResponseEntity.ok(responseMapper.toResponseDTO(updatedFuncionario));
    }
}