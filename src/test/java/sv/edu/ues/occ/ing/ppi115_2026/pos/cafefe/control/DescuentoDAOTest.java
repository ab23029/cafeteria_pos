package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import java.util.UUID;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Descuento;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DescuentoDAOTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private DescuentoDAO descuentoDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        Descuento entidad = new Descuento();
        entidad.setIdDescuento(UUID.randomUUID());
        descuentoDAO.create(entidad);
        verify(em, times(1)).persist(entidad);
    }

    @Test
    public void testFind() {
        UUID id = UUID.randomUUID();
        Descuento esperada = new Descuento();
        esperada.setIdDescuento(id);
        when(em.find(Descuento.class, id)).thenReturn(esperada);

        Descuento resultado = descuentoDAO.find(id);
        assertNotNull(resultado);
        assertEquals(id, resultado.getIdDescuento());
    }

    @Test
    public void testEdit() {
        Descuento entidad = new Descuento();
        entidad.setIdDescuento(UUID.randomUUID());
        descuentoDAO.edit(entidad);
        verify(em, times(1)).merge(entidad);
    }

    @Test
    public void testRemove() {
        Descuento entidad = new Descuento();
        entidad.setIdDescuento(UUID.randomUUID());

        when(em.merge(entidad)).thenReturn(entidad);

        descuentoDAO.remove(entidad);

        verify(em, times(1)).merge(entidad);
        verify(em, times(1)).remove(entidad);
    }
}