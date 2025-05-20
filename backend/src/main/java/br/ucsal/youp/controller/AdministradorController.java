package br.ucsal.youp.controller;

import br.ucsal.youp.dto.AdministradorDTO;
import br.ucsal.youp.model.Administrador;
import br.ucsal.youp.service.FuncionarioService;
import br.ucsal.youp.service.AdministradorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("administrador")
@Log4j2
@RequiredArgsConstructor
public class AdministradorController {

    //

    @Autowired
    private final AdministradorService administradorService;


    @Autowired
    private final FuncionarioService funcionarioService;


    @GetMapping
    public ResponseEntity<Page<Administrador>> list(Pageable pageable){
        return ResponseEntity.ok(administradorService.listAll(pageable));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Administrador>> listAll(){
        return ResponseEntity.ok(administradorService.listAllNonPageable());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Administrador> findById(@PathVariable long id){
        return ResponseEntity.ok(administradorService.findByIdOrThrowBadRequestException(id));
    }

    @GetMapping(path = "/find")
    public ResponseEntity<List<Administrador>> findByNome(@RequestParam String nome){
        return ResponseEntity.ok(administradorService.findByNome(nome));
    }


    @PostMapping
    public ResponseEntity<Administrador> save(@RequestBody @Valid AdministradorDTO administradorDTO) {
        return new ResponseEntity<>(administradorService.save(administradorDTO), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        administradorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody AdministradorDTO administradorDTO){
        administradorService.replace(administradorDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
