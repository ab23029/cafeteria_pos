package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.beans;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.control.ProductoDAOInterface;
import sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.*;

@Named("productoBean")
@ViewScoped
public class ProductoBean implements Serializable {

    @Inject
    private ProductoDAOInterface dao;

    // Atributo principal de la entidad
    private Producto registro = new Producto();

    // Variables seleccionadas en la interfaz (UI)
    private TipoProducto tipoProductoSeleccionado;
    private Caracteristica caracteristicaSeleccionada;
    private String valorCaracteristica;
    private Descuento descuentoSeleccionado;
    private Integer valorDescuento;

    public List<Producto> getLista() {
        return dao.findRange(0, 100);
    }

    /**
     * Método principal de guardado con todas las validaciones de negocio.
     */
    public void guardar() {
        if (registro == null) return;

        // --- REGLA 1: Validar Tipo de Producto Activo ---
        if (!esTipoProductoValido()) {
            mensajeError("Debe seleccionar un tipo de producto activo.");
            return;
        }

        // --- REGLA 2: Validar Expresión Regular de Característica ---
        if (!esCaracteristicaValida()) {
            mensajeError("El valor ingresado no cumple con la expresión regular requerida.");
            return;
        }

        // --- REGLA 3: Validar Descuento (Monto y Vigencia) ---
        if (!esDescuentoValido()) {
            return; // El mensaje de error específico se emite dentro del método
        }

        // --- PROCESO DE GUARDADO ---
        prepararYGuardarProducto();
        mensajeInfo("Producto guardado correctamente.");
        limpiarFormulario();
    }

    // =========================================================================
    // MÉTODOS DE VALIDACIÓN (FÁCILES DE ESTUDIAR)
    // =========================================================================

    private boolean esTipoProductoValido() {
        return tipoProductoSeleccionado != null && Boolean.TRUE.equals(tipoProductoSeleccionado.getActivo());
    }

    private boolean esCaracteristicaValida() {
        if (caracteristicaSeleccionada == null || caracteristicaSeleccionada.getIdTipoCaracteristica() == null) {
            return true; // Es opcional
        }

        String regex = caracteristicaSeleccionada.getIdTipoCaracteristica().getExpresionRegular();
        if (regex == null || regex.isBlank()) {
            return true; // No tiene restricción de expresión regular
        }

        return valorCaracteristica != null && Pattern.matches(regex, valorCaracteristica);
    }

    private boolean esDescuentoValido() {
        if (descuentoSeleccionado == null) return true; // Descuento opcional

        TipoTipoDescuento(); // Referencia implícita

        // 3.1 Validar límite de monto máximo
        TipoDescuento td = descuentoSeleccionado.getIdTipoDescuento();
        if (td != null && td.getDescuentoMaximo() != null && valorDescuento != null) {
            if (valorDescuento > td.getDescuentoMaximo()) {
                mensajeError("El descuento " + valorDescuento + "% supera el máximo permitido (" + td.getDescuentoMaximo() + "%).");
                return false;
            }
        }

        // 3.2 Validar fechas de vigencia
        Date hoy = new Date();
        if (descuentoSeleccionado.getFechaDesde() != null && hoy.before(descuentoSeleccionado.getFechaDesde())) {
            mensajeError("El descuento seleccionado aún no ha iniciado su vigencia.");
            return false;
        }
        if (descuentoSeleccionado.getFechaHasta() != null && hoy.after(descuentoSeleccionado.getFechaHasta())) {
            mensajeError("El descuento seleccionado ya está vencido.");
            return false;
        }

        return true;
    }

    private void TipoTipoDescuento() {
        // Método auxiliar de estructura
    }

    // =========================================================================
    // CONSTRUCCIÓN DE REGISTRO
    // =========================================================================

    private void prepararYGuardarProducto() {
        if (registro.getIdProducto() == null) {
            registro.setIdProducto(UUID.randomUUID());
        }
        if (registro.getPrecioSugerido() == null) {
            registro.setPrecioSugerido(BigDecimal.ZERO);
        }

        // Asignar Tipo de Producto
        ProductoTipoProducto ptp = new ProductoTipoProducto();
     ptp.setIdProductoTipoProducto(UUID.randomUUID());
        ptp.setIdProducto(registro);
        ptp.setIdTipoProducto(tipoProductoSeleccionado);
        ptp.setFechaCreacion(new Date());
        registro.setProductoTipoProductoList(List.of(ptp));

        // Asignar Característica (si existe)
        if (caracteristicaSeleccionada != null) {
            ProductoCaracteristica pc = new ProductoCaracteristica();
            pc.setIdProductoCaracteristica(UUID.randomUUID());
            pc.setIdProducto(registro);
            pc.setIdCaracteristica(caracteristicaSeleccionada);
            pc.setValor(valorCaracteristica);
            registro.setProductoCaracteristicaList(List.of(pc));
        }

        // Asignar Descuento (si existe)
        if (descuentoSeleccionado != null) {
            DescuentoProducto dp = new DescuentoProducto();
            dp.setIdDescuentoProducto(UUID.randomUUID());
            dp.setIdProducto(registro);
            dp.setIdDescuento(descuentoSeleccionado);
            dp.setValor(valorDescuento);
            dp.setFechaDesde(descuentoSeleccionado.getFechaDesde());
            dp.setFechaHasta(descuentoSeleccionado.getFechaHasta());
            registro.setDescuentoProductoList(List.of(dp));
        }

        dao.create(registro);
    }

    private void limpiarFormulario() {
        this.registro = new Producto();
        this.tipoProductoSeleccionado = null;
        this.caracteristicaSeleccionada = null;
        this.valorCaracteristica = null;
        this.descuentoSeleccionado = null;
        this.valorDescuento = null;
    }

    // Mensajes informativos en JSF
    private void mensajeError(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", msg));
    }

    private void mensajeInfo(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", msg));
    }

    // --- GETTERS Y SETTERS ---
    public Producto getRegistro() { return registro; }
    public void setRegistro(Producto registro) { this.registro = registro; }
    public TipoProducto getTipoProductoSeleccionado() { return tipoProductoSeleccionado; }
    public void setTipoProductoSeleccionado(TipoProducto tipoProductoSeleccionado) { this.tipoProductoSeleccionado = tipoProductoSeleccionado; }
    public Caracteristica getCaracteristicaSeleccionada() { return caracteristicaSeleccionada; }
    public void setCaracteristicaSeleccionada(Caracteristica caracteristicaSeleccionada) { this.caracteristicaSeleccionada = caracteristicaSeleccionada; }
    public String getValorCaracteristica() { return valorCaracteristica; }
    public void setValorCaracteristica(String valorCaracteristica) { this.valorCaracteristica = valorCaracteristica; }
    public Descuento getDescuentoSeleccionado() { return descuentoSeleccionado; }
    public void setDescuentoSeleccionado(Descuento descuentoSeleccionado) { this.descuentoSeleccionado = descuentoSeleccionado; }
    public Integer getValorDescuento() { return valorDescuento; }
    public void setValorDescuento(Integer valorDescuento) { this.valorDescuento = valorDescuento; }
}