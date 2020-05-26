package ecole.gestion.modele;

import ecole.metier.Infos;
import ecole.metier.Salle;

import java.util.*;

/**
 *
 * @author fabian
 */
public class ModeleSalle implements DAOSalle {

    private Set<Salle> listeSalle = new TreeSet();


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
        if (!s.getListeInfos().isEmpty()) {
            System.out.println("impossible de supprimer. Salle présent dans une info");
            return false;
        }
        if (s != null) {
            listeSalle.remove(s);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteInfo(Infos i) {
        for (Salle s : listeSalle) {
            for (Infos in : s.getListeInfos()) {
                if (in.equals(i)) {
                    s.getListeInfos().remove(i);
                }
            }
        }

        return true;
    }

    @Override
    public Set<Salle> readAll() {
        return listeSalle;
    }
}
