package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import java.util.UUID;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.TipoProducto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TipoProductoDAOTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private TipoProductoDAO tipoProductoDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        TipoProducto entidad = new TipoProducto();
        entidad.setIdTipoProducto(UUID.randomUUID());
        tipoProductoDAO.create(entidad);
        verify(em, times(1)).persist(entidad);
    }

    @Test
    public void testFind() {
        UUID id = UUID.randomUUID();
        TipoProducto esperada = new TipoProducto();
        esperada.setIdTipoProducto(id);
        when(em.find(TipoProducto.class, id)).thenReturn(esperada);

        TipoProducto resultado = tipoProductoDAO.find(id);
        assertNotNull(resultado);
        assertEquals(id, resultado.getIdTipoProducto());
    }

    @Test
    public void testEdit() {
        TipoProducto entidad = new TipoProducto();
        entidad.setIdTipoProducto(UUID.randomUUID());
        tipoProductoDAO.edit(entidad);
        verify(em, times(1)).merge(entidad);
    }

    @Test
    public void testRemove() {
        TipoProducto entidad = new TipoProducto();
        entidad.setIdTipoProducto(UUID.randomUUID());

        when(em.merge(entidad)).thenReturn(entidad);

        tipoProductoDAO.remove(entidad);

        verify(em, times(1)).merge(entidad);
        verify(em, times(1)).remove(entidad);
    }
}