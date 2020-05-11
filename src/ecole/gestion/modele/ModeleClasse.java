package ecole.gestion.modele;

import ecole.metier.Classe;
import ecole.metier.Infos;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author fabian
 */
public class ModeleClasse implements DAOClasse{

    private Set<Classe> listeClasse = new HashSet();

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
        if (cl != null) {
            listeClasse.remove(cl);
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public Set<Classe> readAll(){
        return listeClasse;
    }
    
    @Override
    public boolean add(Classe cl, Infos i) {
        cl.getListeInfos().add(i);
        return true;
    }
}
