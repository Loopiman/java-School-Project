package ecole.metier;

import java.math.*;
import java.time.LocalDate;
import java.util.*;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Fabian Raeskinet
 * @version 1.0
 * @see Enseignant
 *
 */
public class Enseignant implements Comparable<Enseignant> {

    /**
     * identifiant unique de l'enseignant
     */
    protected String matricule;

    /**
     * nom de l'enseignant
     */
    protected String nom;

    /**
     * prénom de l'enseignant
     */
    protected String prenom;

    /**
     * numéro de téléphone du l'enseignant
     */
    protected String tel;

    /**
     * nombre d'heures par semaine
     */
    protected int chargeSem;

    /**
     * nombre d'heures restante par semaine
     */
    protected int chargeRest;

    /**
     * salaire mensuel
     */
    protected BigDecimal salaireMensu;

    /**
     * date de l'engagement
     */
    protected LocalDate dateEngagement;

    /**
     * liste infos de l'enseignant
     */
    protected Set<Infos> listeInfos = new HashSet();

    /**
     * constructeur par défaut
     */
    public Enseignant() {

    }

    /**
     * constructeur parametré
     *
     * @param matricule
     * @param nom
     * @param prenom
     * @param tel
     * @param chargeSem
     * @param salaireMensu
     * @param dateEngagement
     */
    public Enseignant(String matricule, String nom, String prenom, String tel, int chargeSem, int chargeRest, BigDecimal salaireMensu, LocalDate dateEngagement) {

        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.chargeSem = chargeSem;
        this.chargeRest = chargeRest;
        this.salaireMensu = salaireMensu;
        this.dateEngagement = dateEngagement;
    }

    /**
     * getter du matricule unique
     *
     * @return matricule
     */
    public String getMatricule() {
        return matricule;
    }

    /**
     * setter du matricule unique
     *
     * @param matricule
     */
    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    /**
     * getter du nom
     *
     * @return nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * setter du nom
     *
     * @param nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * getter du prenom
     *
     * @return prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * setter du prenom
     *
     * @param prenom
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * getter du téléphone
     *
     * @return telephone
     */
    public String getTel() {
        return tel;
    }

    /**
     * setter du téléphone
     *
     * @param tel
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * getter de la charge par semaine
     *
     * @return chargeSem
     */
    public int getChargeSem() {
        return chargeSem;
    }

    /**
     * setter de la charge par semaine
     *
     * @param chargeSem
     */
    public void setChargeSem(int chargeSem) {
        this.chargeSem = chargeSem;
    }
    /**
     * getter de la charge restante par semaine
     *
     * @return chargeSem
     */
    public int getChargeRest() {
        return chargeRest;
    }

    /**
     * setter de la charge restante par semaine
     *
     * @param chargeSem
     */
    public void setChargeRest(int chargeRest) {
        this.chargeRest = chargeRest;
    }

    /**
     * getter du salaire mensuel
     *
     * @return salaireMensu
     */
    public BigDecimal getSalaireMensu() {
        return salaireMensu;
    }

    /**
     * setter du salaire mensuel
     *
     * @param salaireMensu
     */
    public void setSalaireMensu(BigDecimal salaireMensu) {
        this.salaireMensu = salaireMensu;
    }

    /**
     * getter de la date d'engagement
     *
     * @return dateEngagement
     */
    public LocalDate getDateEngagement() {
        return dateEngagement;
    }

    /**
     * setter de la date d'engagement
     *
     * @param dateEngagement
     */
    public void setDateEngagement(LocalDate dateEngagement) {
        this.dateEngagement = dateEngagement;
    }

    /**
     * getter de la liste d'infos
     * 
     * @return 
     */
    public Set<Infos> getListeInfos() {
        return listeInfos;
    }

    /**
     * setter de la liste d'infos
     * 
     * @param listeInfos 
     */
    public void setListeInfos(Set<Infos> listeInfos) {
        this.listeInfos = listeInfos;
    }

    /**
     * hashcode du sigle
     *
     * @return hash
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.matricule);
        return hash;
    }

    /**
     * methode equals pour vérifier que le sigle est unique
     *
     * @param obj
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
        final Enseignant other = (Enseignant) obj;
        if (!Objects.equals(this.matricule, other.matricule)) {
            return false;
        }
        return true;
    }

    /**
     * override methode compareTo
     * 
     * @param e
     * @return 
     */
    @Override
    public int compareTo(Enseignant e) {
        return this.matricule.compareTo(e.getMatricule());
    }

    /**
     * methode qui affiche les infos du professeur
     *
     * @return info
     */
    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d-MM-yyyy", Locale.FRENCH);
        String aff = dateEngagement.format(dtf);
        return "matricule : " + matricule + " nom : " + nom + " prénom : " + prenom + " tel : " + tel + " chargeSem : " + chargeSem + "chargeRest : " + chargeRest + " salaireMensu : " + salaireMensu + " date engagement : " + aff;
    }

}
