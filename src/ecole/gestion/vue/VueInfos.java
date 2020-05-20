package ecole.gestion.vue;

import ecole.metier.Infos;

import methods.Controle;

import java.util.Scanner;
import java.util.*;

/**
 *
 * @author fabia
 */
public class VueInfos {

    Scanner sc = new Scanner(System.in);

    public void displayMsg(String msg) {
        System.out.println(msg);
    }

    public String getMsg(String invite) {
        displayMsg(invite);
        String msg = sc.nextLine();

        return msg;
    }

    public int menu() {
        int ch;
        String chs;
        do {
            do {
                chs = getMsg("1. Afficher les Infos\n2. Supprimer\n3. Quitter le menu\n\nVotre choix : ");
            } while (!Controle.verifInteger(chs));

            ch = Integer.parseInt(chs);
            if (ch >= 1 && ch <= 3) {
                return ch;
            }
            displayMsg("choix invalide");
        } while (true);
    }

    public void display(Infos s) {
        displayMsg(s.toString());
    }

    public void affAll(List<Infos> ls) {
        int i = 1;
        for (Infos s : ls) {
            displayMsg(i + "." + s.toString());
            i++;
        }
    }
}
