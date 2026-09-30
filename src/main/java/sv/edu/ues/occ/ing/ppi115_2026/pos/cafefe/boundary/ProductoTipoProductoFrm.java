package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.boundary;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.ProductoDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.ProductoTipoProductoDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.TipoProductoDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Producto;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.ProductoTipoProducto;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.TipoProducto;

@Named(value = "productoTipoProductoFrm")
@ViewScoped
public class ProductoTipoProductoFrm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ProductoTipoProductoDAOInterface productoTipoProductoDAO;

    @Inject
    private ProductoDAOInterface productoDAO;

    @Inject
    private TipoProductoDAOInterface tipoProductoDAO;

    private List<ProductoTipoProducto> listaProductoTipoProducto;
    private List<Producto> listaProductos;
    private List<TipoProducto> listaTiposProducto;

    private ProductoTipoProducto registroSeleccionado;
    private UUID idProductoSeleccionado;
    private UUID idTipoProductoSeleccionado;

    @PostConstruct
    public void init() {
        nuevoRegistro();
        cargarDatos();
    }

    public void nuevoRegistro() {
        this.registroSeleccionado = new ProductoTipoProducto();
        this.registroSeleccionado.setFechaCreacion(new Date());
        this.idProductoSeleccionado = null;
        this.idTipoProductoSeleccionado = null;
    }

    public void cargarDatos() {
        try {
            this.listaProductoTipoProducto = productoTipoProductoDAO.findRange(0, 100);
            this.listaProductos = productoDAO.findRange(0, 100);
            this.listaTiposProducto = tipoProductoDAO.findRange(0, 100);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al cargar listas de datos"));
        }
    }

    public void guardar() {
        try {
            if (idProductoSeleccionado != null && idTipoProductoSeleccionado != null) {
                Producto prod = productoDAO.find(idProductoSeleccionado);
                TipoProducto tipo = tipoProductoDAO.find(idTipoProductoSeleccionado);

                // Validación requerida por la rúbrica: No permitir asignar tipos inactivos
                if (tipo == null || Boolean.FALSE.equals(tipo.getActivo())) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se puede asignar un Tipo de Producto deshabilitado/inactivo."));
                    return;
                }

                // Asignar entidades relacionadas
                registroSeleccionado.setIdProducto(prod);
                registroSeleccionado.setIdTipoProducto(tipo);

                if (registroSeleccionado.getIdProductoTipoProducto() == null) {
                    // Generar la llave primaria de la entidad
                    registroSeleccionado.setIdProductoTipoProducto(UUID.randomUUID());

                    productoTipoProductoDAO.create(registroSeleccionado);
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Asignación realizada correctamente"));
                } else {
                    productoTipoProductoDAO.edit(registroSeleccionado);
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Asignación actualizada"));
                }
                nuevoRegistro();
                cargarDatos();
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Atención", "Debe seleccionar un Producto y un Tipo"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al guardar la asignación: " + e.getMessage()));
        }
    }

    // Método para seleccionar un registro existente de la tabla (Edición)
    public void seleccionarRegistro(ProductoTipoProducto ptp) {
        if (ptp != null) {
            this.registroSeleccionado = ptp;
            this.idProductoSeleccionado = ptp.getIdProducto() != null ? ptp.getIdProducto().getIdProducto() : null;
            this.idTipoProductoSeleccionado = ptp.getIdTipoProducto() != null ? ptp.getIdTipoProducto().getIdTipoProducto() : null;
        }
    }

    // Getters y Setters
    public List<ProductoTipoProducto> getListaProductoTipoProducto() { return listaProductoTipoProducto; }
    public List<Producto> getListaProductos() { return listaProductos; }
    public List<TipoProducto> getListaTiposProducto() { return listaTiposProducto; }

    public ProductoTipoProducto getRegistroSeleccionado() { return registroSeleccionado; }
    public void setRegistroSeleccionado(ProductoTipoProducto registroSeleccionado) { this.registroSeleccionado = registroSeleccionado; }

    public UUID getIdProductoSeleccionado() { return idProductoSeleccionado; }
    public void setIdProductoSeleccionado(UUID idProductoSeleccionado) { this.idProductoSeleccionado = idProductoSeleccionado; }

    public UUID getIdTipoProductoSeleccionado() { return idTipoProductoSeleccionado; }
    public void setIdTipoProductoSeleccionado(UUID idTipoProductoSeleccionado) { this.idTipoProductoSeleccionado = idTipoProductoSeleccionado; }
}