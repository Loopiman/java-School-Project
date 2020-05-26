package methods;

import static java.lang.Integer.parseInt;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 *
 * @author fabia
 */
public class Controle {

    public static boolean verifInteger(String ph) {
        for (int i = 0; i < ph.length(); i++) {
            if (Character.isDigit(ph.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public static boolean verifAlphabet(String ph) {

        if(verifInteger(ph)){
            return false;
        }
        return true;
    }

    public static boolean verifId(String matricule, String S) {
        boolean ok;
        String part1;
        String part2;

        int length = S.length();
        int matricule_length = matricule.length();
        if(matricule.substring(0,matricule_length).length() < length){
            return false;
        }
        part1 = matricule.substring(0, length);
        part2 = matricule.substring(length);
        if (part1.equals(S) && verifAlphabet(part1) && verifInteger(part2) == true) {
            ok = true;
        } else {
            ok = false;
            System.out.println("Matricule Invalide");
        }

        return ok;
    }

    public static boolean verifId(String matricule) {
        boolean ok = false;

        String part1;
        String part2;

        part1 = matricule.substring(0, 1);
        part2 = matricule.substring(1);
        if (verifAlphabet(part1) && verifInteger(part2)) {
            ok = true;
        } else {
            ok = false;
            System.out.println("Matricule Invalide");
        }

        return ok;
    }

    public static boolean verifNumero(String tel) {
        boolean ok;
        if (tel.length() != 13) {
            System.out.println("Numéro invalide");
            ok = false;
        } else {
            String part1 = tel.substring(0,4);
            String part2 = tel.substring(5,7);
            String part3 = tel.substring(8,10);
            String part4 = tel.substring(11);
            String char1 = tel.substring(4,5);
            String char2 = tel.substring(7,8);
            String char3 = tel.substring(10,11);
            
            System.out.println("tel : " + part1+char1+part2+char2+part3+char3+part4);
            

            if (verifInteger(part1) && verifInteger(part2) && verifInteger(part3) && verifInteger(part4) && char1.equals("/") && char2.equals(".")&& char3.equals(".") && tel.substring(0, 2).equals("04") == true) {
                ok = true;
            } else {
                System.out.println("Veuillez entrez un numéro correct");
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
        try {
            BigDecimal min = new BigDecimal("2000");
            BigDecimal salaire = new BigDecimal(sal);
            if (salaire.compareTo(min) == -1) {
                System.out.println("Veuillez entrer un salaire minimum 2000€");
                ok = false;
            }
        } catch (Exception e) {
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

    public static boolean verifAnnee(String a) {
        boolean ok = false;

        int now = LocalDate.now().getYear();
        if (verifInteger(a) == true) {
            if ((a.length() < 4 || a.length() > 4) || parseInt(a) != now) {
                System.out.println("Veuillez entrer une année valide");
            } else {
                ok = true;
            }
        } else {
            System.out.println("Veuillez entrer une année valide");
        }
        return ok;
    }

    public static boolean verifNbrEleve(String nbr) {
        boolean ok = false;

        if (verifInteger(nbr) == true) {
            int nombre = parseInt(nbr);
            if (nombre < 1) {
                System.out.println("Veuillez entrer au moins un élève ! ");
            } else {
                ok = true;
            }
        }
        return ok;
    }
}
