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
import java.util.regex.Pattern;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.CaracteristicaDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.ProductoCaracteristicaDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.ProductoDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Caracteristica;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Producto;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.ProductoCaracteristica;

@Named(value = "productoCaracteristicaFrm")
@ViewScoped
public class ProductoCaracteristicaFrm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ProductoCaracteristicaDAOInterface productoCaracteristicaDAO;

    @Inject
    private ProductoDAOInterface productoDAO;

    @Inject
    private CaracteristicaDAOInterface caracteristicaDAO;

    private List<ProductoCaracteristica> listaProductoCaracteristica;
    private List<Producto> listaProductos;
    private List<Caracteristica> listaCaracteristicas;

    private ProductoCaracteristica registroSeleccionado;
    private UUID idProductoSeleccionado;
    private UUID idCaracteristicaSeleccionada;

    @PostConstruct
    public void init() {
        nuevoRegistro();
        cargarDatos();
    }

    public void nuevoRegistro() {
        this.registroSeleccionado = new ProductoCaracteristica();
        this.idProductoSeleccionado = null;
        this.idCaracteristicaSeleccionada = null;
    }

    public void cargarDatos() {
        try {
            this.listaProductoCaracteristica = productoCaracteristicaDAO.findRange(0, 100);
            this.listaProductos = productoDAO.findRange(0, 100);
            this.listaCaracteristicas = caracteristicaDAO.findRange(0, 100);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al cargar la información"));
        }
    }

    public void guardar() {
        try {
            if (idProductoSeleccionado != null && idCaracteristicaSeleccionada != null) {
                Producto prod = productoDAO.find(idProductoSeleccionado);
                Caracteristica car = caracteristicaDAO.find(idCaracteristicaSeleccionada);

                // Validación de Expresión Regular exigida por la rúbrica
                if (car != null && car.getIdTipoCaracteristica() != null 
                        && car.getIdTipoCaracteristica().getExpresionRegular() != null) {
                    
                    String regex = car.getIdTipoCaracteristica().getExpresionRegular();
                    String valor = registroSeleccionado.getValor();

                    if (valor == null || !Pattern.matches(regex, valor)) {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de Validación", 
                                        "El valor ingresado no cumple con la Expresión Regular: " + regex));
                        return;
                    }
                }

                registroSeleccionado.setIdProducto(prod);
                registroSeleccionado.setIdCaracteristica(car);

                if (registroSeleccionado.getIdProductoCaracteristica() == null) {
                    registroSeleccionado.setIdProductoCaracteristica(UUID.randomUUID());
                    productoCaracteristicaDAO.create(registroSeleccionado);
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Característica asignada al producto"));
                } else {
                    productoCaracteristicaDAO.edit(registroSeleccionado);
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Característica actualizada"));
                }
                nuevoRegistro();
                cargarDatos();
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Atención", "Debe seleccionar un Producto y una Característica"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al guardar el registro"));
        }
    }

    // Getters y Setters
    public List<ProductoCaracteristica> getListaProductoCaracteristica() { return listaProductoCaracteristica; }
    public List<Producto> getListaProductos() { return listaProductos; }
    public List<Caracteristica> getListaCaracteristicas() { return listaCaracteristicas; }

    public ProductoCaracteristica getRegistroSeleccionado() { return registroSeleccionado; }
    public void setRegistroSeleccionado(ProductoCaracteristica registroSeleccionado) { this.registroSeleccionado = registroSeleccionado; }

    public UUID getIdProductoSeleccionado() { return idProductoSeleccionado; }
    public void setIdProductoSeleccionado(UUID idProductoSeleccionado) { this.idProductoSeleccionado = idProductoSeleccionado; }

    public UUID getIdCaracteristicaSeleccionada() { return idCaracteristicaSeleccionada; }
    public void setIdCaracteristicaSeleccionada(UUID idCaracteristicaSeleccionada) { this.idCaracteristicaSeleccionada = idCaracteristicaSeleccionada; }
}