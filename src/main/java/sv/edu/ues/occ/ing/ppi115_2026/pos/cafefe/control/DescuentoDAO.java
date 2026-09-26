package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Descuento;

@Stateless
public class DescuentoDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "cafefePU")
    private EntityManager em;

    public void create(Descuento entity) {
        em.persist(entity);
    }

    public void edit(Descuento entity) {
        em.merge(entity);
    }

    public void remove(Descuento entity) {
        em.remove(em.merge(entity));
    }

    public Descuento find(Object id) {
        return em.find(Descuento.class, id);
    }

    public List<Descuento> findAll() {
        return em.createQuery("SELECT d FROM Descuento d JOIN FETCH d.idTipoDescuento", Descuento.class).getResultList();
    }

    public List<Descuento> findRange(int first, int max) {
        return em.createQuery("SELECT d FROM Descuento d JOIN FETCH d.idTipoDescuento", Descuento.class)
                 .setFirstResult(first)
                 .setMaxResults(max)
                 .getResultList();
    }

    public int count() {
        return ((Long) em.createQuery("SELECT COUNT(d) FROM Descuento d").getSingleResult()).intValue();
    }
}