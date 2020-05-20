package ecole.gestion.modele;

import ecole.metier.Classe;
import ecole.metier.Cours;
import ecole.metier.Enseignant;
import ecole.metier.Infos;
import ecole.metier.Salle;
import ecole.metier.db.EnseignantDB;
import ecole.metier.db.InfosDB;
import ecole.metier.db.SalleDB;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import myconnections.DBConnection;

/**
 *
 * @author fabia
 */
public class ModeleInfosDB implements DAOInfos {

    protected Connection dbConnect;

    public ModeleInfosDB() {
        dbConnect = DBConnection.getConnection();
    }

    @Override
    public boolean delete(Infos srech) {
        String req = "delete from api_info where id_info= ?";
        String req1 = "select e.id_enseignant, c.nhs from api_enseignant e"
                + " join api_info i on e.id_enseignant = i.id_enseignant"
                + " join api_cours c on c.id_cours = i.id_cours where i.id_info = ?";
        String req2 = "update api_enseignant set chargerest = chargerest + ? where id_enseignant = ?";
        int id_enseignant = 0;
        int nhs = 0;
        InfosDB i = (InfosDB) srech;
        try (PreparedStatement pstm = dbConnect.prepareStatement(req);
                PreparedStatement pstm1 = dbConnect.prepareStatement(req1);
                PreparedStatement pstm2 = dbConnect.prepareStatement(req2);) {

            pstm1.setInt(1, i.getId_infos());

            try (ResultSet rs1 = pstm1.executeQuery()) {
                if (rs1.next()) {
                    id_enseignant = rs1.getInt("ID_ENSEIGNANT");
                    nhs = rs1.getInt("NHS");
                }
            }

            pstm2.setInt(1, nhs);
            pstm2.setInt(2, id_enseignant);
            pstm2.executeUpdate();

            pstm.setInt(1, i.getId_infos());
            int n = pstm.executeUpdate();
            if (n == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            System.out.println("error : " + e);
            return false;
        }
    }

    @Override
    public List<Infos> readAll() {
        String req = "select * from api_info i"
                + " left join api_enseignant e on e.id_enseignant = i.id_enseignant"
                + " left join api_cours c on c.id_cours = i.id_cours"
                + " left join api_classe cl on cl.id_classe = i.id_classe"
                + " left join api_salle s on s.id_salle = i.id_salle";

        List<Infos> li = new ArrayList<>();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req); ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                int idinfos = rs.getInt("ID_INFO");
                int idclasse = rs.getInt("ID_CLASSE");
                int idenseignant = rs.getInt("ID_ENSEIGNANT");
                int idcours = rs.getInt("ID_COURS");
                int idsalle = rs.getInt("ID_SALLE");

                String code = rs.getString("CODE");
                String intitule = rs.getString("INTITULE");
                int nhs = rs.getInt("NHS");

                Cours c = new Cours(code, intitule, nhs);

                String sigle_classe = rs.getString("SIGLE");
                int annee = rs.getInt("ANNEE");
                String specialite = rs.getString("SPECIALITE");
                int nbr_eleves = rs.getInt("NBR_ELEVES");

                Classe cl = new Classe(sigle_classe, annee, specialite, nbr_eleves);

                int id_salle = rs.getInt("ID_SALLE");
                String sigle_salle = rs.getString("SIGLE_SALLE");
                int capacite = rs.getInt("CAPACITE");

                Salle s = new SalleDB(id_salle, sigle_salle, capacite);

                int id_enseignant = rs.getInt("ID_ENSEIGNANT");
                String matricule = rs.getString("MATRICULE");
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String tel = rs.getString("TEL");
                int chargesem = rs.getInt("CHARGESEM");
                int chargeRest = rs.getInt("CHARGEREST");
                BigDecimal salaire_mensu = rs.getBigDecimal("SALAIRE_MENSU");
                LocalDate date_engagement = rs.getDate("DATE_ENGAG").toLocalDate();
                Enseignant e = new EnseignantDB(id_enseignant, matricule, nom, prenom, tel, chargesem, chargeRest, salaire_mensu, date_engagement);

                li.add(new InfosDB(idinfos, idcours, idenseignant, idclasse, idsalle, c, s, e));
            }
            return li;
        } catch (Exception e) {
            System.out.println("error" + e);
            return li;
        }
    }

    @Override
    public boolean add(Infos i) {
        //inutile ici puisque les infos sont ajoutées dans la base de données directement depuis classeDB
        return true;
    }
    

}
