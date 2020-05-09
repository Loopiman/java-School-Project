package methods;

import ecole.metier.Classe;
import java.util.*;

/**
 *
 * @author fabia
 */
public class SigleClasseComparator implements Comparator<Classe>{
    @Override
    public int compare(Classe cl1, Classe cl2){
        String sigle1 = cl1.getSigleClasse();
        String sigle2 = cl2.getSigleClasse();
        
        return sigle1.compareTo(sigle2);
        
    }
}
