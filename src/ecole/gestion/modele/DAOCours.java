package ecole.gestion.modele;

import ecole.metier.Cours;
import java.util.List;

/**
 *
 * @author fabia
 */
public interface DAOCours {

    Cours create(Cours newc);

    boolean delete(Cours crech);

    Cours read(Cours crech);

    Cours update(Cours crech);

    List<Cours> readAll();
}
