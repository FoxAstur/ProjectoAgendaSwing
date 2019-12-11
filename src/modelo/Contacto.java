
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.swing.ImageIcon;

//Clase para construir y manejar los Contactos
public class Contacto implements Serializable{
    
        private String nombre;
        private String apellido1;
        private String apellido2;
        private String dni;
        private String telefono;
        private Date fechaNacimiento;
        private Tipo tipo;
        private ImageIcon foto;

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public ImageIcon getFoto() {
        return foto;
    }

    public void setFoto(ImageIcon foto) {
        this.foto = foto;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
        
        

    
    public String getNombre() {
        return nombre;
    }

   
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Contacto() {
    }

    public Contacto(String nombre, String apellido1, String apellido2, String dni, String telefono, Date fechaNacimiento, Tipo tipo, ImageIcon foto) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.dni = dni;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.tipo = tipo;
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Contacto{" + "nombre=" + nombre + ", apellido1=" + apellido1 + '}';
    }
 
    
}
