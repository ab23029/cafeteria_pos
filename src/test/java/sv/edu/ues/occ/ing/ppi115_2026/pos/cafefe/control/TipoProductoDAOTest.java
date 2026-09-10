package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.TipoProducto;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class TipoProductoDAOTest {

    @Mock
    private EntityManager emMock;

    @Spy
    private TipoProductoDAO dao;

    private UUID idPrueba;
    private TipoProducto entidadPrueba;

    @BeforeEach
    public void setUp() {
        Mockito.doReturn(emMock).when(dao).getEntityManager();
        idPrueba = UUID.randomUUID();
        entidadPrueba = new TipoProducto(idPrueba);
        entidadPrueba.setNombre("Bebidas Calientes");
    }

    @Test
    public void testCreateSuccess() {
        Assertions.assertDoesNotThrow(() -> dao.create(entidadPrueba));
        Mockito.verify(emMock, Mockito.times(1)).persist(entidadPrueba);
    }

    @Test
    public void testCreateNullEntityThrowsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> dao.create(null));
    }
}
