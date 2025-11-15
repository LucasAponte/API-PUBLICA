package ar.utn.ba.ddsi.apipublica.services;

import ar.utn.ba.ddsi.apipublica.models.dtos.HechoCreateDTO;
import ar.utn.ba.ddsi.apipublica.models.dtos.HechoFilterDTO;
import ar.utn.ba.ddsi.apipublica.models.dtos.HechoRtaDTO;
import ar.utn.ba.ddsi.apipublica.models.entities.Hecho;

import java.util.List;

public interface IHechoService {
    List<HechoRtaDTO> buscarConFiltro(HechoFilterDTO filter);

    Hecho crearHecho(HechoCreateDTO dto);
}
