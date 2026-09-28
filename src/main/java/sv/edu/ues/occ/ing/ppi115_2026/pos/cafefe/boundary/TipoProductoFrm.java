package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.boundary;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.TipoProductoDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.TipoProducto;

@Named(value = "tipoProductoFrm")
@ViewScoped
public class TipoProductoFrm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private TipoProductoDAOInterface tipoProductoDAO;

    private List<TipoProducto> listaTipoProducto;
    private TipoProducto registroSeleccionado;

    @PostConstruct
    public void init() {
        nuevoRegistro();
        cargarDatos();
    }

    public void nuevoRegistro() {
        this.registroSeleccionado = new TipoProducto();
        this.registroSeleccionado.setActivo(true);
    }

    public void cargarDatos() {
        try {
            this.listaTipoProducto = tipoProductoDAO.findRange(0, 100);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al cargar tipos de producto"));
        }
    }

    public void guardar() {
        try {
            if (registroSeleccionado != null) {
                if (registroSeleccionado.getIdTipoProducto() == null) {
                    registroSeleccionado.setIdTipoProducto(UUID.randomUUID());
                    tipoProductoDAO.create(registroSeleccionado);
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Tipo de producto creado correctamente"));
                } else {
                    tipoProductoDAO.edit(registroSeleccionado);
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Tipo de producto actualizado"));
                }
                nuevoRegistro();
                cargarDatos();
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al guardar los datos"));
        }
    }

    // Getters y Setters
    public List<TipoProducto> getListaTipoProducto() { return listaTipoProducto; }
    public void setListaTipoProducto(List<TipoProducto> listaTipoProducto) { this.listaTipoProducto = listaTipoProducto; }

    public TipoProducto getRegistroSeleccionado() { return registroSeleccionado; }
    public void setRegistroSeleccionado(TipoProducto registroSeleccionado) { this.registroSeleccionado = registroSeleccionado; }
}