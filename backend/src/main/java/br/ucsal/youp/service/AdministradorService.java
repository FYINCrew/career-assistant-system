package br.ucsal.youp.service;


import br.ucsal.youp.dto.AdministradorDTO;
import br.ucsal.youp.exception.BadRequestException;
import br.ucsal.youp.mapper.AdministradorMapper;
import br.ucsal.youp.model.Administrador;
import br.ucsal.youp.repository.AdministradorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdministradorService {
    @Autowired
    private final AdministradorRepository administradorRepository;

    public Page<Administrador> listAll(Pageable pageable){
        return administradorRepository.findAll(pageable);
    }

    public List<Administrador> listAllNonPageable() {
        return administradorRepository.findAll();
    }

    public List<Administrador> findByNome(String nome){
        return administradorRepository.findByNome(nome);
    }

    public Administrador findByIdOrThrowBadRequestException(long id){

        return administradorRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Gestor não encontrado"));
    }

    @Transactional
    public Administrador save(AdministradorDTO administradorDTO) {
        return administradorRepository.save(AdministradorMapper.INSTANCE.toAdministrador(administradorDTO));
    }

    @Transactional
    public void delete(long id) {
        administradorRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    @Transactional
    public void replace(AdministradorDTO administradorDTO) {
//        Gestor savedGestor = findByIdOrThrowBadRequestException(gestorDTO.id());
        Administrador administrador = AdministradorMapper.INSTANCE.toAdministrador(administradorDTO);
        administradorRepository.save(administrador);

    }

}
