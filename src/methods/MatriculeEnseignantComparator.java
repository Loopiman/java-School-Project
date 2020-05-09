package methods;

import ecole.metier.Enseignant;
import java.util.Comparator;

/**
 *
 * @author fabia
 */
public class MatriculeEnseignantComparator implements Comparator<Enseignant>{
    @Override
    public int compare(Enseignant e1, Enseignant e2){
        String matricule1 = e1.getMatricule();
        String matricule2 = e2.getMatricule();
        
        return matricule1.compareTo(matricule2);
        
    }
}
