package ecole.gestion.modele;

import ecole.metier.Classe;
import ecole.metier.Infos;
import java.util.List;

/**
 *
 * @author fabia
 */
public interface DAOClasse {

    Classe create(Classe newcl);

    boolean delete(Classe clrech);

    Classe read(Classe clrech);

    Classe update(Classe clrech);

    public boolean add(Classe cl, Infos i);

    List<Classe> readAll();

}
