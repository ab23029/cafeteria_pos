package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class ProductoTipoProductoPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "id_producto_tipo_producto")
    private UUID idProductoTipoProducto;

    @Basic(optional = false)
    @NotNull
    @Column(name = "id_producto")
    private UUID idProducto;

    public ProductoTipoProductoPK() {
    }

    public ProductoTipoProductoPK(UUID idProductoTipoProducto, UUID idProducto) {
        this.idProductoTipoProducto = idProductoTipoProducto;
        this.idProducto = idProducto;
    }

    public UUID getIdProductoTipoProducto() {
        return idProductoTipoProducto;
    }

    public void setIdProductoTipoProducto(UUID idProductoTipoProducto) {
        this.idProductoTipoProducto = idProductoTipoProducto;
    }

    public UUID getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(UUID idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProductoTipoProducto != null ? idProductoTipoProducto.hashCode() : 0);
        hash += (idProducto != null ? idProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ProductoTipoProductoPK)) {
            return false;
        }
        ProductoTipoProductoPK other = (ProductoTipoProductoPK) object;
        if ((this.idProductoTipoProducto == null && other.idProductoTipoProducto != null) || (this.idProductoTipoProducto != null && !this.idProductoTipoProducto.equals(other.idProductoTipoProducto))) {
            return false;
        }
        if ((this.idProducto == null && other.idProducto != null) || (this.idProducto != null && !this.idProducto.equals(other.idProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.entity.ProductoTipoProductoPK[ idProductoTipoProducto=" + idProductoTipoProducto + ", idProducto=" + idProducto + " ]";
    }
}