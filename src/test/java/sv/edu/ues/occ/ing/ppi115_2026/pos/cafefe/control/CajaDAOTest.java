package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import java.util.UUID;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Caja;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CajaDAOTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private CajaDAO cajaDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        Caja entidad = new Caja();
        entidad.setIdCaja(UUID.randomUUID());
        cajaDAO.create(entidad);
        verify(em, times(1)).persist(entidad);
    }

    @Test
    public void testFind() {
        UUID id = UUID.randomUUID();
        Caja esperada = new Caja();
        esperada.setIdCaja(id);
        when(em.find(Caja.class, id)).thenReturn(esperada);

        Caja resultado = cajaDAO.find(id);
        assertNotNull(resultado);
        assertEquals(id, resultado.getIdCaja());
    }

    @Test
    public void testEdit() {
        Caja entidad = new Caja();
        entidad.setIdCaja(UUID.randomUUID());
        cajaDAO.edit(entidad);
        verify(em, times(1)).merge(entidad);
    }

    @Test
    public void testRemove() {
        Caja entidad = new Caja();
        entidad.setIdCaja(UUID.randomUUID());

        when(em.merge(entidad)).thenReturn(entidad);

        cajaDAO.remove(entidad);

        verify(em, times(1)).merge(entidad);
        verify(em, times(1)).remove(entidad);
    }
}