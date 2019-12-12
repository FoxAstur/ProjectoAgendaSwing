package controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import modelo.Contacto;
import modelo.ContactoDAO;
import modelo.Tipo;

public class PersonaRamDao implements ContactoDAO {

    List<Contacto> agenda = new ArrayList();

    public PersonaRamDao() {

        Contacto p0 = new Contacto("Eva", "Rey", "Varela", "71665576Z", "985118525", new Date(86, 11, 23), Tipo.enemigo, new ImageIcon("./Fotos/Lisa.Jpg"));
        Contacto p1 = new Contacto("Claudia", "Perez", "Andrade", "76438431R", "985118525", new Date(91, 4, 19), Tipo.amigo, new ImageIcon("./Fotos/Bart.Jpg"));
        Contacto p2 = new Contacto("Juan", "Rodriguez", "Menendez", "10535241E", "985118789", new Date(84, 5, 19), Tipo.trabajo, new ImageIcon("./Fotos/Homer.Jpg"));
        Contacto p3 = new Contacto("Alberto", "Lopez", "Mendez", "52930684W", "985118525", new Date(76, 8, 6), Tipo.familiar, new ImageIcon("./Fotos/Marge.Jpg"));
        Contacto p4 = new Contacto("juale", "es", "monger", "54130146Z", "985118525", new Date(76, 8, 6), Tipo.familiar, new ImageIcon("./Fotos/Maggie.Jpg"));

        agenda.add(p0);
        agenda.add(p1);
        agenda.add(p2);
        agenda.add(p3);
        agenda.add(p4);
    }

    @Override
    public boolean addPersona(Contacto c) {

        if (!agenda.contains(c)) {
            agenda.add(c);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Contacto> getAllPersona() {

        return agenda;

    }

    @Override
    public Contacto getPersonaByNif(String NIF) {

        for (Contacto c : agenda) {
            //recorro la agenda buscando ese dni
            if (NIF.equals(c.getDni())) {

                return c;
            }

        }
        return null;

    }

    @Override
    public boolean updatePersona(Contacto c) {

        int indice;
        if ((indice = agenda.indexOf(c)) == -1) {
            return false;
        } else {
            agenda.set(indice, c);
            return true;
        }

    }

    @Override
    public boolean removePersona(Contacto c) {

        if (!agenda.contains(c)) {
            return false;
        } else {
            agenda.remove(c);
            return true;

        }

    }

}
