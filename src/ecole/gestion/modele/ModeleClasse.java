package ecole.gestion.modele;

import ecole.metier.Classe;
import ecole.metier.Infos;

import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author fabian
 */
public class ModeleClasse implements DAOClasse {

    private Set<Classe> listeClasse = new TreeSet();



    @Override
    public Classe create(Classe cl) {
        for (Classe classe : listeClasse) {
            if (classe.getSigleClasse().equals(cl.getSigleClasse())) {
                return null;
            }
        }
        listeClasse.add(cl);

        return cl;
    }

    @Override
    public Classe read(Classe clrech) {
        String matriculeRech = clrech.getSigleClasse();
        for (Classe cl : listeClasse) {
            if (cl.getSigleClasse().equals(matriculeRech)) {
                return cl;
            }
        }
        return null;
    }

    @Override
    public Classe update(Classe clrech) {
        Classe cl = read(clrech);

        if (cl == null) {
            return null;
        }

        cl.setSpecialite(clrech.getSpecialite());
        cl.setNbrEleves(clrech.getNbrEleves());
        cl.setAnnee(clrech.getAnnee());

        return cl;
    }

    @Override
    public boolean delete(Classe clrech) {
        Classe cl = read(clrech);
        if (!cl.getListeInfos().isEmpty()) {
            System.out.println("impossible de supprimer, classe présente dans une info");
            return false;
        }
        if (cl != null) {
            listeClasse.remove(cl);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteInfo(Infos i) {
        for (Classe cl : listeClasse) {
            for (Infos in : cl.getListeInfos()) {
                if (in.equals(i)) {
                    cl.getListeInfos().remove(i);
                }
            }
        }

        return true;
    }

    @Override
    public Set<Classe> readAll() {
        return listeClasse;
    }

    @Override
    public boolean add(Classe cl, Infos i) {

        Set<Infos> listeInfos = new TreeSet();
        listeInfos = cl.getListeInfos();
        for (Infos in : listeInfos) {
            if (in.equals(i)) {
                return false;
            }
        }
        cl.getListeInfos().add(i);
        return true;
    }
}
