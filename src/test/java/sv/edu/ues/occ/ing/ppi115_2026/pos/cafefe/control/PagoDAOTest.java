package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import java.util.UUID;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Pago;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PagoDAOTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private PagoDAO pagoDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        Pago entidad = new Pago();
        entidad.setIdPago(UUID.randomUUID());
        pagoDAO.create(entidad);
        verify(em, times(1)).persist(entidad);
    }

    @Test
    public void testFind() {
        UUID id = UUID.randomUUID();
        Pago esperada = new Pago();
        esperada.setIdPago(id);
        when(em.find(Pago.class, id)).thenReturn(esperada);

        Pago resultado = pagoDAO.find(id);
        assertNotNull(resultado);
        assertEquals(id, resultado.getIdPago());
    }

    @Test
    public void testEdit() {
        Pago entidad = new Pago();
        entidad.setIdPago(UUID.randomUUID());
        pagoDAO.edit(entidad);
        verify(em, times(1)).merge(entidad);
    }

    @Test
    public void testRemove() {
        Pago entidad = new Pago();
        entidad.setIdPago(UUID.randomUUID());

        when(em.merge(entidad)).thenReturn(entidad);

        pagoDAO.remove(entidad);

        verify(em, times(1)).merge(entidad);
        verify(em, times(1)).remove(entidad);;
    }
}