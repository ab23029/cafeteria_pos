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
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.DescuentoDAO;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.TipoDescuentoDAO;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Descuento;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.TipoDescuento;

@Named(value = "descuentoFrm")
@ViewScoped
public class DescuentoFrm implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private DescuentoDAO descuentoDAO;

    @EJB
    private TipoDescuentoDAO tipoDescuentoDAO;

    private Descuento registro;
    private List<Descuento> listaRegistros;
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
        this.listaRegistros = descuentoDAO.findAll();
    }

    public void btnGuardarHandler() {
        if (registro != null && idTipoDescuentoSeleccionado != null) {
            TipoDescuento td = tipoDescuentoDAO.find(idTipoDescuentoSeleccionado);
            if (td != null) {
                registro.setIdDescuento(UUID.randomUUID());
                registro.setIdTipoDescuento(td);
                descuentoDAO.create(registro);

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Descuento registrado correctamente"));
                limpiar();
                cargarDatos();
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Seleccione un Tipo de Descuento válido"));
        }
    }

    // Getters y Setters
    public DescuentoDAO getDescuentoDAO() { return descuentoDAO; }
    public TipoDescuentoDAO getTipoDescuentoDAO() { return tipoDescuentoDAO; }
    public Descuento getRegistro() { return registro; }
    public void setRegistro(Descuento registro) { this.registro = registro; }
    public List<Descuento> getListaRegistros() { return listaRegistros; }
    public UUID getIdTipoDescuentoSeleccionado() { return idTipoDescuentoSeleccionado; }
    public void setIdTipoDescuentoSeleccionado(UUID idTipoDescuentoSeleccionado) { this.idTipoDescuentoSeleccionado = idTipoDescuentoSeleccionado; }
}