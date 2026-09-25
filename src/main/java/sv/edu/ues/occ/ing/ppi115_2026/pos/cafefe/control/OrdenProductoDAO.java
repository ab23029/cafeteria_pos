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
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.OrdenProducto;

@Stateless
public class OrdenProductoDAO implements Serializable {

    @PersistenceContext(unitName = "cafefePU")
    private EntityManager em;

    private static final Logger LOG = Logger.getLogger(OrdenProductoDAO.class.getName());

    public void crear(OrdenProducto ordenProducto) {
        try {
            if (ordenProducto != null) {
                em.persist(ordenProducto);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al crear OrdenProducto", e);
        }
    }

    public void modificar(OrdenProducto ordenProducto) {
        try {
            if (ordenProducto != null) {
                em.merge(ordenProducto);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al modificar OrdenProducto", e);
        }
    }

    public void eliminar(OrdenProducto ordenProducto) {
        try {
            if (ordenProducto != null) {
                OrdenProducto op = em.merge(ordenProducto);
                em.remove(op);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al eliminar OrdenProducto", e);
        }
    }

    public OrdenProducto buscarPorId(Object id) {
        try {
            if (id == null) {
                return null;
            }
            if (id instanceof String strId) {
                return em.find(OrdenProducto.class, UUID.fromString(strId));
            }
            return em.find(OrdenProducto.class, id);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al buscar OrdenProducto por ID", e);
            return null;
        }
    }

    public List<OrdenProducto> findRange(int first, int pageSize) {
        try {
            var cb = em.getCriteriaBuilder();
            var cq = cb.createQuery(OrdenProducto.class);
            var root = cq.from(OrdenProducto.class);
            cq.select(root);
            var query = em.createQuery(cq);
            query.setFirstResult(first);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al listar OrdenProducto", e);
            return Collections.emptyList();
        }
    }

    public int count() {
        try {
            var cb = em.getCriteriaBuilder();
            var cq = cb.createQuery(Long.class);
            cq.select(cb.count(cq.from(OrdenProducto.class)));
            return em.createQuery(cq).getSingleResult().intValue();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al contar OrdenProducto", e);
            return 0;
        }
    }
}