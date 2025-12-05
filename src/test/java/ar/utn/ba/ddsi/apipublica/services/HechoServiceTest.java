package ar.utn.ba.ddsi.apipublica.services;

import ar.utn.ba.ddsi.apipublica.models.dtos.HechoFilterDTO;
import ar.utn.ba.ddsi.apipublica.models.dtos.HechoOutputDTO;
import ar.utn.ba.ddsi.apipublica.models.entities.Hecho;
import ar.utn.ba.ddsi.apipublica.models.repository.AdjuntoRepository;
import ar.utn.ba.ddsi.apipublica.models.repository.CategoriaRepository;
import ar.utn.ba.ddsi.apipublica.models.repository.FuenteRepository;
import ar.utn.ba.ddsi.apipublica.models.repository.HechoRepository;
import ar.utn.ba.ddsi.apipublica.models.repository.UbicacionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HechoServiceTest {

    @Mock
    private HechoRepository hechoRepository;
    @Mock
    private CategoriaRepository categoriaRepository;
    @Mock
    private FuenteRepository fuenteRepository;
    @Mock
    private UbicacionRepository ubicacionRepository;
    @Mock
    private AdjuntoRepository adjuntoRepository;

    @InjectMocks
    private HechoService hechoService;

    // verifica delegacion de filtros normalizados al repositorio
    @Test
    void buscarConFiltroDelegatesParsedValuesToRepository() {
        HechoFilterDTO filter = new HechoFilterDTO(
                "  Seguridad  ",
                "2024-01-01",
                "2024-01-31",
                "2023-12-01",
                "2023-12-31",
                "-34.55",
                "-58.49",
                "  fraude  "
        );

        // Mock: el repositorio devuelve una lista de Hecho; el servicio la convertirá a DTOs
        List<Hecho> expected = List.of(new Hecho());
        when(hechoRepository.buscarHechosSegun(
                any(), any(), any(), any(), any(), any(), any(), any(), any()
        )).thenReturn(expected);

        List<HechoOutputDTO> result = hechoService.buscarConFiltro(filter);

        // ahora el servicio devuelve DTOs, así que comprobamos tamaño y tipo
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof HechoOutputDTO);

        // Verificar que el repositorio fue llamado con los valores parseados correctos (9 argumentos)
        verify(hechoRepository).buscarHechosSegun(
                eq("Seguridad"),
                eq(LocalDate.parse("2024-01-01")),
                eq(LocalDate.parse("2024-01-31")),
                eq(LocalDate.parse("2023-12-01")),
                eq(LocalDate.parse("2023-12-31")),
                eq(-34.55f),
                eq(-58.49f),
                eq(0.01f),       // delta por defecto definido en el servicio
                eq("fraude")
        );
    }

    // confirma que sin filtros consulta todos los hechos
    @Test
    void buscarConFiltroSinParametrosRecuperaTodo() {
        when(hechoRepository.findAll()).thenReturn(Collections.emptyList());

        List<HechoOutputDTO> result = hechoService.buscarConFiltro(null);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(hechoRepository, times(1)).findAll();
    }

    // asegura que fechas invalidas lanzan error antes de ir al repositorio
    @Test
    void buscarConFiltroLanzaExcepcionPorFechaInvalida() {
        HechoFilterDTO filter = new HechoFilterDTO(
                null,
                "2024-13-01", // mes inválido
                null,
                null,
                null,
                null,
                null,
                null
        );

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> hechoService.buscarConFiltro(filter));
        assertTrue(ex.getMessage().toLowerCase().contains("formato") || ex.getMessage().toLowerCase().contains("fecha"));

        // Asegurarnos que no se llamó al repo con 9 argumentos
        verify(hechoRepository, times(0)).buscarHechosSegun(
                any(), any(), any(), any(), any(), any(), any(), any(), any()
        );
    }
}
