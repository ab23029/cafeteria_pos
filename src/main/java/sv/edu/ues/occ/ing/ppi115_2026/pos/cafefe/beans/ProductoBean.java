package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.beans;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.ProductoDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Producto;

@Named("productoBean")
@ViewScoped
public class ProductoBean implements Serializable {

    @Inject
    private ProductoDAOInterface dao;

    private Producto registro = new Producto();

    public List<Producto> getLista() {
        return dao.findRange(0, 100);
    }

    public void guardar() {
        if (registro != null) {
            dao.create(registro);
            registro = new Producto();
        }
    }

    public Producto getRegistro() {
        return registro;
    }

    public void setRegistro(Producto registro) {
        this.registro = registro;
    }
}