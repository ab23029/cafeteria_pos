package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.boundary;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.OrdenDAO;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Orden;

@Named("ordenFrm")
@ViewScoped
public class OrdenFrm implements Serializable {

    @Inject
    private OrdenDAO ordenDAO;

    private Orden registroSeleccionado;
    private List<Orden> registros;

    @PostConstruct
    public void init() {
        this.registroSeleccionado = new Orden();
        this.registros = ordenDAO.obtenerTodos();
    }

    public void btnGuardarHandler() {
        if (this.registroSeleccionado.getIdOrden() == null) {
            this.registroSeleccionado.setIdOrden(UUID.randomUUID());
            ordenDAO.crear(this.registroSeleccionado);
        } else {
            ordenDAO.modificar(this.registroSeleccionado);
        }
        this.init();
    }

    public Orden getRegistroSeleccionado() { return registroSeleccionado; }
    public void setRegistroSeleccionado(Orden registroSeleccionado) { this.registroSeleccionado = registroSeleccionado; }
    public List<Orden> getRegistros() { return registros; }
}
