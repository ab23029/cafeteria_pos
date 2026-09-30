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
                // Filtramos únicamente los tipos activos de la lista cargada
                List<TipoDescuento> todos = tipoDescuentoDAO.findRange(0, 100);
                if (todos != null) {
                    this.listaTiposDescuento = todos.stream()
                            .filter(t -> Boolean.TRUE.equals(t.getActivo()))
                            .toList();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al cargar datos", ex);
        }
    }

    public void btnGuardarHandler() {
        try {
            if (registro != null && idTipoDescuentoSeleccionado != null) {
                // Buscamos el tipo seleccionado dentro de la lista de activos
                TipoDescuento td = listaTiposDescuento.stream()
                        .filter(t -> idTipoDescuentoSeleccionado.equals(t.getIdTipoDescuento()))
                        .findFirst()
                        .orElse(null);

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
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El Tipo de Descuento seleccionado no es válido"));
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

    public Descuento getRegistro() { return registro; }
    public void setRegistro(Descuento registro) { this.registro = registro; }

    public List<Descuento> getListaRegistros() { return listaRegistros; }
    public void setListaRegistros(List<Descuento> listaRegistros) { this.listaRegistros = listaRegistros; }

    public List<TipoDescuento> getListaTiposDescuento() { return listaTiposDescuento; }
    public void setListaTiposDescuento(List<TipoDescuento> listaTiposDescuento) { this.listaTiposDescuento = listaTiposDescuento; }

    public UUID getIdTipoDescuentoSeleccionado() { return idTipoDescuentoSeleccionado; }
    public void setIdTipoDescuentoSeleccionado(UUID idTipoDescuentoSeleccionado) { this.idTipoDescuentoSeleccionado = idTipoDescuentoSeleccionado; }
}