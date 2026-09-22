package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.PagoDetalle;

@Stateless
public class PagoDetalleDAO {

    @PersistenceContext(unitName = "cafefePU")
    private EntityManager em;

    public void crear(PagoDetalle entity) {
        em.persist(entity);
    }

    public void modificar(PagoDetalle entity) {
        em.merge(entity);
    }

    public List<PagoDetalle> obtenerTodos() {
        return em.createQuery("SELECT pd FROM PagoDetalle pd", PagoDetalle.class).getResultList();
    }
}
