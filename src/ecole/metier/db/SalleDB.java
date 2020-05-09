package ecole.metier.db;

import ecole.metier.Salle;
import java.util.Objects;

/**
 *
 * @author Fabian Raeskinet
 * @version 1.0
 * @see Salle
 *
 */
public class SalleDB extends Salle{

    /**
     * identifiant unique de la salle
     */
    protected int id_salle;

    public SalleDB(int id_salle, String sigleSalle, int capacite) {
        super(sigleSalle, capacite);
        this.id_salle = id_salle;
    }



    public int getId_salle() {
        return id_salle;
    }

    public void setId_salle(int id_salle) {
        this.id_salle = id_salle;
    }

    @Override
    public String toString() {
        return "SalleDB{"+super.toString() + "id_salle=" + id_salle + '}';
    }

    

}