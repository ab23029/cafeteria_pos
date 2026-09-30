package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import java.util.List;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.TipoDescuento;

public interface TipoDescuentoDAOInterface {
    
    void create(TipoDescuento entity);
    TipoDescuento find(Object id);
    List<TipoDescuento> findRange(int first, int max);
    List<TipoDescuento> findActivos();
}