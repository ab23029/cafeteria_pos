package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "producto_tipo_producto", schema = "public")
@NamedQueries({
    @NamedQuery(name = "ProductoTipoProducto.findAll", query = "SELECT p FROM ProductoTipoProducto p"),
    @NamedQuery(name = "ProductoTipoProducto.findByIdProductoTipoProducto", query = "SELECT p FROM ProductoTipoProducto p WHERE p.idProductoTipoProducto = :idProductoTipoProducto"),
    @NamedQuery(name = "ProductoTipoProducto.findByFechaCreacion", query = "SELECT p FROM ProductoTipoProducto p WHERE p.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "ProductoTipoProducto.findByObservaciones", query = "SELECT p FROM ProductoTipoProducto p WHERE p.observaciones = :observaciones")
})
public class ProductoTipoProducto implements Serializable {

    private static final long serialVersionUID = 1L;

@Id
@NotNull
@Column(name = "id_producto_tipo_producto", nullable = false)
private UUID idProductoTipoProducto;

    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @Size(max = 2147483647)
    @Column(name = "observaciones")
    private String observaciones;

  @JoinColumn(name = "id_producto", referencedColumnName = "id_producto")
@ManyToOne
private Producto idProducto;
  
    @JoinColumn(name = "id_tipo_producto", referencedColumnName = "id_tipo_producto")
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoProducto idTipoProducto;

    public ProductoTipoProducto() {
    }

    public ProductoTipoProducto(UUID idProductoTipoProducto) {
        this.idProductoTipoProducto = idProductoTipoProducto;
    }

    public UUID getIdProductoTipoProducto() {
        return idProductoTipoProducto;
    }

    public void setIdProductoTipoProducto(UUID idProductoTipoProducto) {
        this.idProductoTipoProducto = idProductoTipoProducto;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

 public Producto getIdProducto() {
    return idProducto;
}

public void setIdProducto(Producto idProducto) {
    this.idProducto = idProducto;
}

    public TipoProducto getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(TipoProducto idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.idProductoTipoProducto);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ProductoTipoProducto other = (ProductoTipoProducto) obj;
        return Objects.equals(this.idProductoTipoProducto, other.idProductoTipoProducto);
    }

    @Override
    public String toString() {
        return "sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.ProductoTipoProducto[ idProductoTipoProducto=" + idProductoTipoProducto + " ]";
    }
}