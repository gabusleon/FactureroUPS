package ec.edu.ups.moviles.Facturero.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
public class FacturaCabecera implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty
    private long id;

    @Column(name = "fecha_emision", nullable = false)
    @JsonProperty
    private LocalDate fechaDeEmision;

    @JsonProperty
    private BigDecimal subtotal;

    @JsonProperty
    private BigDecimal impuesto;

    @Column(nullable = false)
    @JsonProperty
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonProperty
    private Cliente cliente;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "facturaCabecera", cascade = CascadeType.ALL)
    @JsonProperty
    private List<FacturaDetalle> detalles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getFechaDeEmision() {
        return fechaDeEmision;
    }

    public void setFechaDeEmision(LocalDate fechaDeEmision) {
        this.fechaDeEmision = fechaDeEmision;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(BigDecimal impuesto) {
        this.impuesto = impuesto;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<FacturaDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<FacturaDetalle> detalles) {
        this.detalles = detalles;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}
