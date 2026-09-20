package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.beans;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.TipoCaracteristicaDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.TipoCaracteristica;

@Named("tipoCaracteristicaBean")
@ViewScoped
public class TipoCaracteristicaBean implements Serializable {

    @Inject
    private TipoCaracteristicaDAOInterface dao;

    private TipoCaracteristica registro = new TipoCaracteristica();

    public List<TipoCaracteristica> getLista() {
        return dao.findRange(0, 100);
    }

    public void guardar() {
        if (registro != null) {
            dao.create(registro);
            registro = new TipoCaracteristica();
        }
    }

    public TipoCaracteristica getRegistro() {
        return registro;
    }

    public void setRegistro(TipoCaracteristica registro) {
        this.registro = registro;
    }
}