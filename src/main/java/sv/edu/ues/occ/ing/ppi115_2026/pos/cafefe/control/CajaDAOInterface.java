package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import jakarta.ejb.Local;
import java.util.List;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Caja;

@Local
public interface CajaDAOInterface {
    void crear(Caja caja);
    void modificar(Caja caja);
    void eliminar(Caja caja);
    Caja buscarPorId(Object id);
    List<Caja> findRange(int first, int pageSize);
    int count();
}