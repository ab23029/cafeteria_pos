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
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Factura;

@Stateless
public class FacturaDAO implements Serializable {

    @PersistenceContext(unitName = "cafefePU")
    private EntityManager em;

    private static final Logger LOG = Logger.getLogger(FacturaDAO.class.getName());

    public void crear(Factura factura) {
        try {
            if (factura != null) {
                em.persist(factura);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al crear factura", e);
        }
    }

    public void modificar(Factura factura) {
        try {
            if (factura != null) {
                em.merge(factura);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al modificar factura", e);
        }
    }

    public void eliminar(Factura factura) {
        try {
            if (factura != null) {
                Factura f = em.merge(factura);
                em.remove(f);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al eliminar factura", e);
        }
    }

    public Factura buscarPorId(Object id) {
        try {
            if (id == null) {
                return null;
            }
            if (id instanceof String strId) {
                return em.find(Factura.class, UUID.fromString(strId));
            }
            return em.find(Factura.class, id);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al buscar factura por ID", e);
            return null;
        }
    }

    public List<Factura> findRange(int first, int pageSize) {
        try {
            var cb = em.getCriteriaBuilder();
            var cq = cb.createQuery(Factura.class);
            var root = cq.from(Factura.class);
            cq.select(root);
            var query = em.createQuery(cq);
            query.setFirstResult(first);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al listar facturas", e);
            return Collections.emptyList();
        }
    }

    public int count() {
        try {
            var cb = em.getCriteriaBuilder();
            var cq = cb.createQuery(Long.class);
            cq.select(cb.count(cq.from(Factura.class)));
            return em.createQuery(cq).getSingleResult().intValue();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al contar facturas", e);
            return 0;
        }
    }
}