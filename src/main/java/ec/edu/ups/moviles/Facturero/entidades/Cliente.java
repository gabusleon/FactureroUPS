package ec.edu.ups.moviles.Facturero.entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ec.edu.ups.moviles.Facturero.entidades.tipos.TipoIdentificacion;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_identificacion", nullable = false)
    @JsonProperty
    private TipoIdentificacion tipoIdentificacion;

    @Column(name = "identificacion_numero", nullable = false, unique = true)
    @JsonProperty
    private String identificacionNumero;

    @Column(length = 250, nullable = false)
    @JsonProperty
    @Nationalized
    private String nombre;

    @Column(nullable = false)
    @JsonProperty
    private String direccion;

    @Column(nullable = false)
    @JsonProperty
    private String telefono;

    @Column(name = "correo_electronico", length = 100, nullable = false)
    @JsonProperty
    private String correoElectronico;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<FacturaCabecera> facturas;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TipoIdentificacion getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public List<FacturaCabecera> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<FacturaCabecera> facturas) {
        this.facturas = facturas;
    }

    public String getIdentificacionNumero() {
        return identificacionNumero;
    }

    public void setIdentificacionNumero(String identificacionNumero) {
        this.identificacionNumero = identificacionNumero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
}
