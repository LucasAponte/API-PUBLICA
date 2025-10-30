package ar.utn.ba.ddsi.apipublica.models.entities;

import java.util.List;

public interface AlgoritmoDeConsenso {
    List<Hecho> aplicar(List<Hecho>hechos,List<Fuente> fuentes);

}