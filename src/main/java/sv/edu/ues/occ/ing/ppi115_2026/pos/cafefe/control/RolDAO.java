package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Rol;

@Stateless
public class RolDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "cafefePU")
    private EntityManager em;

    public void create(Rol entity) {
        em.persist(entity);
    }

    public void edit(Rol entity) {
        em.merge(entity);
    }

    public void remove(Rol entity) {
        em.remove(em.merge(entity));
    }

    public Rol find(Object id) {
        return em.find(Rol.class, id);
    }

    public List<Rol> findAll() {
        return em.createQuery("SELECT r FROM Rol r", Rol.class).getResultList();
    }

    public List<Rol> findRange(int first, int max) {
        return em.createQuery("SELECT r FROM Rol r", Rol.class)
                 .setFirstResult(first)
                 .setMaxResults(max)
                 .getResultList();
    }

    public int count() {
        return ((Long) em.createQuery("SELECT COUNT(r) FROM Rol r").getSingleResult()).intValue();
    }
}