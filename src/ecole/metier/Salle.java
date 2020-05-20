package ecole.metier;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Fabian Raeskinet
 * @version 1.0
 * @see Salle
 *
 */
public class Salle implements Comparable<Salle>{

    /**
     * identifiant unique de la salle
     */
    protected String sigleSalle;

    /**
     * capacité de la salle
     */
    protected int capacite;

    protected Set<Infos> listeInfos = new HashSet();

    /**
     * constructeur par défaut
     */
    public Salle() {

    }

    /**
     *
     * costructeur paramétré
     *
     * @param sigleSalle identifiant unique de la salle, affecté par
     * l'utilisateur
     * @param capacite capacité maximale de la classe
     *
     */
    public Salle(String sigleSalle, int capacite) {
        this.sigleSalle = sigleSalle;
        this.capacite = capacite;
    }

    /**
     * getter sigleSalle
     *
     * @return identifiant de la salle
     */
    public String getSigleSalle() {
        return sigleSalle;
    }

    /**
     * setter sigleSalle
     *
     * @param sigleSalle
     */
    public void setSigleSalle(String sigleSalle) {
        this.sigleSalle = sigleSalle;
    }

    /**
     * getter capacité maximale de la salle
     *
     * @return capacite
     */
    public int getCapacite() {
        return capacite;
    }

    /**
     * setter setCapacite
     *
     * @param capacite
     */
    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public Set<Infos> getListeInfos() {
        return listeInfos;
    }

    public void setListeInfos(Set<Infos> listeInfos) {
        this.listeInfos = listeInfos;
    }
    
    

    /**
     * hashCode
     *
     * @return hash
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.sigleSalle);
        return hash;
    }

    /**
     *
     * @param obj
     *
     * @return true
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Salle other = (Salle) obj;
        if (!Objects.equals(this.sigleSalle, other.sigleSalle)) {
            return false;
        }
        return true;
    }
    @Override
    public int compareTo(Salle s) {
        return this.sigleSalle.compareTo(s.sigleSalle);
    }
    @Override
    public String toString() {
        return "Salle{" + "sigleSalle=" + sigleSalle + ", capacite=" + capacite + '}';
    }

}
