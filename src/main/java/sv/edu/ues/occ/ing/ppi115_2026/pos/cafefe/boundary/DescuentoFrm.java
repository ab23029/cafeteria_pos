package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.boundary;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.DescuentoDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.TipoDescuentoDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Descuento;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.TipoDescuento;

@Named(value = "descuentoFrm")
@ViewScoped
public class DescuentoFrm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private DescuentoDAOInterface descuentoDAO;

    @Inject
    private TipoDescuentoDAOInterface tipoDescuentoDAO;

    private Descuento registro;
    private List<Descuento> listaRegistros;
    private List<TipoDescuento> listaTiposDescuento;
    private UUID idTipoDescuentoSeleccionado;

    @PostConstruct
    public void init() {
        limpiar();
        cargarDatos();
    }

    public void limpiar() {
        this.registro = new Descuento();
        this.idTipoDescuentoSeleccionado = null;
    }

    public void cargarDatos() {
        try {
            if (descuentoDAO != null) {
                this.listaRegistros = descuentoDAO.findRange(0, 100);
            }
            if (tipoDescuentoDAO != null) {
                this.listaTiposDescuento = tipoDescuentoDAO.findRange(0, 100);
            }
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al cargar datos", ex);
        }
    }

    public void btnGuardarHandler() {
        try {
            if (registro != null && idTipoDescuentoSeleccionado != null) {
                TipoDescuento td = tipoDescuentoDAO.find(idTipoDescuentoSeleccionado);
                if (td != null) {
                    if (registro.getIdDescuento() == null) {
                        registro.setIdDescuento(UUID.randomUUID());
                    }
                    registro.setIdTipoDescuento(td);
                    descuentoDAO.create(registro);

                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Descuento registrado correctamente"));
                    limpiar();
                    cargarDatos();
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se encontró el Tipo de Descuento seleccionado"));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Seleccione un Tipo de Descuento válido"));
            }
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al guardar el descuento", ex);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un problema al guardar el registro"));
        }
    }

    // Getters y Setters
    public Descuento getRegistro() { return registro; }
    public void setRegistro(Descuento registro) { this.registro = registro; }
    
    public List<Descuento> getListaRegistros() { return listaRegistros; }
    public void setListaRegistros(List<Descuento> listaRegistros) { this.listaRegistros = listaRegistros; }

    public List<TipoDescuento> getListaTiposDescuento() { return listaTiposDescuento; }
    public void setListaTiposDescuento(List<TipoDescuento> listaTiposDescuento) { this.listaTiposDescuento = listaTiposDescuento; }

    public UUID getIdTipoDescuentoSeleccionado() { return idTipoDescuentoSeleccionado; }
    public void setIdTipoDescuentoSeleccionado(UUID idTipoDescuentoSeleccionado) { this.idTipoDescuentoSeleccionado = idTipoDescuentoSeleccionado; }
}