package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.TipoDescuento;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class TipoDescuentoDAOTest {

    @Mock
    private EntityManager emMock;

    @Spy
    private TipoDescuentoDAO dao;

    private UUID idPrueba;
    private TipoDescuento entidadPrueba;

    @BeforeEach
    public void setUp() {
        Mockito.doReturn(emMock).when(dao).getEntityManager();

        idPrueba = UUID.randomUUID();
        entidadPrueba = new TipoDescuento(idPrueba);
        entidadPrueba.setNombre("Descuento Estudiantil");
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

    @Test
    public void testEditSuccess() {
        Mockito.when(emMock.merge(entidadPrueba)).thenReturn(entidadPrueba);
        TipoDescuento resultado = dao.edit(entidadPrueba);
        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(entidadPrueba.getNombre(), resultado.getNombre());
    }

    @Test
    public void testEditNullThrowsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> dao.edit(null));
    }

    @Test
    public void testRemoveSuccess() {
        Mockito.when(emMock.merge(entidadPrueba)).thenReturn(entidadPrueba);
        Assertions.assertDoesNotThrow(() -> dao.remove(entidadPrueba));
        Mockito.verify(emMock, Mockito.times(1)).remove(entidadPrueba);
    }

    @Test
    public void testRemoveNullThrowsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> dao.remove(null));
    }

    @Test
    public void testFindSuccess() {
        Mockito.when(emMock.find(TipoDescuento.class, idPrueba)).thenReturn(entidadPrueba);
        TipoDescuento resultado = dao.find(idPrueba);
        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(idPrueba, resultado.getIdTipoDescuento());
    }

    @Test
    public void testFindNullIdThrowsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> dao.find(null));
    }

    @Test

    @SuppressWarnings("unchecked")
    public void testFindRangeSuccess() {
        CriteriaBuilder cbMock = Mockito.mock(CriteriaBuilder.class);
        CriteriaQuery<TipoDescuento> cqMock = Mockito.mock(CriteriaQuery.class);
        Root<TipoDescuento> rootMock = Mockito.mock(Root.class);
        TypedQuery<TipoDescuento> queryMock = Mockito.mock(TypedQuery.class);

        Mockito.when(emMock.getCriteriaBuilder()).thenReturn(cbMock);
        Mockito.when(cbMock.createQuery(TipoDescuento.class)).thenReturn(cqMock);
        Mockito.when(cqMock.from(TipoDescuento.class)).thenReturn(rootMock);
        Mockito.when(cqMock.select(rootMock)).thenReturn(cqMock);
        Mockito.when(emMock.createQuery(cqMock)).thenReturn(queryMock);
        Mockito.when(queryMock.getResultList()).thenReturn(List.of(entidadPrueba));

        List<TipoDescuento> resultado = dao.findRange(0, 10);
        Assertions.assertNotNull(resultado);
        Assertions.assertFalse(resultado.isEmpty());
        Mockito.verify(queryMock).setFirstResult(0);
        Mockito.verify(queryMock).setMaxResults(10);
    }

    @Test
    public void testFindRangeInvalidParamsThrowsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> dao.findRange(-1, 10));
        Assertions.assertThrows(IllegalArgumentException.class, () -> dao.findRange(0, 0));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCountSuccess() {
        CriteriaBuilder cbMock = Mockito.mock(CriteriaBuilder.class);
        CriteriaQuery<Long> cqLongMock = Mockito.mock(CriteriaQuery.class);
        Root<TipoDescuento> rootMock = Mockito.mock(Root.class);
        Expression<Long> countExprMock = Mockito.mock(Expression.class);
        TypedQuery<Long> countQueryMock = Mockito.mock(TypedQuery.class);

        Mockito.when(emMock.getCriteriaBuilder()).thenReturn(cbMock);
        Mockito.when(cbMock.createQuery(Long.class)).thenReturn(cqLongMock);
        Mockito.when(cqLongMock.from(TipoDescuento.class)).thenReturn(rootMock);
        Mockito.when(cbMock.count(rootMock)).thenReturn(countExprMock);
        Mockito.when(cqLongMock.select(countExprMock)).thenReturn(cqLongMock);
        Mockito.when(emMock.createQuery(cqLongMock)).thenReturn(countQueryMock);
        Mockito.when(countQueryMock.getSingleResult()).thenReturn(5L);

        int total = dao.count();
        Assertions.assertEquals(5, total);
    }
}