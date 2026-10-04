package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import java.util.UUID;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Rol;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RolDAOTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private RolDAO rolDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        Rol entidad = new Rol();
        entidad.setIdRol(UUID.randomUUID());
        rolDAO.create(entidad);
        verify(em, times(1)).persist(entidad);
    }

    @Test
    public void testFind() {
        UUID id = UUID.randomUUID();
        Rol esperada = new Rol();
        esperada.setIdRol(id);
        when(em.find(Rol.class, id)).thenReturn(esperada);

        Rol resultado = rolDAO.find(id);
        assertNotNull(resultado);
        assertEquals(id, resultado.getIdRol());
    }

    @Test
    public void testEdit() {
        Rol entidad = new Rol();
        entidad.setIdRol(UUID.randomUUID());
        rolDAO.edit(entidad);
        verify(em, times(1)).merge(entidad);
    }

    @Test
    public void testRemove() {
       Rol entidad = new Rol();
        entidad.setIdRol(UUID.randomUUID());

        when(em.merge(entidad)).thenReturn(entidad);

        rolDAO.remove(entidad);

        verify(em, times(1)).merge(entidad);
        verify(em, times(1)).remove(entidad);
    }
}