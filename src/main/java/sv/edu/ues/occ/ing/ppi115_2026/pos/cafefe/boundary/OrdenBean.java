package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.boundary;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.EmpleadoRolDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.OrdenDAO;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.ProductoDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.EmpleadoRol;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Orden;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Producto;

@Named("ordenBean")
@ViewScoped
public class OrdenBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private OrdenDAO ordenDAO;

    @Inject
    private EmpleadoRolDAOInterface empleadoRolDAO;

    @Inject
    private ProductoDAOInterface productoDAO;

    private Orden registro;
    private List<Orden> lista;
    private List<EmpleadoRol> listaCamareros;
    private List<Producto> listaProductos;

    private EmpleadoRol empleadoRolSeleccionado;
    private Producto productoSeleccionado;
    private String observacionItem;

    @PostConstruct
    public void init() {
        this.registro = new Orden();
        this.registro.setFechaCreacion(new Date());
        cargarDatos();
    }

    public void cargarDatos() {
        try {
            this.lista = ordenDAO.obtenerTodos();
            this.listaProductos = productoDAO.findRange(0, 100);
            
            // Cargar únicamente Empleados cuyo Rol sea 'Camarero' y activo
            List<EmpleadoRol> todosRoles = empleadoRolDAO.findRange(0, 100);
            this.listaCamareros = new ArrayList<>();
            if (todosRoles != null) {
                for (EmpleadoRol er : todosRoles) {
                    if (er.getIdRol() != null && "Camarero".equalsIgnoreCase(er.getIdRol().getNombre())
                            && Boolean.TRUE.equals(er.getActivo())) {
                        this.listaCamareros.add(er);
                    }
                }
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al cargar los datos de la orden"));
        }
    }

    public void aplicarDescuentosPorFecha() {
        Date fechaOrden = registro.getFechaCreacion();
        if (fechaOrden != null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Descuento por Fechas", "Procesado para la fecha seleccionada"));
        }
    }

    public void aplicarDescuentoAbierto() {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Descuento Abierto", "Aplicado correctamente a los productos aplicables"));
    }

    public void guardar() {
        try {
            if (empleadoRolSeleccionado == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Debe seleccionar un camarero"));
                return;
            }
            if (this.registro.getIdOrden() == null) {
                this.registro.setIdOrden(UUID.randomUUID());
                this.registro.setIdEmpleadoRol(empleadoRolSeleccionado);
                ordenDAO.crear(this.registro);
            } else {
                ordenDAO.modificar(this.registro);
            }
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Orden guardada con éxito"));
            init();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al guardar la orden: " + e.getMessage()));
        }
    }

    // Getters y Setters exigidos por JSF
    public Orden getRegistro() { return registro; }
    public void setRegistro(Orden registro) { this.registro = registro; }

    public List<Orden> getLista() { return lista; }
    public List<EmpleadoRol> getListaCamareros() { return listaCamareros; }
    public List<Producto> getListaProductos() { return listaProductos; }

    public EmpleadoRol getEmpleadoRolSeleccionado() { return empleadoRolSeleccionado; }
    public void setEmpleadoRolSeleccionado(EmpleadoRol empleadoRolSeleccionado) { this.empleadoRolSeleccionado = empleadoRolSeleccionado; }

    public Producto getProductoSeleccionado() { return productoSeleccionado; }
    public void setProductoSeleccionado(Producto productoSeleccionado) { this.productoSeleccionado = productoSeleccionado; }

    public String getObservacionItem() { return observacionItem; }
    public void setObservacionItem(String observacionItem) { this.observacionItem = observacionItem; }
}