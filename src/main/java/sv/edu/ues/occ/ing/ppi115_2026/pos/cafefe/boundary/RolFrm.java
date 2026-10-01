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
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.RolDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Rol;

@Named(value = "rolFrm")
@ViewScoped
public class RolFrm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private RolDAOInterface rolDAO;

    private List<Rol> listaRol;
    private Rol registroSeleccionado;

    @PostConstruct
    public void init() {
        nuevoRegistro();
        cargarDatos();
    }

    public void nuevoRegistro() {
        this.registroSeleccionado = new Rol();
        this.registroSeleccionado.setActivo(true);
    }

    public void cargarDatos() {
        try {
            this.listaRol = rolDAO.findRange(0, 100);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al cargar los roles"));
        }
    }

    public void guardar() {
        try {
            if (registroSeleccionado.getNombre() == null || registroSeleccionado.getNombre().trim().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Atención", "El nombre del rol es obligatorio"));
                return;
            }

            if (registroSeleccionado.getIdRol() == null) {
                registroSeleccionado.setIdRol(UUID.randomUUID());
                rolDAO.create(registroSeleccionado);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Rol registrado correctamente"));
            } else {
                rolDAO.edit(registroSeleccionado);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Rol actualizado correctamente"));
            }
            nuevoRegistro();
            cargarDatos();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al guardar el rol"));
        }
    }

    public void seleccionar(Rol r) {
        this.registroSeleccionado = r;
    }

    // Getters y Setters
    public List<Rol> getListaRol() { return listaRol; }
    public Rol getRegistroSeleccionado() { return registroSeleccionado; }
    public void setRegistroSeleccionado(Rol registroSeleccionado) { this.registroSeleccionado = registroSeleccionado; }
}