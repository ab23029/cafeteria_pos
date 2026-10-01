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

    private List<Rol> rolesActivos;

    private List<EmpleadoRol> relacionesActuales;

    private List<Rol> todosLosRoles;

    @PostConstruct

    public void init() {

        cargarListas();

    }

    public void cargarListas() {

        try {

            this.listaEmpleados = empleadoDAO.findRange(0, 100);

            this.todosLosRoles = rolDAO.findRange(0, 100);

            if (this.todosLosRoles == null) {

                this.todosLosRoles = new ArrayList<>();

            }

            if (this.rolesPickList == null) {

                this.rolesPickList = new DualListModel<>(new ArrayList<>(), new ArrayList<>());

            }

        } catch (Exception e) {

            e.printStackTrace();

            if (this.listaEmpleados == null) {

                this.listaEmpleados = new ArrayList<>();

            }

            if (this.todosLosRoles == null) {

                this.todosLosRoles = new ArrayList<>();

            }

        }

    }

    public void cargarRolesDelEmpleado() {

        if (idEmpleadoSeleccionado == null) {

            this.rolesPickList = new DualListModel<>(new ArrayList<>(), new ArrayList<>());

            return;

        }

        cargarListas();

        try {

            relacionesActuales = empleadoRolDAO.findByEmpleado(idEmpleadoSeleccionado);

        } catch (Exception e) {

            relacionesActuales = new ArrayList<>();

        }

        // 1. Obtener los roles activos asignados al empleado desde la BD
        List<String> asignados = new ArrayList<>();

        if (relacionesActuales != null) {

            for (EmpleadoRol er : relacionesActuales) {

                if (Boolean.TRUE.equals(er.getActivo()) && er.getIdRol() != null && er.getIdRol().getNombre() != null) {

                    String nombreRol = er.getIdRol().getNombre().trim();

                    if (!Boolean.TRUE.equals(er.getIdRol().getActivo())) {

                        nombreRol += " (Inactivo)";

                    }

                    asignados.add(nombreRol);

                }

            }

        }

        // 2. Filtrar roles disponibles
        List<String> disponibles = new ArrayList<>();

        if (todosLosRoles != null) {

            for (Rol r : todosLosRoles) {

                if (r != null && r.getNombre() != null) {

                    String nombreBase = r.getNombre().trim();

                    String etiquetaRol = nombreBase;

                    if (!Boolean.TRUE.equals(r.getActivo())) {

                        etiquetaRol += " (Inactivo)";

                    }

                    // Verificamos si ya está asignado (comparación insensible a mayúsculas/minúsculas)
                    boolean yaEstaAsignado = false;

                    for (String asig : asignados) {

                        if (asig.equalsIgnoreCase(etiquetaRol)) {

                            yaEstaAsignado = true;

                            break;

                        }

                    }

                    if (!yaEstaAsignado) {

                        disponibles.add(etiquetaRol);

                    }

                }

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

            if (empObj == null) {

                return;

            }

            // Limpiar la etiqueta "(Inactivo)" y quitar espacios extras
            List<String> seleccionadosOriginales = rolesPickList.getTarget();

            List<String> seleccionadosLimpios = new ArrayList<>();

            for (String s : seleccionadosOriginales) {

                String limpio = s.replace("(Inactivo)", "").trim();

                seleccionadosLimpios.add(limpio);

            }

            // Actualizar o desactivar relaciones existentes
            if (relacionesActuales != null) {

                for (EmpleadoRol er : relacionesActuales) {

                    if (er.getIdRol() != null && er.getIdRol().getNombre() != null) {

                        String nombreRolBD = er.getIdRol().getNombre().trim();

                        boolean estaSeleccionado = false;

                        for (String sel : seleccionadosLimpios) {

                            if (sel.equalsIgnoreCase(nombreRolBD)) {

                                estaSeleccionado = true;

                                break;

                            }

                        }

                        if (estaSeleccionado) {

                            er.setActivo(true);

                            empleadoRolDAO.edit(er);

                        } else {

                            er.setActivo(false);

                            empleadoRolDAO.edit(er);

                        }

                    }

                }

            }

            // Crear nuevas relaciones
            for (String nombreRol : seleccionadosLimpios) {

                boolean yaExiste = false;

                if (relacionesActuales != null) {

                    for (EmpleadoRol er : relacionesActuales) {

                        if (er.getIdRol() != null && er.getIdRol().getNombre() != null) {

                            if (nombreRol.equalsIgnoreCase(er.getIdRol().getNombre().trim())) {

                                yaExiste = true;

                                break;

                            }

                        }

                    }

                }

                if (!yaExiste) {

                    Rol rolObj = null;

                    for (Rol r : todosLosRoles) {

                        if (r.getNombre() != null && nombreRol.equalsIgnoreCase(r.getNombre().trim())) {

                            rolObj = r;

                            break;

                        }

                    }

                    if (rolObj != null) {

                        EmpleadoRol nuevoER = new EmpleadoRol();

                        nuevoER.setIdEmpleadoRol(UUID.randomUUID());

                        nuevoER.setIdEmpleado(empObj);

                        nuevoER.setIdRol(rolObj);

                        nuevoER.setActivo(true);

                        empleadoRolDAO.create(nuevoER);

                    }

                }

            }

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Asignación de roles actualizada correctamente"));

            // Recargar los roles actualizados
            cargarRolesDelEmpleado();

        } catch (Exception e) {

            e.printStackTrace();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un problema al guardar los roles"));

        }

    }

    // Getters y Setters
    public List<Empleado> getListaEmpleados() {

        return listaEmpleados;

    }

    public UUID getIdEmpleadoSeleccionado() {

        return idEmpleadoSeleccionado;

    }

    public void setIdEmpleadoSeleccionado(UUID idEmpleadoSeleccionado) {

        this.idEmpleadoSeleccionado = idEmpleadoSeleccionado;

    }

    public DualListModel<String> getRolesPickList() {

        return rolesPickList;

    }

    public void setRolesPickList(DualListModel<String> rolesPickList) {

        this.rolesPickList = rolesPickList;

    }

}
