
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
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.ProductoTipoProductoPK;
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

            // Asignar el Tipo de Producto
            registroSeleccionado.setIdTipoProducto(tipo);

            if (registroSeleccionado.getProductoTipoProductoPK() == null) {
                // Crear la llave primaria compuesta PK
                UUID idGenerado = UUID.randomUUID();
                ProductoTipoProductoPK pk = new ProductoTipoProductoPK(idGenerado, idProductoSeleccionado);
                registroSeleccionado.setProductoTipoProductoPK(pk);
                
                // Relacionar el objeto producto
                registroSeleccionado.setProducto(prod);

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
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al guardar la asignación"));
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