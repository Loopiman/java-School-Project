package ecole.gestion.modele;

import ecole.metier.Salle;
import java.util.List;

/**
 *
 * @author fabia
 */
public interface DAOSalle {

    Salle create(Salle news);

    boolean delete(Salle srech);

    Salle read(Salle srech);

    Salle update(Salle srech);

    List<Salle> readAll();
}