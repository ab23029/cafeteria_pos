package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.boundary;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.OrdenProductoDAO;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.OrdenProducto;

@Named("ordenProductoFrm")
@ViewScoped
public class OrdenProductoFrm implements Serializable {

    @Inject
    private OrdenProductoDAO ordenProductoDAO;

    private OrdenProducto registroSeleccionado;
    private LazyDataModel<OrdenProducto> modelo;

    @PostConstruct
    public void init() {
        nuevoRegistro();
        this.modelo = new LazyDataModel<OrdenProducto>() {
            @Override
            public String getRowKey(OrdenProducto ordenProducto) {
                if (ordenProducto != null && ordenProducto.getIdOrdenProducto() != null) {
                    return ordenProducto.getIdOrdenProducto().toString();
                }
                return null;
            }

            @Override
            public OrdenProducto getRowData(String rowKey) {
                if (rowKey != null && !rowKey.trim().isEmpty()) {
                    try {
                        return ordenProductoDAO.buscarPorId(rowKey);
                    } catch (Exception e) {
                        return null;
                    }
                }
                return null;
            }

            @Override
            public List<OrdenProducto> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
                List<OrdenProducto> lista = ordenProductoDAO.findRange(first, pageSize);
                setRowCount(ordenProductoDAO.count());
                return lista;
            }

            @Override
            public int count(Map<String, FilterMeta> filterBy) {
                return ordenProductoDAO.count();
            }
        };
    }

    public void nuevoRegistro() {
        this.registroSeleccionado = new OrdenProducto();
        this.registroSeleccionado.setIdOrdenProducto(UUID.randomUUID());
    }

    public void btnGuardarHandler() {
        try {
            if (registroSeleccionado.getIdOrdenProducto() == null) {
                registroSeleccionado.setIdOrdenProducto(UUID.randomUUID());
            }

            boolean existe = false;
            if (registroSeleccionado.getIdOrdenProducto() != null) {
                OrdenProducto op = ordenProductoDAO.buscarPorId(registroSeleccionado.getIdOrdenProducto().toString());
                if (op != null) {
                    existe = true;
                }
            }

            if (!existe) {
                ordenProductoDAO.crear(registroSeleccionado);
                mostrarMensaje(FacesMessage.SEVERITY_INFO, "Éxito", "Orden-Producto registrada correctamente");
            } else {
                ordenProductoDAO.modificar(registroSeleccionado);
                mostrarMensaje(FacesMessage.SEVERITY_INFO, "Éxito", "Orden-Producto modificada correctamente");
            }
            nuevoRegistro();
        } catch (Exception e) {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo guardar la información");
        }
    }

    public void btnEliminarHandler() {
        try {
            ordenProductoDAO.eliminar(registroSeleccionado);
            mostrarMensaje(FacesMessage.SEVERITY_INFO, "Éxito", "Registro eliminado correctamente");
            nuevoRegistro();
        } catch (Exception e) {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar el registro");
        }
    }

    private void mostrarMensaje(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }

    // Getters y Setters
    public OrdenProducto getRegistroSeleccionado() { return registroSeleccionado; }
    public void setRegistroSeleccionado(OrdenProducto registroSeleccionado) { this.registroSeleccionado = registroSeleccionado; }
    public LazyDataModel<OrdenProducto> getModelo() { return modelo; }
}