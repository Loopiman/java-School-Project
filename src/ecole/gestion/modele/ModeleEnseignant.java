package ecole.gestion.modele;

import ecole.metier.Enseignant;
import ecole.metier.Infos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/**
 *
 * @author fabian
 */
public class ModeleEnseignant implements DAOEnseignant {

    private Set<Enseignant> listeEnseignant = new TreeSet();
    


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
        if (!e.getListeInfos().isEmpty()) {
            System.out.println("impossible de supprimer. Enseignant présent dans une info");
            return false;
        }
        if (e != null) {
            listeEnseignant.remove(e);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteInfo(Infos i) {
        for (Enseignant e : listeEnseignant) {
            for (Infos in : e.getListeInfos()) {
                if (in.equals(i)) {
                    e.getListeInfos().remove(i);
                }
            }
        }

        return true;
    }

    @Override
    public Set<Enseignant> readAll() {
        return listeEnseignant;
    }

}
