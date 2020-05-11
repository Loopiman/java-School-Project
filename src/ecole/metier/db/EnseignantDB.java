package ecole.metier.db;

import ecole.metier.Enseignant;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author Fabian Raeskinet
 * @version 1.0
 * @see Enseignant
 *
 */
public class EnseignantDB extends Enseignant{

    
    /**
     * identifiant unique de l'enseignantDB
     */
    protected int id_enseignant;

    public EnseignantDB() {
    }

    public EnseignantDB(int id_enseignant, String matricule, String nom, String prenom, String tel, int chargeSem, int chargeRest, BigDecimal salaireMensu, LocalDate dateEngagement) {
        super(matricule, nom, prenom, tel, chargeSem, chargeRest, salaireMensu, dateEngagement);
        this.id_enseignant = id_enseignant;
    }



    public int getId_enseignant() {
        return id_enseignant;
    }

    public void setId_enseignant(int id_enseignant) {
        this.id_enseignant = id_enseignant;
    }

    @Override
    public String toString() {
        return "EnseignantDB{"+super.toString() + "id_enseignant=" + id_enseignant + '}';
    }
    

}
