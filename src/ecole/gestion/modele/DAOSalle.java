package ecole.gestion.modele;

import ecole.metier.Infos;
import ecole.metier.Salle;

import java.util.Set;

/**
 *
 * @author fabia
 */
public interface DAOSalle {

    Salle create(Salle news);

    boolean delete(Salle srech);

    Salle read(Salle srech);

    Salle update(Salle srech);

    Set<Salle> readAll();
    
    boolean deleteInfo(Infos i);
    
}
