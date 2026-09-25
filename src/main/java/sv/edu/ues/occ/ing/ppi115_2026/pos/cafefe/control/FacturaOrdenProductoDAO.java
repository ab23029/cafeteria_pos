package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.FacturaOrdenProducto;

@Stateless
public class FacturaOrdenProductoDAO implements Serializable {

    @PersistenceContext(unitName = "cafefePU")
    private EntityManager em;

    private static final Logger LOG = Logger.getLogger(FacturaOrdenProductoDAO.class.getName());

    public void crear(FacturaOrdenProducto facturaOrdenProducto) {
        try {
            if (facturaOrdenProducto != null) {
                em.persist(facturaOrdenProducto);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al crear FacturaOrdenProducto", e);
        }
    }

    public void modificar(FacturaOrdenProducto facturaOrdenProducto) {
        try {
            if (facturaOrdenProducto != null) {
                em.merge(facturaOrdenProducto);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al modificar FacturaOrdenProducto", e);
        }
    }

    public void eliminar(FacturaOrdenProducto facturaOrdenProducto) {
        try {
            if (facturaOrdenProducto != null) {
                FacturaOrdenProducto fop = em.merge(facturaOrdenProducto);
                em.remove(fop);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al eliminar FacturaOrdenProducto", e);
        }
    }

    public FacturaOrdenProducto buscarPorId(Object id) {
        try {
            if (id == null) {
                return null;
            }
            if (id instanceof String strId) {
                return em.find(FacturaOrdenProducto.class, UUID.fromString(strId));
            }
            return em.find(FacturaOrdenProducto.class, id);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al buscar FacturaOrdenProducto por ID", e);
            return null;
        }
    }

    public List<FacturaOrdenProducto> findRange(int first, int pageSize) {
        try {
            var cb = em.getCriteriaBuilder();
            var cq = cb.createQuery(FacturaOrdenProducto.class);
            var root = cq.from(FacturaOrdenProducto.class);
            cq.select(root);
            var query = em.createQuery(cq);
            query.setFirstResult(first);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al listar FacturaOrdenProducto", e);
            return Collections.emptyList();
        }
    }

    public int count() {
        try {
            var cb = em.getCriteriaBuilder();
            var cq = cb.createQuery(Long.class);
            cq.select(cb.count(cq.from(FacturaOrdenProducto.class)));
            return em.createQuery(cq).getSingleResult().intValue();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al contar FacturaOrdenProducto", e);
            return 0;
        }
    }
}