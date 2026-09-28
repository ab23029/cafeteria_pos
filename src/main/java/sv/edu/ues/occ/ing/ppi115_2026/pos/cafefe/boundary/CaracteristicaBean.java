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

@Named("caracteristicaBean")
@ViewScoped
public class CaracteristicaBean implements Serializable {
@Inject
private CaracteristicaDAOInterface caracteristicaDAO;

    private Caracteristica registro;
    private List<Caracteristica> lista;

    @PostConstruct
    public void init() {
        this.registro = new Caracteristica();
        this.lista = caracteristicaDAO.findRange(0, 100);
    }

    public void guardar() {
        if (this.registro.getIdCaracteristica() == null) {
            this.registro.setIdCaracteristica(UUID.randomUUID());
            caracteristicaDAO.create(this.registro);
        } else {
            caracteristicaDAO.edit(this.registro);
        }
        this.init();
    }

    public Caracteristica getRegistro() { return registro; }
    public void setRegistro(Caracteristica registro) { this.registro = registro; }
    public List<Caracteristica> getLista() { return lista; }
}
