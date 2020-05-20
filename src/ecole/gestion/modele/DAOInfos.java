package ecole.gestion.modele;

import ecole.metier.Infos;
import java.util.List;

/**
 *
 * @author fabia
 */
public interface DAOInfos {
    
    boolean delete(Infos srech);

    List<Infos> readAll();
    
    public boolean add(Infos i);
}
