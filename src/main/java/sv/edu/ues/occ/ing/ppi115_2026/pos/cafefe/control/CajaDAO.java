package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.Serializable;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Caja;

@Stateless
public class CajaDAO extends DefaultDAO<Caja> implements CajaDAOInterface, Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "cafefePU")
    private EntityManager em;

    public CajaDAO() {
        super(Caja.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void crear(Caja reg) {
        super.create(reg);
    }

    @Override
    public void modificar(Caja reg) {
        super.edit(reg);
    }

    @Override
    public void eliminar(Caja reg) {
        super.remove(reg);
    }

    @Override
    public Caja buscarPorId(Object id) {
        return super.find(id);
    }
}