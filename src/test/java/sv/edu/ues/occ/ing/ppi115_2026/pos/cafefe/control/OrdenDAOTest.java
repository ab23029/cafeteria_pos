package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import java.util.UUID;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Orden;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrdenDAOTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private OrdenDAO ordenDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        Orden entidad = new Orden();
        entidad.setIdOrden(UUID.randomUUID());
        ordenDAO.crear(entidad);
        verify(em, times(1)).persist(entidad);
    }


    @Test
    public void testEdit() {
        Orden entidad = new Orden();
        entidad.setIdOrden(UUID.randomUUID());
        ordenDAO.modificar(entidad);
        verify(em, times(1)).merge(entidad);
    }

}