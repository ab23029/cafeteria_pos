package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.boundary;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.FacturaDAO;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Factura;

@Named("facturaFrm")
@ViewScoped
public class FacturaFrm implements Serializable {

    @Inject
    private FacturaDAO facturaDAO;

    private Factura registroSeleccionado;
    private LazyDataModel<Factura> modelo;

    @PostConstruct
    public void init() {
        nuevoRegistro();
        this.modelo = new LazyDataModel<Factura>() {
            @Override
            public String getRowKey(Factura factura) {
                if (factura != null && factura.getIdFactura() != null) {
                    return factura.getIdFactura().toString();
                }
                return null;
            }

            @Override
            public Factura getRowData(String rowKey) {
                if (rowKey != null && !rowKey.trim().isEmpty()) {
                    try {
                        return facturaDAO.buscarPorId(rowKey);
                    } catch (Exception e) {
                        return null;
                    }
                }
                return null;
            }

            @Override
            public List<Factura> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
                List<Factura> lista = facturaDAO.findRange(first, pageSize);
                setRowCount(facturaDAO.count());
                return lista;
            }

            @Override
            public int count(Map<String, FilterMeta> filterBy) {
                return facturaDAO.count();
            }
        };
    }

    public void nuevoRegistro() {
        this.registroSeleccionado = new Factura();
        this.registroSeleccionado.setIdFactura(UUID.randomUUID());
        this.registroSeleccionado.setFechaFacturacion(new Date());
        this.registroSeleccionado.setEstado("PENDIENTE");
    }

    public void btnGuardarHandler() {
        try {
            if (registroSeleccionado.getIdFactura() == null) {
                registroSeleccionado.setIdFactura(UUID.randomUUID());
            }

            boolean existe = false;
            if (registroSeleccionado.getIdFactura() != null) {
                Factura f = facturaDAO.buscarPorId(registroSeleccionado.getIdFactura().toString());
                if (f != null) {
                    existe = true;
                }
            }

            if (!existe) {
                facturaDAO.crear(registroSeleccionado);
                mostrarMensaje(FacesMessage.SEVERITY_INFO, "Éxito", "Factura creada correctamente");
            } else {
                facturaDAO.modificar(registroSeleccionado);
                mostrarMensaje(FacesMessage.SEVERITY_INFO, "Éxito", "Factura modificada correctamente");
            }
            nuevoRegistro();
        } catch (Exception e) {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo guardar la factura");
        }
    }

    public void btnEliminarHandler() {
        try {
            facturaDAO.eliminar(registroSeleccionado);
            mostrarMensaje(FacesMessage.SEVERITY_INFO, "Éxito", "Factura eliminada correctamente");
            nuevoRegistro();
        } catch (Exception e) {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar la factura");
        }
    }

    private void mostrarMensaje(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }

    // Getters y Setters
    public Factura getRegistroSeleccionado() { return registroSeleccionado; }
    public void setRegistroSeleccionado(Factura registroSeleccionado) { this.registroSeleccionado = registroSeleccionado; }
    public LazyDataModel<Factura> getModelo() { return modelo; }
}