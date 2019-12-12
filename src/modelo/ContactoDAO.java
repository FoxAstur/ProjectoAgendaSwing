/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.List;

/**
 *
 * @author Carlos
 */
public interface ContactoDAO {
    
    
    //Operaciones CRUD de Contactos
    
    boolean addPersona(Contacto p);

    List<Contacto> getAllPersona();

    Contacto getPersonaByNif(String NIF);

    boolean updatePersona(Contacto p);

    boolean removePersona(Contacto p);
}
    
    

