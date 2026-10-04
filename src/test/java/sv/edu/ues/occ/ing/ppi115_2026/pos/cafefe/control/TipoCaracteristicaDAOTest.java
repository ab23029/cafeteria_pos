package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import java.util.UUID;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.TipoCaracteristica;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TipoCaracteristicaDAOTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private TipoCaracteristicaDAO tipoCaracteristicaDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        TipoCaracteristica entidad = new TipoCaracteristica();
        entidad.setIdTipoCaracteristica(UUID.randomUUID());
        tipoCaracteristicaDAO.create(entidad);
        verify(em, times(1)).persist(entidad);
    }

    @Test
    public void testFind() {
        UUID id = UUID.randomUUID();
        TipoCaracteristica esperada = new TipoCaracteristica();
        esperada.setIdTipoCaracteristica(id);
        when(em.find(TipoCaracteristica.class, id)).thenReturn(esperada);

        TipoCaracteristica resultado = tipoCaracteristicaDAO.find(id);
        assertNotNull(resultado);
        assertEquals(id, resultado.getIdTipoCaracteristica());
    }

    @Test
    public void testEdit() {
        TipoCaracteristica entidad = new TipoCaracteristica();
        entidad.setIdTipoCaracteristica(UUID.randomUUID());
        tipoCaracteristicaDAO.edit(entidad);
        verify(em, times(1)).merge(entidad);
    }

    @Test
    public void testRemove() {
        TipoCaracteristica entidad = new TipoCaracteristica();
        entidad.setIdTipoCaracteristica(UUID.randomUUID());

        when(em.merge(entidad)).thenReturn(entidad);

        tipoCaracteristicaDAO.remove(entidad);

        verify(em, times(1)).merge(entidad);
        verify(em, times(1)).remove(entidad);
    }
}