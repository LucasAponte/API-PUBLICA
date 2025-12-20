package ar.utn.ba.ddsi.apipublica.services;

import ar.utn.ba.ddsi.apipublica.models.dtos.CategoriaOutputDTO;
import ar.utn.ba.ddsi.apipublica.models.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaOutputDTO> findAll() {
        return categoriaRepository.findAll().stream()
                .map(categoria -> new CategoriaOutputDTO(categoria.getNombre()))
                .collect(java.util.stream.Collectors.toList());
    }

}
