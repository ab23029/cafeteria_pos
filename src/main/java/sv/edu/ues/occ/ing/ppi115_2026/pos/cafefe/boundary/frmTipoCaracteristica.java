package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.boundary;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.TipoCaracteristicaDAO;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.TipoCaracteristica;

@Named(value = "frmTipoCaracteristica")
@ViewScoped
public class frmTipoCaracteristica implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private TipoCaracteristicaDAO tipoCaracteristicaDAO;

    private TipoCaracteristica registro;

    @PostConstruct
    public void init() {
        limpiar();
    }

    public TipoCaracteristicaDAO getTipoCaracteristicaDAO() {
        return tipoCaracteristicaDAO;
    }

    public TipoCaracteristica createNewEntity() {
        TipoCaracteristica tc = new TipoCaracteristica();
        tc.setIdTipoCaracteristica(UUID.randomUUID());
        tc.setActivo(true);
        return tc;
    }

    public TipoCaracteristica getRegistro() {
        if (registro == null) {
            registro = createNewEntity();
        }
        return registro;
    }

    public void setRegistro(TipoCaracteristica registro) {
        this.registro = registro;
    }

    public void limpiar() {
        this.registro = createNewEntity();
    }

    public void btnCrearHandler() {
        try {
            if (registro.getIdTipoCaracteristica() == null) {
                registro.setIdTipoCaracteristica(UUID.randomUUID());
            }
            tipoCaracteristicaDAO.create(registro);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Tipo de característica guardado correctamente"));
            limpiar();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo guardar el tipo de característica"));
        }
    }
}