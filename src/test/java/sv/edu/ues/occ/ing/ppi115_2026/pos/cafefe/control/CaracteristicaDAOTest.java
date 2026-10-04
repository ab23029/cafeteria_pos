package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import java.util.UUID;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Caracteristica;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CaracteristicaDAOTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private CaracteristicaDAO caracteristicaDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        Caracteristica entidad = new Caracteristica();
        entidad.setIdCaracteristica(UUID.randomUUID());
        caracteristicaDAO.create(entidad);
        verify(em, times(1)).persist(entidad);
    }

    @Test
    public void testFind() {
        UUID id = UUID.randomUUID();
        Caracteristica esperada = new Caracteristica();
        esperada.setIdCaracteristica(id);
        when(em.find(Caracteristica.class, id)).thenReturn(esperada);

        Caracteristica resultado = caracteristicaDAO.find(id);
        assertNotNull(resultado);
        assertEquals(id, resultado.getIdCaracteristica());
    }

    @Test
    public void testEdit() {
        Caracteristica entidad = new Caracteristica();
        entidad.setIdCaracteristica(UUID.randomUUID());
        caracteristicaDAO.edit(entidad);
        verify(em, times(1)).merge(entidad);
    }

    @Test
    public void testRemove() {
        Caracteristica entidad = new Caracteristica();
        entidad.setIdCaracteristica(UUID.randomUUID());

        when(em.merge(entidad)).thenReturn(entidad);

        caracteristicaDAO.remove(entidad);

        verify(em, times(1)).merge(entidad);
        verify(em, times(1)).remove(entidad);
    }
}