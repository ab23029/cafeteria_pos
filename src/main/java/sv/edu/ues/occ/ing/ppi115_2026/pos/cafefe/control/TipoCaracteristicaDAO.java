package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import java.io.Serializable;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.TipoCaracteristica;

@Stateless
@LocalBean
public class TipoCaracteristicaDAO extends DefaultDAO<TipoCaracteristica> implements Serializable {

    @PersistenceContext(unitName = "cafefePU")
    protected EntityManager em;

    public TipoCaracteristicaDAO() {
        super(TipoCaracteristica.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}