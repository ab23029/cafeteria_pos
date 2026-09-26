package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Empleado;

@Stateless
public class EmpleadoDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "cafefePU")
    private EntityManager em;

    public void create(Empleado entity) {
        em.persist(entity);
    }

    public void edit(Empleado entity) {
        em.merge(entity);
    }

    public void remove(Empleado entity) {
        em.remove(em.merge(entity));
    }

    public Empleado find(Object id) {
        return em.find(Empleado.class, id);
    }

    public List<Empleado> findAll() {
        return em.createQuery("SELECT e FROM Empleado e", Empleado.class).getResultList();
    }

    public List<Empleado> findRange(int first, int max) {
        return em.createQuery("SELECT e FROM Empleado e", Empleado.class)
                 .setFirstResult(first)
                 .setMaxResults(max)
                 .getResultList();
    }

    public int count() {
        return ((Long) em.createQuery("SELECT COUNT(e) FROM Empleado e").getSingleResult()).intValue();
    }
}