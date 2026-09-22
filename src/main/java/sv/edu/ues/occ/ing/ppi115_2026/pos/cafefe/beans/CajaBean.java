package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.beans;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.CajaDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Caja;

@Named("cajaBean")
@ViewScoped
public class CajaBean implements Serializable {

    @Inject
    private CajaDAOInterface dao;

    private Caja registro = new Caja();

    public List<Caja> getLista() {
        return dao.findRange(0, 100);
    }

    public void guardar() {
        if (registro != null) {
            dao.create(registro);
            registro = new Caja();
        }
    }

    public Caja getRegistro() {
        return registro;
    }

    public void setRegistro(Caja registro) {
        this.registro = registro;
    }
}