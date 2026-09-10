/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.UUID;
import java.util.UUID;

/**
 *
 * @author brandon
 */
@Entity
@Table(name = "tipo_descuento")
@NamedQueries({
    @NamedQuery(name = "TipoDescuento.findAll", query = "SELECT t FROM TipoDescuento t"),
    @NamedQuery(name = "TipoDescuento.findByNombre", query = "SELECT t FROM TipoDescuento t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TipoDescuento.findByActivo", query = "SELECT t FROM TipoDescuento t WHERE t.activo = :activo"),
    @NamedQuery(name = "TipoDescuento.findByDescuentoMaximo", query = "SELECT t FROM TipoDescuento t WHERE t.descuentoMaximo = :descuentoMaximo"),
    @NamedQuery(name = "TipoDescuento.findByObservaciones", query = "SELECT t FROM TipoDescuento t WHERE t.observaciones = :observaciones")})
public class TipoDescuento implements Serializable {

    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 2147483647)
    @Column(name = "observaciones")
    private String observaciones;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_tipo_descuento")
    private UUID idTipoDescuento;
    @Column(name = "activo")
    private Boolean activo;
    @Column(name = "descuento_maximo")
    private Integer descuentoMaximo;

    public TipoDescuento() {
    }

    public TipoDescuento(UUID idTipoDescuento) {
        this.idTipoDescuento = idTipoDescuento;
    }

    public UUID getIdTipoDescuento() {
        return idTipoDescuento;
    }

    public void setIdTipoDescuento(UUID idTipoDescuento) {
        this.idTipoDescuento = idTipoDescuento;
    }


    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Integer getDescuentoMaximo() {
        return descuentoMaximo;
    }

    public void setDescuentoMaximo(Integer descuentoMaximo) {
        this.descuentoMaximo = descuentoMaximo;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoDescuento != null ? idTipoDescuento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoDescuento)) {
            return false;
        }
        TipoDescuento other = (TipoDescuento) object;
        if ((this.idTipoDescuento == null && other.idTipoDescuento != null) || (this.idTipoDescuento != null && !this.idTipoDescuento.equals(other.idTipoDescuento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.TipoDescuento[ idTipoDescuento=" + idTipoDescuento + " ]";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void setIdDescuento(UUID randomUUID) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
