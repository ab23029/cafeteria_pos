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
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.DescuentoDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.DescuentoProductoDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.ProductoDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Descuento;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.DescuentoProducto;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Producto;

@Named(value = "descuentoProductoFrm")
@ViewScoped
public class DescuentoProductoFrm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private DescuentoProductoDAOInterface descuentoProductoDAO;

    @Inject
    private DescuentoDAOInterface descuentoDAO;

    @Inject
    private ProductoDAOInterface productoDAO;

    private List<DescuentoProducto> listaDescuentoProducto;
    private List<Descuento> listaDescuentos;
    private List<Producto> listaProductos;

    private DescuentoProducto registroSeleccionado;
    private UUID idDescuentoSeleccionado;
    private UUID idProductoSeleccionado;

    @PostConstruct
    public void init() {
        nuevoRegistro();
        cargarDatos();
    }

    public void nuevoRegistro() {
        this.registroSeleccionado = new DescuentoProducto();
        this.idDescuentoSeleccionado = null;
        this.idProductoSeleccionado = null;
    }

    public void cargarDatos() {
        try {
            this.listaDescuentoProducto = descuentoProductoDAO.findRange(0, 100);
            this.listaDescuentos = descuentoDAO.findRange(0, 100);
            this.listaProductos = productoDAO.findRange(0, 100);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al cargar los datos"));
        }
    }

    public void guardar() {
        try {
            if (registroSeleccionado == null || idDescuentoSeleccionado == null || idProductoSeleccionado == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Debe seleccionar un Producto y un Descuento"));
                return;
            }

            // Validación de límites de Porcentaje / Valor
            if (registroSeleccionado.getValor() != null) {
                if (registroSeleccionado.getValor().doubleValue() < 0 || registroSeleccionado.getValor().doubleValue() > 100) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de Validación", "El porcentaje de descuento debe estar entre 0% y 100%"));
                    return;
                }
            }

            // Validación de Fechas utilizando .before() para java.util.Date
            if (registroSeleccionado.getFechaDesde() != null && registroSeleccionado.getFechaHasta() != null) {
                if (registroSeleccionado.getFechaHasta().before(registroSeleccionado.getFechaDesde())) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de Validación", "La Fecha Hasta no puede ser anterior a la Fecha Desde"));
                    return;
                }
            }

            Descuento desc = descuentoDAO.find(idDescuentoSeleccionado);
            Producto prod = productoDAO.find(idProductoSeleccionado);

            registroSeleccionado.setIdDescuento(desc);
            registroSeleccionado.setIdProducto(prod);

            if (registroSeleccionado.getIdDescuentoProducto() == null) {
                registroSeleccionado.setIdDescuentoProducto(UUID.randomUUID());
                descuentoProductoDAO.create(registroSeleccionado);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Asociación Descuento-Producto creada correctamente"));
            } else {
                descuentoProductoDAO.edit(registroSeleccionado);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Registro actualizado correctamente"));
            }
            nuevoRegistro();
            cargarDatos();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al guardar los datos"));
        }
    }

    // Getters y Setters
    public List<DescuentoProducto> getListaDescuentoProducto() { return listaDescuentoProducto; }
    public void setListaDescuentoProducto(List<DescuentoProducto> listaDescuentoProducto) { this.listaDescuentoProducto = listaDescuentoProducto; }

    public List<Descuento> getListaDescuentos() { return listaDescuentos; }
    public void setListaDescuentos(List<Descuento> listaDescuentos) { this.listaDescuentos = listaDescuentos; }

    public List<Producto> getListaProductos() { return listaProductos; }
    public void setListaProductos(List<Producto> listaProductos) { this.listaProductos = listaProductos; }

    public DescuentoProducto getRegistroSeleccionado() { return registroSeleccionado; }
    public void setRegistroSeleccionado(DescuentoProducto registroSeleccionado) { this.registroSeleccionado = registroSeleccionado; }

    public UUID getIdDescuentoSeleccionado() { return idDescuentoSeleccionado; }
    public void setIdDescuentoSeleccionado(UUID idDescuentoSeleccionado) { this.idDescuentoSeleccionado = idDescuentoSeleccionado; }

    public UUID getIdProductoSeleccionado() { return idProductoSeleccionado; }
    public void setIdProductoSeleccionado(UUID idProductoSeleccionado) { this.idProductoSeleccionado = idProductoSeleccionado; }
}