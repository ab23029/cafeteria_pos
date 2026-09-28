package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.boundary;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.CaracteristicaDAO;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.CaracteristicaDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Caracteristica;

@Named("caracteristicaFrm")
@ViewScoped
public class CaracteristicaFrm implements Serializable {

   @Inject
private CaracteristicaDAOInterface caracteristicaDAO;
    private Caracteristica registroSeleccionado;
    private List<Caracteristica> registros;

    @PostConstruct
    public void init() {
        this.registroSeleccionado = new Caracteristica();
        this.registros = caracteristicaDAO.findRange(0, 100);
    }

    public void btnGuardarHandler() {
        if (this.registroSeleccionado.getIdCaracteristica() == null) {
            this.registroSeleccionado.setIdCaracteristica(UUID.randomUUID());
            caracteristicaDAO.create(this.registroSeleccionado);
        } else {
            caracteristicaDAO.edit(this.registroSeleccionado);
        }
        this.init();
    }

    public Caracteristica getRegistroSeleccionado() { return registroSeleccionado; }
    public void setRegistroSeleccionado(Caracteristica registroSeleccionado) { this.registroSeleccionado = registroSeleccionado; }
    public List<Caracteristica> getRegistros() { return registros; }
}
