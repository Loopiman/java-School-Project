package ecole.gestion.modele;

import ecole.metier.Cours;
import ecole.metier.Infos;

import java.util.*;

/**
 *
 * @author fabian
 */
public class ModeleCours implements DAOCours {

    private Set<Cours> listeCours = new TreeSet();


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
        if (!c.getListeInfos().isEmpty()) {
            System.out.println("impossible de supprimer. Cours présent dans une info");
            return false;
        }
        if (c != null) {
            listeCours.remove(c);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteInfo(Infos i) {
        for (Cours c : listeCours) {
            for (Infos in : c.getListeInfos()) {
                if (in.equals(i)) {
                    c.getListeInfos().remove(i);
                }
            }
        }

        return true;
    }

    @Override
    public Set<Cours> readAll() {
        return listeCours;
    }
}
