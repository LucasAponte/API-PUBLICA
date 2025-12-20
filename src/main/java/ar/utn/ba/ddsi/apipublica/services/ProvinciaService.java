package ar.utn.ba.ddsi.apipublica.services;

import ar.utn.ba.ddsi.apipublica.models.dtos.ProvinciaOutputDTO;
import ar.utn.ba.ddsi.apipublica.models.repository.ProvinciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinciaService {
    private final ProvinciaRepository provinciaRepository;

    public ProvinciaService(ProvinciaRepository provinciaRepository) {
        this.provinciaRepository = provinciaRepository;
    }

    public List<ProvinciaOutputDTO> findAll() {
        return provinciaRepository.findAll().stream()
                .map(provincia -> new ProvinciaOutputDTO(provincia.getNombre(), provincia.getPais()))
                .collect(java.util.stream.Collectors.toList());
    }

}
