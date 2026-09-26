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
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.ProductoDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Producto;

@Named(value = "productoFrm")
@ViewScoped
public class ProductoFrm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ProductoDAOInterface productoDAO;

    private List<Producto> listaProductos;
    private Producto registroSeleccionado;

    @PostConstruct
    public void init() {
        nuevoRegistro();
        cargarDatos();
    }

    public void nuevoRegistro() {
        this.registroSeleccionado = new Producto();
        this.registroSeleccionado.setActivo(true);
    }

    public void cargarDatos() {
        try {
            this.listaProductos = productoDAO.findRange(0, 100);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudieron cargar los productos"));
        }
    }

    public void guardar() {
        try {
            if (registroSeleccionado != null) {
                if (registroSeleccionado.getIdProducto() == null) {
                    registroSeleccionado.setIdProducto(UUID.randomUUID());
                    productoDAO.create(registroSeleccionado);
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Producto guardado correctamente"));
                } else {
                    productoDAO.edit(registroSeleccionado);
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Producto actualizado correctamente"));
                }
                nuevoRegistro();
                cargarDatos();
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al guardar el producto"));
        }
    }

    // Getters y Setters
    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public Producto getRegistroSeleccionado() {
        return registroSeleccionado;
    }

    public void setRegistroSeleccionado(Producto registroSeleccionado) {
        this.registroSeleccionado = registroSeleccionado;
    }
}