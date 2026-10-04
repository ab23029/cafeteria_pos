package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.boundary;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.DescuentoProductoDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.EmpleadoRolDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.OrdenDAO;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.ProductoDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.*;

@Named("ordenFrm")
@ViewScoped
public class OrdenFrm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private OrdenDAO ordenDAO;

    @Inject
    private EmpleadoRolDAOInterface empleadoRolDAO;

    @Inject
    private ProductoDAOInterface productoDAO;

    @Inject
    private DescuentoProductoDAOInterface descuentoProductoDAO;

    private Orden registroSeleccionado;
    private List<Orden> registros;
    private List<OrdenProducto> itemsOrden = new ArrayList<>();
    private List<EmpleadoRol> listaCamareros;
    private List<Producto> listaProductos;

    private Producto productoSeleccionado;
    private String observacionItem;

    @PostConstruct
    public void init() {
        this.registroSeleccionado = new Orden();
        this.registroSeleccionado.setFechaCreacion(new Date());
        this.itemsOrden = new ArrayList<>();
        cargarDatos();
    }

    public void cargarDatos() {
        try {
            this.registros = ordenDAO.obtenerTodos();
            this.listaProductos = productoDAO.findRange(0, 100);

            List<EmpleadoRol> todosRoles = empleadoRolDAO.findRange(0, 100);
            this.listaCamareros = new ArrayList<>();
            if (todosRoles != null) {
                for (EmpleadoRol er : todosRoles) {
                    if (er.getIdRol() != null && er.getIdRol().getNombre() != null) {
                        String nombreRol = er.getIdRol().getNombre().trim();
                        if ("CAMARERO".equalsIgnoreCase(nombreRol) && Boolean.TRUE.equals(er.getActivo())) {
                            this.listaCamareros.add(er);
                        }
                    }
                }
            }
        } catch (Exception e) {
            mensajeError("Error al cargar datos del formulario de órdenes.");
        }
    }

    public void agregarProducto() {
        if (productoSeleccionado == null) {
            mensajeWarn("Debe seleccionar un producto para agregar.");
            return;
        }

        OrdenProducto nuevoItem = new OrdenProducto();
        nuevoItem.setIdOrdenProducto(UUID.randomUUID());
        nuevoItem.setIdProducto(productoSeleccionado);
        nuevoItem.setIdOrden(this.registroSeleccionado); 
        nuevoItem.setPrecio(productoSeleccionado.getPrecioSugerido());
        nuevoItem.setObservaciones(observacionItem != null && !observacionItem.trim().isEmpty() 
                ? observacionItem : "Precio base sugerido.");

        this.itemsOrden.add(nuevoItem);
        
        this.productoSeleccionado = null;
        this.observacionItem = "";
        mensajeInfo("Producto agregado a la orden.");
    }

    public void removerProducto(OrdenProducto item) {
        if (item != null) {
            this.itemsOrden.remove(item);
            mensajeInfo("Producto eliminado de la orden.");
        }
    }

    public void btnGuardarHandler() {
        if (this.registroSeleccionado.getIdEmpleadoRol() == null) {
            mensajeError("Debe seleccionar un empleado camarero.");
            return;
        }

        if (this.itemsOrden == null || this.itemsOrden.isEmpty()) {
            mensajeWarn("Debe agregar al menos un producto a la orden.");
            return;
        }

        try {
            if (this.registroSeleccionado.getIdOrden() == null) {
                this.registroSeleccionado.setIdOrden(UUID.randomUUID());

                for (OrdenProducto op : this.itemsOrden) {
                    op.setIdOrden(this.registroSeleccionado);
                }
                this.registroSeleccionado.setOrdenProductoList(this.itemsOrden);

                ordenDAO.crear(this.registroSeleccionado);
                mensajeInfo("Orden creada exitosamente.");
            } else {
                ordenDAO.modificar(this.registroSeleccionado);
                mensajeInfo("Orden modificada correctamente.");
            }

            limpiarFormulario();
        } catch (Exception e) {
            mensajeError("Error al guardar la orden: " + e.getMessage());
        }
    }

    private void limpiarFormulario() {
        this.registroSeleccionado = new Orden();
        this.registroSeleccionado.setFechaCreacion(new Date());
        this.itemsOrden = new ArrayList<>();
        this.productoSeleccionado = null;
        this.observacionItem = "";
        cargarDatos();
    }

    public void aplicarDescuentosAutomaticos() {
        Date fechaOrden = registroSeleccionado.getFechaCreacion();

        if (fechaOrden == null) {
            mensajeWarn("Debe definir la fecha de la orden.");
            return;
        }

        List<DescuentoProducto> listaDescuentosBase = descuentoProductoDAO.findRange(0, 500);

        for (OrdenProducto item : itemsOrden) {
            Producto prod = item.getIdProducto();
            if (prod == null) continue;

            DescuentoProducto descuentoValido = null;

            if (listaDescuentosBase != null) {
                for (DescuentoProducto dp : listaDescuentosBase) {
                    if (dp.getIdProducto() != null && dp.getIdProducto().getIdProducto().equals(prod.getIdProducto())) {
                        
                        boolean esValido = false;

                        // Rango Cerrado
                        if (dp.getFechaDesde() != null && dp.getFechaHasta() != null) {
                            if (!fechaOrden.before(dp.getFechaDesde()) && !fechaOrden.after(dp.getFechaHasta())) {
                                esValido = true;
                            }
                        } 
                        // Rango Abierto
                        else if (dp.getFechaDesde() != null && dp.getFechaHasta() == null) {
                            if (!fechaOrden.before(dp.getFechaDesde())) {
                                esValido = true;
                            }
                        }

                        if (esValido) {
                            int valorActual = (dp.getValor() != null) ? dp.getValor() : 0;
                            int valorMaximo = (descuentoValido != null && descuentoValido.getValor() != null) ? descuentoValido.getValor() : -1;

                            if (descuentoValido == null || valorActual > valorMaximo) {
                                descuentoValido = dp;
                            }
                        }
                    }
                }
            }

            if (descuentoValido != null) {
                BigDecimal nuevoPrecio = calcularPrecioConDescuento(prod.getPrecioSugerido(), descuentoValido.getValor());
                item.setPrecio(nuevoPrecio);
                item.setObservaciones("Descuento aplicado: " + descuentoValido.getValor() + "%");
            } else {
                item.setPrecio(prod.getPrecioSugerido());
                item.setObservaciones("Precio regular (Sin descuento vigente a la fecha).");
            }
        }
        mensajeInfo("Cálculo de descuentos finalizado según la fecha de la orden.");
    }

    private BigDecimal calcularPrecioConDescuento(BigDecimal precioOriginal, Integer porcentaje) {
        if (precioOriginal == null || porcentaje == null) return precioOriginal;
        BigDecimal desc = BigDecimal.valueOf(porcentaje).divide(BigDecimal.valueOf(100));
        BigDecimal montoDescuento = precioOriginal.multiply(desc);
        return precioOriginal.subtract(montoDescuento).setScale(2, RoundingMode.HALF_UP);
    }

    private void mensajeError(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", msg));
    }

    private void mensajeWarn(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atención", msg));
    }

    private void mensajeInfo(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", msg));
    }

    public Orden getRegistroSeleccionado() { return registroSeleccionado; }
    public void setRegistroSeleccionado(Orden registroSeleccionado) { this.registroSeleccionado = registroSeleccionado; }
    public List<Orden> getRegistros() { return registros; }
    public List<OrdenProducto> getItemsOrden() { return itemsOrden; }
    public void setItemsOrden(List<OrdenProducto> itemsOrden) { this.itemsOrden = itemsOrden; }
    public List<EmpleadoRol> getListaCamareros() { return listaCamareros; }
    public List<Producto> getListaProductos() { return listaProductos; }
    public Producto getProductoSeleccionado() { return productoSeleccionado; }
    public void setProductoSeleccionado(Producto productoSeleccionado) { this.productoSeleccionado = productoSeleccionado; }
    public String getObservacionItem() { return observacionItem; }
    public void setObservacionItem(String observacionItem) { this.observacionItem = observacionItem; }
}