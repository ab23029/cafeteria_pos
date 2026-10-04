package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import java.util.UUID;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Producto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductoDAOTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private ProductoDAO productoDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        Producto entidad = new Producto();
        entidad.setIdProducto(UUID.randomUUID());
        productoDAO.create(entidad);
        verify(em, times(1)).persist(entidad);
    }

    @Test
    public void testFind() {
        UUID id = UUID.randomUUID();
        Producto esperada = new Producto();
        esperada.setIdProducto(id);
        when(em.find(Producto.class, id)).thenReturn(esperada);

        Producto resultado = productoDAO.find(id);
        assertNotNull(resultado);
        assertEquals(id, resultado.getIdProducto());
    }

    @Test
    public void testEdit() {
        Producto entidad = new Producto();
        entidad.setIdProducto(UUID.randomUUID());
        productoDAO.edit(entidad);
        verify(em, times(1)).merge(entidad);
    }

    @Test
    public void testRemove() {
       Producto entidad = new Producto();
        entidad.setIdProducto(UUID.randomUUID());

        when(em.merge(entidad)).thenReturn(entidad);

        productoDAO.remove(entidad);

        verify(em, times(1)).merge(entidad);
        verify(em, times(1)).remove(entidad);
    }
}