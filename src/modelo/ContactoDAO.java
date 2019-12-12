
package modelo;

import java.util.List;


public interface ContactoDAO {
    
    
    //Operaciones CRUD de Contactos
    
    boolean addPersona(Contacto p);

    List<Contacto> getAllPersona();

    Contacto getPersonaByNif(String NIF);

    boolean updatePersona(Contacto p);

    boolean removePersona(Contacto p);
    
    
    
}