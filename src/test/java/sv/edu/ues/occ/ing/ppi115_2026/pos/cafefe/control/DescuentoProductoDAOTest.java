package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import java.util.UUID;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.DescuentoProducto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DescuentoProductoDAOTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private DescuentoProductoDAO descuentoProductoDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        DescuentoProducto entidad = new DescuentoProducto();
        entidad.setIdDescuentoProducto(UUID.randomUUID());
        descuentoProductoDAO.create(entidad);
        verify(em, times(1)).persist(entidad);
    }

    @Test
    public void testFind() {
        UUID id = UUID.randomUUID();
        DescuentoProducto esperada = new DescuentoProducto();
        esperada.setIdDescuentoProducto(id);
        when(em.find(DescuentoProducto.class, id)).thenReturn(esperada);

        DescuentoProducto resultado = descuentoProductoDAO.find(id);
        assertNotNull(resultado);
        assertEquals(id, resultado.getIdDescuentoProducto());
    }

    @Test
    public void testEdit() {
        DescuentoProducto entidad = new DescuentoProducto();
        entidad.setIdDescuentoProducto(UUID.randomUUID());
        descuentoProductoDAO.edit(entidad);
        verify(em, times(1)).merge(entidad);
    }

    @Test
    public void testRemove() {
        DescuentoProducto entidad = new DescuentoProducto();
        entidad.setIdDescuentoProducto(UUID.randomUUID());

        when(em.merge(entidad)).thenReturn(entidad);

        descuentoProductoDAO.remove(entidad);

        verify(em, times(1)).merge(entidad);
        verify(em, times(1)).remove(entidad);
    }
}