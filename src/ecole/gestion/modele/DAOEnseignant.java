package ecole.gestion.modele;

import ecole.metier.Enseignant;
import ecole.metier.Infos;

import java.util.Set;

/**
 *
 * @author fabian
 */
public interface DAOEnseignant {

    Enseignant create(Enseignant newe);

    boolean delete(Enseignant erech);

    Enseignant read(Enseignant erech);

    Enseignant update(Enseignant erech);

    Set<Enseignant> readAll();
    
    boolean deleteInfo(Infos i);
    

}
