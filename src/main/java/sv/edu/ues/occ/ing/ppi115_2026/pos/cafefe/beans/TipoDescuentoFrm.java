package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.beans;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.faces.event.ActionEvent;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.TipoDescuentoDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.TipoDescuento;

@Named("tipoDescuentoFrm")
@ViewScoped
public class TipoDescuentoFrm implements Serializable {

    @Inject
    private TipoDescuentoDAOInterface tipoDescuentoDAO;

    private List<TipoDescuento> registros;
    private TipoDescuento registroSeleccionado;

    @PostConstruct
    public void init() {
        cargarRegistros();
        this.registroSeleccionado = new TipoDescuento();
    }

    public void cargarRegistros() {
        try {
            if (tipoDescuentoDAO != null) {
                this.registros = tipoDescuentoDAO.findRange(0, 100);
            }
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al cargar registros", ex);
        }
    }

    // Sobrecarga para actionListener="#{tipoDescuentoFrm.btnNuevoHandler}"
    public void btnNuevoHandler(ActionEvent ae) {
        btnNuevoHandler();
    }

    // Método para invocaciones directas
    public void btnNuevoHandler() {
        this.registroSeleccionado = new TipoDescuento();
    }

    // Sobrecarga para actionListener="#{tipoDescuentoFrm.btnGuardarHandler}"
    public void btnGuardarHandler(ActionEvent ae) {
        guardar();
    }

    public void btnGuardarHandler() {
        guardar();
    }

    public void guardar() {
        try {
            if (this.registroSeleccionado != null) {
                if (this.registroSeleccionado.getIdTipoDescuento() == null) {
                    this.registroSeleccionado.setIdTipoDescuento(UUID.randomUUID());
                }
                tipoDescuentoDAO.create(this.registroSeleccionado);
                this.registroSeleccionado = new TipoDescuento();
                cargarRegistros();
            }
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al guardar", ex);
        }
    }

    public List<TipoDescuento> getModelo() {
        return registros;
    }

    public List<TipoDescuento> getRegistros() {
        return registros;
    }

    public void setRegistros(List<TipoDescuento> registros) {
        this.registros = registros;
    }

    public TipoDescuento getRegistroSeleccionado() {
        return registroSeleccionado;
    }

    public void setRegistroSeleccionado(TipoDescuento registroSeleccionado) {
        this.registroSeleccionado = registroSeleccionado;
    }
}