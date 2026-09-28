package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Orden;

@Stateless
public class OrdenDAO {

    @PersistenceContext(unitName = "cafefePU")
    private EntityManager em;

    public void crear(Orden entity) {
        em.persist(entity);
    }

    public void modificar(Orden entity) {
        em.merge(entity);
    }

    public List<Orden> obtenerTodos() {
        return em.createQuery("SELECT o FROM Orden o", Orden.class).getResultList();
    }
}
