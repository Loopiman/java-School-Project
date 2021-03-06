package ecole.gestion.modele;

import ecole.metier.Cours;
import ecole.metier.db.EnseignantDB;
import ecole.metier.Enseignant;
import ecole.metier.Infos;
import ecole.metier.Salle;
import ecole.metier.db.CoursDB;
import ecole.metier.db.InfosDB;
import ecole.metier.db.SalleDB;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

import myconnections.DBConnection;

/**
 *
 * @author fabia
 */
public class ModeleEnseignantDB implements DAOEnseignant {

    protected Connection dbConnect;

    public ModeleEnseignantDB() {
        dbConnect = DBConnection.getConnection();
    }
    

    @Override
    public Enseignant create(Enseignant obj) {
        String req1 = "insert into api_enseignant(matricule, nom,prenom,tel,chargesem, chargeRest,salaire_mensu,date_engag) values(?,?,?,?,?,?,?,?)";
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(req1);) {
            pstm1.setString(1, obj.getMatricule());
            pstm1.setString(2, obj.getNom());
            pstm1.setString(3, obj.getPrenom());
            pstm1.setString(4, obj.getTel());
            pstm1.setInt(5, obj.getChargeSem());
            pstm1.setInt(6, obj.getChargeRest());
            pstm1.setBigDecimal(7, obj.getSalaireMensu());
            pstm1.setDate(8, Date.valueOf(obj.getDateEngagement()));
            int n = pstm1.executeUpdate();
            if (n == 0) {
                return null;
            } else {
                return read(obj);
            }
        } catch (Exception e) {
            System.out.println("error : " + e);
            return null;
        }
    }

    @Override
    public Enseignant read(Enseignant erech) {
        String matricule = erech.getMatricule();
        String req = "select * from api_enseignant e"
                + " left join api_info i on e.id_enseignant = i.id_enseignant"
                + " left join api_cours c on c.id_cours = i.id_cours"
                + " left join api_salle s on s.id_salle = i.id_salle"
                + " where e.matricule = ? order by e.matricule";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req);) {

            pstm.setString(1, matricule);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    int id_enseignant = rs.getInt("ID_ENSEIGNANT");
                    String nom = rs.getString("NOM");
                    String prenom = rs.getString("PRENOM");
                    String tel = rs.getString("TEL");
                    int chargesem = rs.getInt("CHARGESEM");
                    int chargeRest = rs.getInt("CHARGEREST");
                    BigDecimal salaire_mensu = rs.getBigDecimal("SALAIRE_MENSU");
                    LocalDate date_engagement = rs.getDate("DATE_ENGAG").toLocalDate();
                    Enseignant e = new EnseignantDB(id_enseignant, matricule, nom, prenom, tel, chargesem, chargeRest, salaire_mensu, date_engagement);

                    Set<Infos> li = new TreeSet<>();

                    int id_infos = rs.getInt("ID_INFO");
                    if (id_infos != 0) {
                        int id_cours = rs.getInt("ID_COURS");
                        int id_salle = rs.getInt("ID_SALLE");
                        int id_classe = rs.getInt("ID_CLASSE");

                        String code = rs.getString("CODE");
                        String intitule = rs.getString("INTITULE");
                        int nhs = rs.getInt("NHS");

                        String sigle = rs.getString("SIGLE_SALLE");
                        int capacite = rs.getInt("CAPACITE");

                        Cours c = new CoursDB(id_cours, code, intitule, nhs);
                        Salle s = new SalleDB(id_salle, sigle, capacite);
                        Infos i = new InfosDB(id_infos, id_cours, id_enseignant, id_classe, id_salle, c, s, e);
                        li.add(i);
                        while (rs.next()) {
                            id_infos = rs.getInt("ID_INFO");
                            id_cours = rs.getInt("ID_COURS");
                            id_salle = rs.getInt("ID_SALLE");
                            id_classe = rs.getInt("ID_CLASSE");
                            code = rs.getString("CODE");
                            intitule = rs.getString("INTITULE");
                            nhs = rs.getInt("NHS");
                            sigle = rs.getString("SIGLE_SALLE");
                            capacite = rs.getInt("CAPACITE");
                            c = new CoursDB(id_cours, code, intitule, nhs);
                            s = new SalleDB(id_salle, sigle, capacite);
                            i = new InfosDB(id_infos, id_cours, id_enseignant, id_salle, id_classe, c, s, e);
                            li.add(i);
                        }
                    }
                    e.setListeInfos(li);
                    return e;
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            System.out.println("error : " + e);
            return null;
        }
    }

    @Override
    public Enseignant update(Enseignant obj) {
        EnseignantDB pdb = (EnseignantDB) obj;
        String req = "update api_enseignant set tel=?,chargesem=?, chargerest = ?, salaire_mensu=? where id_enseignant = ?";
        String req1 = "select chargesem from api_enseignant where id_enseignant = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req); PreparedStatement pstm1 = dbConnect.prepareStatement(req1);) {

            pstm1.setInt(1, pdb.getId_enseignant());
            int oldcharge = 0;
            try (ResultSet rs = pstm1.executeQuery()) {
                if (rs.next()) {
                    oldcharge = rs.getInt("CHARGESEM");
                }
            }
            int dif = (pdb.getChargeSem() - oldcharge);
            int chargeRest = obj.getChargeRest() + dif;
            if (chargeRest < 0) {
                System.out.println("Impossible d'enlever plus que la charge actuelle.");
                return null;
            }
            pstm.setInt(5, pdb.getId_enseignant());
            pstm.setString(1, obj.getTel());
            pstm.setInt(2, obj.getChargeSem());
            pstm.setInt(3, obj.getChargeRest() + dif);
            pstm.setBigDecimal(4, obj.getSalaireMensu());
            int n = pstm.executeUpdate();

            if (n == 0) {
                return null;
            }
            return read(obj);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public boolean delete(Enseignant obj) {
        String req = "delete from api_enseignant where matricule= ?";

        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {

            pstm.setString(1, obj.getMatricule());
            int n = pstm.executeUpdate();
            if (n == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            System.out.println("erreur : L'enseignant est attribué à une info");
            return false;
        }
    }

    @Override
    public boolean deleteInfo(Infos i) {
       return true;
    }
    
    

    @Override
    public Set<Enseignant> readAll() {
        String req = "select * from api_enseignant order by matricule";
        Set<Enseignant> e = new TreeSet<>();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req); ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                int id_enseignant = rs.getInt("ID_ENSEIGNANT");
                String matricule = rs.getString("MATRICULE");
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String tel = rs.getString("TEL");
                int chargesem = rs.getInt("CHARGESEM");
                int chargeRest = rs.getInt("CHARGEREST");
                BigDecimal salaire_mensu = rs.getBigDecimal("SALAIRE_MENSU");
                LocalDate date_engagement = rs.getDate("DATE_ENGAG").toLocalDate();
                e.add(new EnseignantDB(id_enseignant, matricule, nom, prenom, tel, chargesem, chargeRest, salaire_mensu, date_engagement));
            }
            return e;

        } catch (Exception ex) {
            return e;
        }
    }

}
