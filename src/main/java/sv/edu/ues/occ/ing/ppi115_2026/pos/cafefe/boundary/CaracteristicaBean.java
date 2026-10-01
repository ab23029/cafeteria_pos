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
import java.util.stream.Collectors;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.CaracteristicaDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.TipoCaracteristicaDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Caracteristica;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.TipoCaracteristica;

@Named("caracteristicaBean")
@ViewScoped
public class CaracteristicaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private CaracteristicaDAOInterface caracteristicaDAO;

    @Inject
    private TipoCaracteristicaDAOInterface tipoCaracteristicaDAO;

    private Caracteristica registro;
    private List<Caracteristica> lista;

    @PostConstruct
    public void init() {
        limpiar();
        cargarLista();
    }

    public void cargarLista() {
        if (caracteristicaDAO != null) {
            this.lista = caracteristicaDAO.findRange(0, 100);
        }
    }

    public void limpiar() {
        this.registro = new Caracteristica();
        this.registro.setActivo(true);
    }

    public List<TipoCaracteristica> getTiposCaracteristicaActivos() {
        if (tipoCaracteristicaDAO != null) {
            return tipoCaracteristicaDAO.findRange(0, 100)
                    .stream()
                    .filter(t -> Boolean.TRUE.equals(t.getActivo()))
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    public void guardar() {
        try {
            if (registro != null && caracteristicaDAO != null) {
                if (registro.getIdCaracteristica() == null) {
                    registro.setIdCaracteristica(UUID.randomUUID());
                    caracteristicaDAO.create(registro);
                    mostrarMensaje("Éxito", "Característica creada correctamente");
                } else {
                    caracteristicaDAO.edit(registro);
                    mostrarMensaje("Éxito", "Característica actualizada correctamente");
                }
                limpiar();
                cargarLista();
            }
        } catch (Exception e) {
            mostrarMensaje("Error", "No se pudo guardar la característica: " + e.getMessage());
        }
    }

    private void mostrarMensaje(String resumen, String detalle) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, resumen, detalle));
    }

    public Caracteristica getRegistro() { return registro; }
    public void setRegistro(Caracteristica registro) { this.registro = registro; }

    public List<Caracteristica> getLista() { return lista; }
    public void setLista(List<Caracteristica> lista) { this.lista = lista; }
}