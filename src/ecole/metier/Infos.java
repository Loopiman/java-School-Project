package ecole.metier;

import java.util.Objects;

/**
 *
 * @author Fabian Raeskinet
 * @version 1.0
 * @see Infos
 */
public class Infos {

    /**
     * cours concerné
     */
    protected Cours cours;

    /**
     * salle concernée
     */
    protected Salle salle;

    /**
     * enseignant concerné
     */
    protected Enseignant enseignant;

    /**
     * constructeur vide
     */
    public Infos() {
    }

    /**
     * construceur paramétré
     *
     * @param cours
     * @param salle
     * @param prof
     */
    public Infos(Cours cours, Salle salle, Enseignant enseignant) {
        this.cours = cours;
        this.salle = salle;
        this.enseignant = enseignant;
    }

    /**
     * getter du cours
     *
     * @return cours
     */
    public Cours getCours() {
        return cours;
    }

    /**
     * setter du cours
     *
     * @param cours
     */
    public void setCours(Cours cours) {
        this.cours = cours;
    }

    /**
     * getter de la salle
     *
     * @return salle
     */
    public Salle getSalle() {
        return salle;
    }

    /**
     * setter de la salle
     *
     * @param salle
     */
    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.cours);
        hash = 67 * hash + Objects.hashCode(this.salle);
        hash = 67 * hash + Objects.hashCode(this.enseignant);
        return hash;
    }

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
        final Infos other = (Infos) obj;
        if (!Objects.equals(this.cours, other.cours)) {
            return false;
        }
        if (!Objects.equals(this.salle, other.salle)) {
            return false;
        }
        if (!Objects.equals(this.enseignant, other.enseignant)) {
            return false;
        }
        return true;
    }

    /**
     * getter de l'enseignant
     *
     * @return prof
     */
    public Enseignant getEnseignant() {
        return enseignant;
    }

    /**
     * setter de l'enseignant
     *
     * @param prof
     */
    public void setEnseignant(Enseignant prof) {
        this.enseignant = prof;
    }

    @Override
    public String toString() {
        return "Infos{" + "cours=" + cours + ", salle=" + salle + ", prof=" + enseignant + '}';
    }

}
