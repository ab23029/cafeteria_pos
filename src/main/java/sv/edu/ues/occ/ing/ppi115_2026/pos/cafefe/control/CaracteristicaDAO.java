package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Caracteristica;

@Stateless
public class CaracteristicaDAO {

    @PersistenceContext(unitName = "cafefePU")
    private EntityManager em;

    public void crear(Caracteristica entity) {
        em.persist(entity);
    }

    public void modificar(Caracteristica entity) {
        em.merge(entity);
    }

    public List<Caracteristica> obtenerTodos() {
        return em.createQuery("SELECT c FROM Caracteristica c", Caracteristica.class).getResultList();
    }
}
