package methods;

import static java.lang.Integer.parseInt;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author fabia
 */
public class Controle {

    public static boolean verifInteger(String ph) {
        boolean ok = false;
        for (int i = 0; i < ph.length(); i++) {
            if (Character.isDigit(ph.charAt(i)) == true) {
                ok = true;
            } else {
                ok = false;
                break;
            }
        }
        return ok;
    }

    public static boolean verifId(String matricule, String S) {
        boolean ok;
        String part1;
        String part2;

        int length = S.length();
        part1 = matricule.substring(0, length);
        part2 = matricule.substring(length);
        if (part1.equals(S) && verifInteger(part2) == true) {
            ok = true;
        } else {
            ok = false;
            System.out.println("Matricule Invalide");
        }

        return ok;
    }

    public static boolean verifNumero(String tel) {
        boolean ok;
        if (tel.length() != 10) {
            System.out.println("Numéro invalide");
            ok = false;
        } else {

            if (verifInteger(tel) == true && tel.substring(0, 2).equals("04") == true) {
                ok = true;
            } else {
                System.out.println("Veuillez entrez un numéro qui commence par 04");
                ok = false;
            }
        }
        return ok;
    }

    public static LocalDate insertDate(String date) {
        LocalDate dateFinal = null;

        String p1, p2, p3;

        int d, m, a;

        if (date.length() == 10) {

            p1 = date.substring(0, 2);

            p2 = date.substring(3, 5);

            p3 = date.substring(6);
            if (verifInteger(p1) == true && verifInteger(p2) == true && verifInteger(p3) == true) {
                d = parseInt(p1);
                m = parseInt(p2);
                a = parseInt(p3);

                try {
                    dateFinal = LocalDate.of(a, m, d);
                } catch (Exception e) {

                }
            }
        }
        if (dateFinal == null) {
            System.out.println("Erreur de date");
        }
        return dateFinal;
    }

    public static boolean verifSalaire(String sal) {
        boolean ok = true;
        if (verifInteger(sal) == true) {
            BigDecimal min = new BigDecimal("2000");
            BigDecimal salaire = new BigDecimal(sal);
            if (salaire.compareTo(min) == -1) {
                System.out.println("Veuillez entrer un salaire minimum 2000€");
                ok = false;
            }
        }
        else{
            ok = false;
        }
        return ok;
    }

    public static boolean verifChargesem(String h) {
        boolean ok = false;
        if (verifInteger(h) == true) {
            int heure = parseInt(h);
            if (heure < 2 || heure > 40) {
                System.out.println("Veuillez entrer une valeur entre 2 et 40");
                ok = false;
            } else {
                ok = true;
            }
        }

        return ok;
    }

    public static boolean verifAnnee(String a){
        boolean ok = false;
        if(verifInteger(a) == true){
            if(a.length() < 4 || a.length() > 4){
                System.out.println("Veuillez entrer une année valide");
            }
            else{
                ok = true;
            }
        }
        else{
                System.out.println("Veuillez entrer une année valide");
        }
        return ok;
    }
    
    public static boolean verifNbrEleve(String nbr){
        boolean ok = false;
        
        if(verifInteger(nbr) == true){
            int nombre = parseInt(nbr);
            if(nombre < 1){
                System.out.println("Veuillez entrer au moins un élève ! ");
            }
            else{
                ok = true;
            }
        }
        return ok;
    }
}
