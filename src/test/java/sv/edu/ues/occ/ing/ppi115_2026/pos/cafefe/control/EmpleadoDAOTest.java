package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import java.util.UUID;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
// IMPORTA LA ENTIDAD Y EL DAO DE EMPLEADO
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Empleado;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmpleadoDAOTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private EmpleadoDAO empleadoDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        Empleado entidad = new Empleado();
        entidad.setIdEmpleado(UUID.randomUUID());
        empleadoDAO.create(entidad);
        verify(em, times(1)).persist(entidad);
    }

    @Test
    public void testFind() {
        UUID id = UUID.randomUUID();
        Empleado esperada = new Empleado();
        esperada.setIdEmpleado(id);
        when(em.find(Empleado.class, id)).thenReturn(esperada);

        Empleado resultado = empleadoDAO.find(id);
        assertNotNull(resultado);
        assertEquals(id, resultado.getIdEmpleado());
    }

    @Test
    public void testEdit() {
        Empleado entidad = new Empleado();
        entidad.setIdEmpleado(UUID.randomUUID());
        empleadoDAO.edit(entidad);
        verify(em, times(1)).merge(entidad);
    }

    @Test
    public void testRemove() {
       Empleado entidad = new Empleado();
        entidad.setIdEmpleado(UUID.randomUUID());

        when(em.merge(entidad)).thenReturn(entidad);

        empleadoDAO.remove(entidad);

        verify(em, times(1)).merge(entidad);
        verify(em, times(1)).remove(entidad);
    }
}