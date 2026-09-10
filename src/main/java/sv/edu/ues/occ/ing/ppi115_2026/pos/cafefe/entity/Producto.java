/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.UUID;
import java.util.UUID;
import java.util.List;

/**
 *
 * @author brandon
 */
@Entity
@Table(name = "producto")
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
    @NamedQuery(name = "Producto.findByNombre", query = "SELECT p FROM Producto p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Producto.findByActivo", query = "SELECT p FROM Producto p WHERE p.activo = :activo"),
    @NamedQuery(name = "Producto.findByComentarios", query = "SELECT p FROM Producto p WHERE p.comentarios = :comentarios")})
public class Producto implements Serializable {

    @Size(max = 155)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 2147483647)
    @Column(name = "comentarios")
    private String comentarios;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_producto")
    private UUID idProducto;
    @Column(name = "activo")
    private Boolean activo;
    @OneToMany(mappedBy = "idProducto", fetch = FetchType.LAZY)
    private List<ProductoCaracteristica> productoCaracteristicaList;
    @OneToMany(mappedBy = "idProducto", fetch = FetchType.LAZY)
    private List<OrdenProducto> ordenProductoList;
    @OneToMany(mappedBy = "idProducto", fetch = FetchType.LAZY)
    private List<DescuentoProducto> descuentoProductoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto", fetch = FetchType.LAZY)
    private List<ProductoTipoProducto> productoTipoProductoList;

    public Producto() {
    }

    public Producto(UUID idProducto) {
        this.idProducto = idProducto;
    }

    public UUID getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(UUID idProducto) {
        this.idProducto = idProducto;
    }


    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }


    public List<ProductoCaracteristica> getProductoCaracteristicaList() {
        return productoCaracteristicaList;
    }

    public void setProductoCaracteristicaList(List<ProductoCaracteristica> productoCaracteristicaList) {
        this.productoCaracteristicaList = productoCaracteristicaList;
    }

    public List<OrdenProducto> getOrdenProductoList() {
        return ordenProductoList;
    }

    public void setOrdenProductoList(List<OrdenProducto> ordenProductoList) {
        this.ordenProductoList = ordenProductoList;
    }

    public List<DescuentoProducto> getDescuentoProductoList() {
        return descuentoProductoList;
    }

    public void setDescuentoProductoList(List<DescuentoProducto> descuentoProductoList) {
        this.descuentoProductoList = descuentoProductoList;
    }

    public List<ProductoTipoProducto> getProductoTipoProductoList() {
        return productoTipoProductoList;
    }

    public void setProductoTipoProductoList(List<ProductoTipoProducto> productoTipoProductoList) {
        this.productoTipoProductoList = productoTipoProductoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProducto != null ? idProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.idProducto == null && other.idProducto != null) || (this.idProducto != null && !this.idProducto.equals(other.idProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.Producto[ idProducto=" + idProducto + " ]";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
    
}
