package ecole.gestion.modele;

import ecole.metier.Salle;
import java.util.*;
import methods.SigleComparator;

/**
 *
 * @author fabian
 */
public class ModeleSalle implements DAOSalle{

    private Set<Salle> listeSalle = new HashSet();
    
    @Override
    public Salle create(Salle s) {
        for (Salle salle : listeSalle) {
            if (salle.getSigleSalle().equals(s.getSigleSalle())) {
                return null;
            }
        }
        listeSalle.add(s);
        
        return s;
    }
    
    @Override
    public Salle read(Salle srech) {
        String sigleRech = srech.getSigleSalle();
        for (Salle s : listeSalle) {
            if (s.getSigleSalle().equals(sigleRech)) {
                return s;
            }
        }
        return null;
    }
    
    @Override
    public Salle update(Salle srech) {
        Salle s = read(srech);
        
        if (s == null) {
            return null;
        }
        s.setCapacite(srech.getCapacite());
        
        return s;
    }
    
    @Override
    public boolean delete(Salle srech) {
        Salle s = read(srech);
        if (s != null) {
            listeSalle.remove(s);
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public Set<Salle> readAll(){
        return listeSalle;
    }
}
