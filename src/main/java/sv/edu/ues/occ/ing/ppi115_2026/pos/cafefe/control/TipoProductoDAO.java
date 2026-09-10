package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.TipoProducto;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.Serializable;

@Stateless
public class TipoProductoDAO extends DefaultDAO<TipoProducto>
        implements TipoProductoDAOInterface, Serializable {

    @PersistenceContext(unitName = "cafefePU")
    private EntityManager em;

    public TipoProductoDAO() {
        super(TipoProducto.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}