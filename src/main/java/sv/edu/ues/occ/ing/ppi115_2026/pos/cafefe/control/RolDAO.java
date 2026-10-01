package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Rol;

@Stateless
public class RolDAO extends DefaultDAO<Rol> implements RolDAOInterface, Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "cafefePU")
    private EntityManager em;

    public RolDAO() {
        super(Rol.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<Rol> findActivos() {
        try {
            return em.createNamedQuery("Rol.findByActivo", Rol.class)
                    .setParameter("activo", true)
                    .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
