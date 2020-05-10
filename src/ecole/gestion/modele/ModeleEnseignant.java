package ecole.gestion.modele;

import java.util.*;
import ecole.metier.Enseignant;

/**
 *
 * @author fabian
 */
public class ModeleEnseignant implements DAOEnseignant {

    private List<Enseignant> listeEnseignant = new ArrayList();

    @Override
    public Enseignant create(Enseignant e) {
        for (Enseignant ens : listeEnseignant) {
            if (ens.getMatricule().equals(e.getMatricule())) {
                return null;
            }
        }
        listeEnseignant.add(e);

        return e;
    }

    @Override
    public Enseignant read(Enseignant erech) {
        String matriculeRech = erech.getMatricule();
        for (Enseignant e : listeEnseignant) {
            if (e.getMatricule().equals(matriculeRech)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public Enseignant update(Enseignant erech) {
        Enseignant e = read(erech);

        if (e == null) {
            return null;
        }
        e.setNom(erech.getNom());
        e.setPrenom(erech.getPrenom());
        e.setTel(erech.getTel());
        e.setChargeSem(erech.getChargeSem());
        e.setSalaireMensu(erech.getSalaireMensu());
        e.setDateEngagement(erech.getDateEngagement());

        return e;
    }

    @Override
    public boolean delete(Enseignant erech) {
        Enseignant e = read(erech);
        if (e != null) {
            listeEnseignant.remove(e);
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public List<Enseignant> readAll(){
        return listeEnseignant;
    }
    

}
