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
import org.primefaces.model.DualListModel;
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
    private UUID idEmpleadoSeleccionado;
    
    private DualListModel<String> rolesPickList;
    private List<Rol> todosLosRoles;
    private List<EmpleadoRol> relacionesActuales;

    @PostConstruct
    public void init() {
        try {
            this.listaEmpleados = empleadoDAO.findRange(0, 100);
            this.todosLosRoles = rolDAO.findRange(0, 100);
            this.rolesPickList = new DualListModel<>(new ArrayList<>(), new ArrayList<>());
        } catch (Exception e) {
            this.listaEmpleados = new ArrayList<>();
            this.todosLosRoles = new ArrayList<>();
        }
    }

    public void cargarRolesDelEmpleado() {
        if (idEmpleadoSeleccionado == null) {
            rolesPickList = new DualListModel<>(new ArrayList<>(), new ArrayList<>());
            return;
        }

        relacionesActuales = empleadoRolDAO.findByEmpleado(idEmpleadoSeleccionado);
        
        List<String> asignados = new ArrayList<>();
        for (EmpleadoRol er : relacionesActuales) {
            if (er.getIdRol() != null && er.getIdRol().getNombre() != null) {
                asignados.add(er.getIdRol().getNombre());
            }
        }

        List<String> disponibles = new ArrayList<>();
        for (Rol r : todosLosRoles) {
            if (r.getNombre() != null && !asignados.contains(r.getNombre())) {
                disponibles.add(r.getNombre());
            }
        }

        this.rolesPickList = new DualListModel<>(disponibles, asignados);
    }

    public void guardarCambios() {
        if (idEmpleadoSeleccionado == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Seleccione un empleado primero"));
            return;
        }

        try {
            Empleado empObj = null;
            for (Empleado e : listaEmpleados) {
                if (e.getIdEmpleado().equals(idEmpleadoSeleccionado)) {
                    empObj = e;
                    break;
                }
            }

            // Eliminar asignaciones anteriores
            for (EmpleadoRol er : relacionesActuales) {
                empleadoRolDAO.remove(er);
            }

            // Crear asignaciones según los elementos en Target
            for (String nombreRol : rolesPickList.getTarget()) {
                Rol rolObj = null;
                for (Rol r : todosLosRoles) {
                    if (nombreRol.equals(r.getNombre())) {
                        rolObj = r;
                        break;
                    }
                }

                if (rolObj != null && empObj != null) {
                    EmpleadoRol nuevoER = new EmpleadoRol();
                    nuevoER.setIdEmpleadoRol(UUID.randomUUID());
                    nuevoER.setIdEmpleado(empObj);
                    nuevoER.setIdRol(rolObj);
                    nuevoER.setActivo(true);
                    empleadoRolDAO.create(nuevoER);
                }
            }

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Asignación de roles actualizada correctamente"));
            cargarRolesDelEmpleado();

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un problema al guardar los roles"));
        }
    }

    // Getters y Setters
    public List<Empleado> getListaEmpleados() { return listaEmpleados; }
    public UUID getIdEmpleadoSeleccionado() { return idEmpleadoSeleccionado; }
    public void setIdEmpleadoSeleccionado(UUID idEmpleadoSeleccionado) { this.idEmpleadoSeleccionado = idEmpleadoSeleccionado; }
    public DualListModel<String> getRolesPickList() { return rolesPickList; }
    public void setRolesPickList(DualListModel<String> rolesPickList) { this.rolesPickList = rolesPickList; }
}