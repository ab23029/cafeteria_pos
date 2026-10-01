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
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.EmpleadoDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Empleado;

@Named(value = "empleadoFrm")
@ViewScoped
public class EmpleadoFrm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EmpleadoDAOInterface empleadoDAO;

    private List<Empleado> listaEmpleado;
    private Empleado registroSeleccionado;

    @PostConstruct
    public void init() {
        nuevoRegistro();
        cargarDatos();
    }

    public void nuevoRegistro() {
        this.registroSeleccionado = new Empleado();
        this.registroSeleccionado.setActivo(true);
    }

    public void cargarDatos() {
        try {
            this.listaEmpleado = empleadoDAO.findRange(0, 100);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al cargar el personal"));
        }
    }

    public void guardar() {
        try {
            // Validaciones básicas de campos obligatorios
            if (registroSeleccionado.getNombre() == null || registroSeleccionado.getNombre().trim().isEmpty() ||
                registroSeleccionado.getApellido() == null || registroSeleccionado.getApellido().trim().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Atención", "El nombre y el apellido son obligatorios"));
                return;
            }

            if (registroSeleccionado.getIdEmpleado() == null) {
                registroSeleccionado.setIdEmpleado(UUID.randomUUID());
                empleadoDAO.create(registroSeleccionado);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Empleado registrado correctamente"));
            } else {
                empleadoDAO.edit(registroSeleccionado);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Empleado actualizado correctamente"));
            }
            nuevoRegistro();
            cargarDatos();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al procesar la información"));
        }
    }

    public void seleccionar(Empleado e) {
        this.registroSeleccionado = e;
    }

    // Getters y Setters
    public List<Empleado> getListaEmpleado() { return listaEmpleado; }
    public Empleado getRegistroSeleccionado() { return registroSeleccionado; }
    public void setRegistroSeleccionado(Empleado registroSeleccionado) { this.registroSeleccionado = registroSeleccionado; }
}