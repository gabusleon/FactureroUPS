package ec.edu.ups.moviles.Facturero.entidades.peticiones.cliente;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EditarCliente {

    @JsonProperty
    private long id;
    @JsonProperty
    private String tipoIdentificacion;
    @JsonProperty
    private String identificacionNumero;
    @JsonProperty
    private String nombre;
    @JsonProperty
    private String direccion;
    @JsonProperty
    private String telefono;
    @JsonProperty
    private String correoElectronico;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
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
