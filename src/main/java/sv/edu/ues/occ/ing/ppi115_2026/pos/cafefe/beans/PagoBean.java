package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.beans;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.PagoDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Pago;

@Named("pagoBean")
@ViewScoped
public class PagoBean implements Serializable {

    @Inject
    private PagoDAOInterface dao;

    private Pago registro = new Pago();

    public List<Pago> getLista() {
        return dao.findRange(0, 100);
    }

    public void guardar() {
        if (registro != null) {
            dao.create(registro);
            registro = new Pago();
        }
    }

    public Pago getRegistro() {
        return registro;
    }

    public void setRegistro(Pago registro) {
        this.registro = registro;
    }
}