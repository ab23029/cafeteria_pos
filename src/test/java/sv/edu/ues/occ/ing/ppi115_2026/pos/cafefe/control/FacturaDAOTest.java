package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import java.util.UUID;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Factura;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FacturaDAOTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private FacturaDAO facturaDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        Factura entidad = new Factura();
        entidad.setIdFactura(UUID.randomUUID());
        facturaDAO.create(entidad);
        verify(em, times(1)).persist(entidad);
    }

    @Test
    public void testFind() {
        UUID id = UUID.randomUUID();
        Factura esperada = new Factura();
        esperada.setIdFactura(id);
        when(em.find(Factura.class, id)).thenReturn(esperada);

        Factura resultado = facturaDAO.find(id);
        assertNotNull(resultado);
        assertEquals(id, resultado.getIdFactura());
    }

    @Test
    public void testEdit() {
        Factura entidad = new Factura();
        entidad.setIdFactura(UUID.randomUUID());
        facturaDAO.edit(entidad);
        verify(em, times(1)).merge(entidad);
    }

    @Test
    public void testRemove() {
       Factura entidad = new Factura();
        entidad.setIdFactura(UUID.randomUUID());

        when(em.merge(entidad)).thenReturn(entidad);

        facturaDAO.remove(entidad);

        verify(em, times(1)).merge(entidad);
        verify(em, times(1)).remove(entidad);
    }
}