package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import java.util.UUID;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.EmpleadoRol;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmpleadoRolDAOTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private EmpleadoRolDAO empleadoRolDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        EmpleadoRol entidad = new EmpleadoRol();
        entidad.setIdEmpleadoRol(UUID.randomUUID());
        empleadoRolDAO.create(entidad);
        verify(em, times(1)).persist(entidad);
    }

    @Test
    public void testFind() {
        UUID id = UUID.randomUUID();
        EmpleadoRol esperada = new EmpleadoRol();
        esperada.setIdEmpleadoRol(id);
        when(em.find(EmpleadoRol.class, id)).thenReturn(esperada);

        EmpleadoRol resultado = empleadoRolDAO.find(id);
        assertNotNull(resultado);
        assertEquals(id, resultado.getIdEmpleadoRol());
    }

    @Test
    public void testEdit() {
        EmpleadoRol entidad = new EmpleadoRol();
        entidad.setIdEmpleadoRol(UUID.randomUUID());
        empleadoRolDAO.edit(entidad);
        verify(em, times(1)).merge(entidad);
    }

    @Test
    public void testRemove() {
       EmpleadoRol entidad = new EmpleadoRol();
        entidad.setIdEmpleadoRol(UUID.randomUUID());

        when(em.merge(entidad)).thenReturn(entidad);

        empleadoRolDAO.remove(entidad);

        verify(em, times(1)).merge(entidad);
        verify(em, times(1)).remove(entidad);
    }
}