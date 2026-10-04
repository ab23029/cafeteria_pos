package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import java.util.UUID;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.ProductoCaracteristica;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductoCaracteristicaDAOTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private ProductoCaracteristicaDAO productoCaracteristicaDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        ProductoCaracteristica entidad = new ProductoCaracteristica();
        entidad.setIdProductoCaracteristica(UUID.randomUUID());
        productoCaracteristicaDAO.create(entidad);
        verify(em, times(1)).persist(entidad);
    }

    @Test
    public void testFind() {
        UUID id = UUID.randomUUID();
        ProductoCaracteristica esperada = new ProductoCaracteristica();
        esperada.setIdProductoCaracteristica(id);
        when(em.find(ProductoCaracteristica.class, id)).thenReturn(esperada);

        ProductoCaracteristica resultado = productoCaracteristicaDAO.find(id);
        assertNotNull(resultado);
        assertEquals(id, resultado.getIdProductoCaracteristica());
    }

    @Test
    public void testEdit() {
        ProductoCaracteristica entidad = new ProductoCaracteristica();
        entidad.setIdProductoCaracteristica(UUID.randomUUID());
        productoCaracteristicaDAO.edit(entidad);
        verify(em, times(1)).merge(entidad);
    }

    @Test
    public void testRemove() {
       ProductoCaracteristica entidad = new ProductoCaracteristica();
        entidad.setIdProductoCaracteristica(UUID.randomUUID());

        when(em.merge(entidad)).thenReturn(entidad);

        productoCaracteristicaDAO.remove(entidad);

        verify(em, times(1)).merge(entidad);
        verify(em, times(1)).remove(entidad);
    }
}