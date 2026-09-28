package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control;

import jakarta.ejb.Local;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Factura;

@Local
public interface FacturaDAOInterface extends DaoInterface<Factura> {
    // Hereda create, edit, remove, find, findRange y count desde DaoInterface
}