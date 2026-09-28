package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.boundary;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.PagoDetalleDAO;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.PagoDetalle;

@Named("pagoDetalleFrm")
@ViewScoped
public class PagoDetalleFrm implements Serializable {

    @Inject
    private PagoDetalleDAO pagoDetalleDAO;

    private PagoDetalle registroSeleccionado;
    private List<PagoDetalle> registros;

    @PostConstruct
    public void init() {
        this.registroSeleccionado = new PagoDetalle();
        this.registros = pagoDetalleDAO.obtenerTodos();
    }

    public void btnGuardarHandler() {
        if (this.registroSeleccionado.getIdPagoDetalle() == null) {
            this.registroSeleccionado.setIdPagoDetalle(UUID.randomUUID());
            pagoDetalleDAO.crear(this.registroSeleccionado);
        } else {
            pagoDetalleDAO.modificar(this.registroSeleccionado);
        }
        this.init();
    }

    public PagoDetalle getRegistroSeleccionado() { return registroSeleccionado; }
    public void setRegistroSeleccionado(PagoDetalle registroSeleccionado) { this.registroSeleccionado = registroSeleccionado; }
    public List<PagoDetalle> getRegistros() { return registros; }
}
