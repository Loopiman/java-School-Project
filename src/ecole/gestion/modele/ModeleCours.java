package ecole.gestion.modele;

import java.util.*;
import ecole.metier.Cours;
import ecole.gestion.modele.DAOCours;

/**
 *
 * @author fabian
 */
public class ModeleCours implements DAOCours{

    private List<Cours> listeCours = new ArrayList();

    @Override
    public Cours create(Cours c) {
        for (Cours cours : listeCours) {
            if (cours.getCode().equals(c.getCode())) {
                return null;
            }
        }
        listeCours.add(c);

        return c;
    }

    @Override
    public Cours read(Cours crech) {
        String matriculeRech = crech.getCode();
        for (Cours c : listeCours) {
            if (c.getCode().equals(matriculeRech)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public Cours update(Cours crech) {
        Cours c = read(crech);

        if (c == null) {
            return null;
        }

        c.setIntitule(crech.getIntitule());
        c.setNhs(crech.getNhs());

        return c;
    }

    @Override
    public boolean delete(Cours crech) {
        Cours c = read(crech);
        if (c != null) {
            listeCours.remove(c);
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public List<Cours> readAll(){
        return listeCours;
    }
}
