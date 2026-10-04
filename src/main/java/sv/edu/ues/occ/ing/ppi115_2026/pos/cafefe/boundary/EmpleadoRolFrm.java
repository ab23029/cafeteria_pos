package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.boundary;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.EmpleadoDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.EmpleadoRolDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.RolDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Empleado;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.EmpleadoRol;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Rol;

@Named(value = "empleadoRolFrm")
@ViewScoped
public class EmpleadoRolFrm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EmpleadoRolDAOInterface empleadoRolDAO;

    @Inject
    private EmpleadoDAOInterface empleadoDAO;

    @Inject
    private RolDAOInterface rolDAO;

    private List<Empleado> listaEmpleados;
    private List<Rol> listaRoles;

    private UUID idEmpleadoSeleccionado;
    private UUID idRolSeleccionado;

    @PostConstruct
    public void init() {
        cargarListas();
    }

    public void cargarListas() {
        try {
            this.listaEmpleados = empleadoDAO.findRange(0, 1000);
            this.listaRoles = rolDAO.findRange(0, 1000);

            if (this.listaEmpleados == null) {
                this.listaEmpleados = new ArrayList<>();
            }
            if (this.listaRoles == null) {
                this.listaRoles = new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (this.listaEmpleados == null) this.listaEmpleados = new ArrayList<>();
            if (this.listaRoles == null) this.listaRoles = new ArrayList<>();
        }
    }

    public void guardarCambios() {
        if (idEmpleadoSeleccionado == null) {
            mensajeWarn("Debe seleccionar un empleado.");
            return;
        }

        if (idRolSeleccionado == null) {
            mensajeWarn("Debe seleccionar un rol para asignar.");
            return;
        }

        try {
            // Buscar objetos por sus UUIDs
            Empleado empObj = null;
            for (Empleado e : listaEmpleados) {
                if (e.getIdEmpleado().equals(idEmpleadoSeleccionado)) {
                    empObj = e;
                    break;
                }
            }

            Rol rolObj = null;
            for (Rol r : listaRoles) {
                if (r.getIdRol().equals(idRolSeleccionado)) {
                    rolObj = r;
                    break;
                }
            }

            if (empObj == null || rolObj == null) {
                mensajeError("Empleado o Rol no encontrado.");
                return;
            }

            // Validar que el empleado esté activo
            if (!Boolean.TRUE.equals(empObj.getActivo())) {
                mensajeError("No se puede asignar rol a un empleado inactivo.");
                return;
            }

            // Validar que el rol esté activo
            if (!Boolean.TRUE.equals(rolObj.getActivo())) {
                mensajeError("No se puede asignar un rol inactivo.");
                return;
            }

            // Crear la relación EmpleadoRol
            EmpleadoRol nuevoER = new EmpleadoRol();
            nuevoER.setIdEmpleadoRol(UUID.randomUUID());
            nuevoER.setIdEmpleado(empObj);
            nuevoER.setIdRol(rolObj);
            nuevoER.setActivo(true);

            empleadoRolDAO.create(nuevoER);

            mensajeInfo("Rol " + rolObj.getNombre() + " asignado a " + empObj.getNombre() + " exitosamente.");

            // Limpiar selecciones
            this.idEmpleadoSeleccionado = null;
            this.idRolSeleccionado = null;

        } catch (Exception e) {
            e.printStackTrace();
            mensajeError("Error al guardar la asignación: " + e.getMessage());
        }
    }

    // Mensajes auxiliares JSF
    private void mensajeInfo(String msg) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", msg));
    }

    private void mensajeWarn(String msg) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", msg));
    }

    private void mensajeError(String msg) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", msg));
    }

    // Getters y Setters
    public List<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public List<Rol> getListaRoles() {
        return listaRoles;
    }

    public UUID getIdEmpleadoSeleccionado() {
        return idEmpleadoSeleccionado;
    }

    public void setIdEmpleadoSeleccionado(UUID idEmpleadoSeleccionado) {
        this.idEmpleadoSeleccionado = idEmpleadoSeleccionado;
    }

    public UUID getIdRolSeleccionado() {
        return idRolSeleccionado;
    }

    public void setIdRolSeleccionado(UUID idRolSeleccionado) {
        this.idRolSeleccionado = idRolSeleccionado;
    }
}