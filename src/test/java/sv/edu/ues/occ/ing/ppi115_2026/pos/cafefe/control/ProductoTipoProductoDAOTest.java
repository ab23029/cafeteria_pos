package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import java.util.UUID;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.ProductoTipoProducto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductoTipoProductoDAOTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private ProductoTipoProductoDAO productoTipoProductoDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        ProductoTipoProducto entidad = new ProductoTipoProducto();
        entidad.setIdProductoTipoProducto(UUID.randomUUID());
        productoTipoProductoDAO.create(entidad);
        verify(em, times(1)).persist(entidad);
    }

    @Test
    public void testFind() {
        UUID id = UUID.randomUUID();
        ProductoTipoProducto esperada = new ProductoTipoProducto();
        esperada.setIdProductoTipoProducto(id);
        when(em.find(ProductoTipoProducto.class, id)).thenReturn(esperada);

        ProductoTipoProducto resultado = productoTipoProductoDAO.find(id);
        assertNotNull(resultado);
        assertEquals(id, resultado.getIdProductoTipoProducto());
    }

    @Test
    public void testEdit() {
        ProductoTipoProducto entidad = new ProductoTipoProducto();
        entidad.setIdProductoTipoProducto(UUID.randomUUID());
        productoTipoProductoDAO.edit(entidad);
        verify(em, times(1)).merge(entidad);
    }

    @Test
    public void testRemove() {
        ProductoTipoProducto entidad = new ProductoTipoProducto();
        entidad.setIdProductoTipoProducto(UUID.randomUUID());

        when(em.merge(entidad)).thenReturn(entidad);

        productoTipoProductoDAO.remove(entidad);

        verify(em, times(1)).merge(entidad);
        verify(em, times(1)).remove(entidad);
    }
}