package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.TipoDescuento;

@Stateless
public class TipoDescuentoDAO extends DefaultDAO<TipoDescuento> implements TipoDescuentoDAOInterface {

    @PersistenceContext(unitName = "cafefePU")
    private EntityManager em;

    public TipoDescuentoDAO() {
        super(TipoDescuento.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<TipoDescuento> findActivos() {
        try {
            return em.createNamedQuery("TipoDescuento.findActivos", TipoDescuento.class).getResultList();
        } catch (Exception e) {
            return List.of();
        }
    }
}