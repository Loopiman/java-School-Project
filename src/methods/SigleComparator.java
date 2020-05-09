package methods;

import ecole.metier.Salle;
import java.util.*;

/**
 *
 * @author fabia
 */
public class SigleComparator implements Comparator<Salle>{
    
    @Override
    public int compare(Salle s1, Salle s2){
        String sigle1 = s1.getSigleSalle();
        String sigle2 = s2.getSigleSalle();
        
        return sigle1.compareTo(sigle2);
        
    }
}
