package ar.utn.ba.ddsi.apipublica.services;

import ar.utn.ba.ddsi.apipublica.models.dtos.HechoFilterDTO;
import ar.utn.ba.ddsi.apipublica.models.entities.Coleccion;
import ar.utn.ba.ddsi.apipublica.models.entities.Hecho;
import ar.utn.ba.ddsi.apipublica.models.repository.ColeccionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ColeccionServiceTest {

    @Mock
    private ColeccionRepository coleccionRepository;

    @InjectMocks
    private ColeccionService coleccionService;

        // verifica que sin modo aplica filtros y navega sin curado
    @Test
    void buscarHechosSegunDelegatesWhenModoNoCurado() {
        Long coleccionId = 42L;
        when(coleccionRepository.findById(coleccionId)).thenReturn(Optional.of(new Coleccion()));
        List<Hecho> expected = List.of(new Hecho());
        when(coleccionRepository.buscarEnColeccionSegun(
                any(), any(), any(), any(), any(), any(), any(), any(), any(), any()
        )).thenReturn(expected);

        HechoFilterDTO filter = new HechoFilterDTO(
                "   Salud",
                "2024-02-01",
                "2024-02-28",
                null,
                null,
                null,
                null,
                "  vacuna  "
        );

        List<Hecho> result = coleccionService.buscarHechosSegun(filter, null, coleccionId);
        assertEquals(expected, result);

        verify(coleccionRepository).buscarEnColeccionSegun(
                eq(coleccionId),
                eq("Salud"),
                eq(LocalDate.parse("2024-02-01")),
                eq(LocalDate.parse("2024-02-28")),
                isNull(),
                isNull(),
                isNull(),
                isNull(),
                isNull(),
                eq("vacuna")
        );
    }

        // confirma que modo curado fuerza consensuado verdadero
    @Test
    void buscarHechosSegunInterpretaModoCurado() {
        Long coleccionId = 7L;
        when(coleccionRepository.findById(coleccionId)).thenReturn(Optional.of(new Coleccion()));
        when(coleccionRepository.buscarEnColeccionSegun(
                any(), any(), any(), any(), any(), any(), any(), any(), any(), any()
        )).thenReturn(List.of());

        HechoFilterDTO filter = new HechoFilterDTO();

        coleccionService.buscarHechosSegun(filter, "CURADO", coleccionId);

        ArgumentCaptor<Boolean> curadoCaptor = ArgumentCaptor.forClass(Boolean.class);
        verify(coleccionRepository).buscarEnColeccionSegun(
                eq(coleccionId),
                isNull(),
                isNull(),
                isNull(),
                isNull(),
                isNull(),
                isNull(),
                isNull(),
                curadoCaptor.capture(),
                isNull()
        );
        assertEquals(Boolean.TRUE, curadoCaptor.getValue());
    }

        // valida que un modo invalido dispara excepcion
    @Test
    void buscarHechosSegunLanzaErrorModoInvalido() {
        Long coleccionId = 9L;
        when(coleccionRepository.findById(coleccionId)).thenReturn(Optional.of(new Coleccion()));
        HechoFilterDTO filter = new HechoFilterDTO();

        assertThrows(IllegalArgumentException.class,
                () -> coleccionService.buscarHechosSegun(filter, "DESCONOCIDO", coleccionId));
    }
}
