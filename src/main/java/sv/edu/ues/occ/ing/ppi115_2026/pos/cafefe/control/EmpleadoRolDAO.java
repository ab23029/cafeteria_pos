package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.EmpleadoRol;

@Stateless
public class EmpleadoRolDAO {

    @PersistenceContext(unitName = "cafefePU")
    private EntityManager em;

    public void create(EmpleadoRol entity) {
        em.persist(entity);
    }

    public void delete(EmpleadoRol entity) {
        em.remove(em.merge(entity));
    }

    public List<EmpleadoRol> findByEmpleado(Object idEmpleado) {
        if (idEmpleado == null) return Collections.emptyList();
        return em.createQuery("SELECT er FROM EmpleadoRol er WHERE er.idEmpleado.idEmpleado = :idEmpleado", EmpleadoRol.class)
                 .setParameter("idEmpleado", idEmpleado)
                 .getResultList();
    }
}