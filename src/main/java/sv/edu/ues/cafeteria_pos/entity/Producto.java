/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.cafeteria_pos.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author diego
 */
@Entity
@Table(name = "producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
    @NamedQuery(name = "Producto.findByNombre", query = "SELECT p FROM Producto p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Producto.findByActivo", query = "SELECT p FROM Producto p WHERE p.activo = :activo"),
    @NamedQuery(name = "Producto.findByComentarios", query = "SELECT p FROM Producto p WHERE p.comentarios = :comentarios")})
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "id_producto")
    private Object idProducto;
    @Size(max = 155)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "activo")
    private Boolean activo;
    @Size(max = 2147483647)
    @Column(name = "comentarios")
    private String comentarios;
    @OneToMany(mappedBy = "idProducto")
    private Collection<ProductoCaracteristica> productoCaracteristicaCollection;
    @OneToMany(mappedBy = "idProducto")
    private Collection<OrdenProducto> ordenProductoCollection;
    @OneToMany(mappedBy = "idProducto")
    private Collection<DescuentoProducto> descuentoProductoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto")
    private Collection<ProductoTipoProducto> productoTipoProductoCollection;

    public Producto() {
    }

    public Producto(Object idProducto) {
        this.idProducto = idProducto;
    }

    public Object getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Object idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    @XmlTransient
    public Collection<ProductoCaracteristica> getProductoCaracteristicaCollection() {
        return productoCaracteristicaCollection;
    }

    public void setProductoCaracteristicaCollection(Collection<ProductoCaracteristica> productoCaracteristicaCollection) {
        this.productoCaracteristicaCollection = productoCaracteristicaCollection;
    }

    @XmlTransient
    public Collection<OrdenProducto> getOrdenProductoCollection() {
        return ordenProductoCollection;
    }

    public void setOrdenProductoCollection(Collection<OrdenProducto> ordenProductoCollection) {
        this.ordenProductoCollection = ordenProductoCollection;
    }

    @XmlTransient
    public Collection<DescuentoProducto> getDescuentoProductoCollection() {
        return descuentoProductoCollection;
    }

    public void setDescuentoProductoCollection(Collection<DescuentoProducto> descuentoProductoCollection) {
        this.descuentoProductoCollection = descuentoProductoCollection;
    }

    @XmlTransient
    public Collection<ProductoTipoProducto> getProductoTipoProductoCollection() {
        return productoTipoProductoCollection;
    }

    public void setProductoTipoProductoCollection(Collection<ProductoTipoProducto> productoTipoProductoCollection) {
        this.productoTipoProductoCollection = productoTipoProductoCollection;
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
        return "sv.edu.ues.cafeteria_pos.entity.Producto[ idProducto=" + idProducto + " ]";
    }
    
}
