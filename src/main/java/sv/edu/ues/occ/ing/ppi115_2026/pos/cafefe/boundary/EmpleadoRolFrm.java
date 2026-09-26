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
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.EmpleadoDAO;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.EmpleadoRolDAO;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.RolDAO;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Empleado;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.EmpleadoRol;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Rol;

@Named(value = "empleadoRolFrm")
@ViewScoped
public class EmpleadoRolFrm implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private EmpleadoDAO empleadoDAO;

    @EJB
    private RolDAO rolDAO;

    @EJB
    private EmpleadoRolDAO empleadoRolDAO;

    private Empleado empleadoSeleccionado;
    private Empleado nuevoEmpleado;
    private List<EmpleadoRol> rolesAsignados;
    private UUID idRolSeleccionado;
    private String observacionesRol;

    @PostConstruct
    public void init() {
        limpiarEmpleado();
    }

    public void limpiarEmpleado() {
        this.nuevoEmpleado = new Empleado();
        this.nuevoEmpleado.setActivo(true);
    }

    public void btnCrearEmpleadoHandler() {
        if (nuevoEmpleado != null) {
            nuevoEmpleado.setIdEmpleado(UUID.randomUUID());
            empleadoDAO.create(nuevoEmpleado);
            
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Empleado registrado correctamente"));
            
            limpiarEmpleado();
        }
    }

    public void onEmpleadoSelect(SelectEvent<Empleado> event) {
        this.empleadoSeleccionado = event.getObject();
        cargarRolesEmpleado();
    }

    public void cargarRolesEmpleado() {
        if (empleadoSeleccionado != null) {
            rolesAsignados = empleadoRolDAO.findByEmpleado(empleadoSeleccionado.getIdEmpleado());
        }
    }

    public void btnAsignarRolHandler() {
        if (empleadoSeleccionado == null || idRolSeleccionado == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Seleccione un empleado y un rol"));
            return;
        }

        Rol rol = rolDAO.find(idRolSeleccionado);
        if (rol != null) {
            EmpleadoRol er = new EmpleadoRol();
            er.setIdEmpleadoRol(UUID.randomUUID());
            er.setIdEmpleado(empleadoSeleccionado.getIdEmpleado());
            er.setIdRol(rol);
            er.setActivo(true);
            er.setObservaciones(observacionesRol != null ? observacionesRol : "Asignado desde panel");

            empleadoRolDAO.create(er);
            cargarRolesEmpleado();
            
            this.observacionesRol = "";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Rol asignado correctamente"));
        }
    }

    // Getters y Setters
    public EmpleadoDAO getEmpleadoDAO() { return empleadoDAO; }
    public RolDAO getRolDAO() { return rolDAO; }
    public EmpleadoRolDAO getEmpleadoRolDAO() { return empleadoRolDAO; }

    public Empleado getEmpleadoSeleccionado() { return empleadoSeleccionado; }
    public void setEmpleadoSeleccionado(Empleado empleadoSeleccionado) { this.empleadoSeleccionado = empleadoSeleccionado; }

    public Empleado getNuevoEmpleado() { return nuevoEmpleado; }
    public void setNuevoEmpleado(Empleado nuevoEmpleado) { this.nuevoEmpleado = nuevoEmpleado; }

    public List<EmpleadoRol> getRolesAsignados() { return rolesAsignados; }
    public void setRolesAsignados(List<EmpleadoRol> rolesAsignados) { this.rolesAsignados = rolesAsignados; }

    public UUID getIdRolSeleccionado() { return idRolSeleccionado; }
    public void setIdRolSeleccionado(UUID idRolSeleccionado) { this.idRolSeleccionado = idRolSeleccionado; }

    public String getObservacionesRol() { return observacionesRol; }
    public void setObservacionesRol(String observacionesRol) { this.observacionesRol = observacionesRol; }
}