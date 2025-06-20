package br.ucsal.youp.controller;

import br.ucsal.youp.dto.cargo.CargoDTO;
import br.ucsal.youp.dto.cargo.CargoFiltroDTO;
import br.ucsal.youp.dto.cargo.CargoResponseDTO;
import br.ucsal.youp.mapper.CargoResponseMapper;
import br.ucsal.youp.model.Cargo;
import br.ucsal.youp.service.CargoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cargos")
@Log4j2
@RequiredArgsConstructor
public class CargoController {

    private final CargoService cargoService;
    private final CargoResponseMapper responseMapper;

    // @GetMapping
    // public ResponseEntity<Page<Cargo>> list(Pageable pageable){
    //     return ResponseEntity.ok(cargoService.listAll(pageable));
    // }

    // @GetMapping(path = "/all")
    // public ResponseEntity<List<Cargo>> listAll(){
    //     return ResponseEntity.ok(cargoService.listAllNonPageable());
    // }

    // @GetMapping(path = "/{id}")
    // public ResponseEntity<Cargo> findById(@PathVariable long id){
    //     return ResponseEntity.ok(cargoService.findByIdCargoOrThrowBadRequestException(id));
    // }

    @GetMapping
    public ResponseEntity<Page<CargoResponseDTO>> list(@PageableDefault(sort = "id") Pageable pageable, @ModelAttribute CargoFiltroDTO cargoFiltroDTO) {
        Page<Cargo> cargos = cargoService.listAll(pageable, cargoFiltroDTO);
        return ResponseEntity.ok(cargos.map(responseMapper::toResponseDTO));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<CargoResponseDTO>> listAll() {
        return ResponseEntity.ok(
            responseMapper.toListResponseDTO(cargoService.listAllNonPageable())
        );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CargoResponseDTO> findById(@PathVariable long id) {
        Cargo cargo = cargoService.findByIdCargoOrThrowBadRequestException(id);
        return ResponseEntity.ok(responseMapper.toResponseDTO(cargo));
    }

    // @GetMapping(path = "/find")
    // public ResponseEntity<List<Cargo>> findByNome(@RequestParam String nome){
    //     return ResponseEntity.ok(cargoService.findByNome(nome));
    // }

//     @PostMapping
//     public ResponseEntity<Cargo> save(@RequestBody @Valid CargoDTO cargoDTO) {
//         return new ResponseEntity<>(cargoService.save(cargoDTO), HttpStatus.CREATED);
//     }

//     @PostMapping("/login")
//     public ResponseEntity<Cargo> login(@RequestBody CargoLoginDTO loginRequest) {
//         Cargo cargo = cargoService.findByEmailAndSenha(loginRequest.email(), loginRequest.senha());
//         if (cargo != null) {
//             return ResponseEntity.ok(cargo);
//         } else {
//             return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//         }
//     }

//     @DeleteMapping(path = "/{id}")
//     public ResponseEntity<Void> delete(@PathVariable long id){
//         cargoService.delete(id);
//         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//     }

//     @PutMapping
//     public ResponseEntity<Void> replace(@RequestBody CargoDTO cargoDTO){
//         cargoService.replace(cargoDTO);
//         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//     }

//     @PutMapping("/ai_score")
//     public ResponseEntity<Cargo> setScore(@RequestBody AddScore scoreDTO){
// //        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//         System.err.println("CHAMOU AQUI");
//         return ResponseEntity.ok(cargoService.updateScore(scoreDTO));
//     }

    @PostMapping
    public ResponseEntity<CargoResponseDTO> save(@RequestBody @Valid CargoDTO cargoDTO) {
        Cargo savedCargo = cargoService.save(cargoDTO);
        System.out.println("Transformou");
        return new ResponseEntity<>(responseMapper.toResponseDTO(savedCargo), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        cargoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<CargoResponseDTO> replace(@RequestBody @Valid CargoDTO cargoDTO) {
        Cargo updatedCargo = cargoService.replace(cargoDTO);
        return ResponseEntity.ok(responseMapper.toResponseDTO(updatedCargo));
    }

}