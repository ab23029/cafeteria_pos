package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.boundary;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.OrdenDAO;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Orden;

@Named("ordenBean")
@ViewScoped
public class OrdenBean implements Serializable {

    @Inject
    private OrdenDAO ordenDAO;

    private Orden registro;
    private List<Orden> lista;

    @PostConstruct
    public void init() {
        this.registro = new Orden();
        this.lista = ordenDAO.obtenerTodos();
    }

    public void guardar() {
        if (this.registro.getIdOrden() == null) {
            this.registro.setIdOrden(UUID.randomUUID());
            ordenDAO.crear(this.registro);
        } else {
            ordenDAO.modificar(this.registro);
        }
        this.init();
    }

    public Orden getRegistro() { return registro; }
    public void setRegistro(Orden registro) { this.registro = registro; }
    public List<Orden> getLista() { return lista; }
}
