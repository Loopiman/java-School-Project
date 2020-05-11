package ecole.gestion.modele;

/**
 *
 * @author fabia
 */
import ecole.metier.db.ClasseDB;
import ecole.metier.Classe;
import ecole.metier.Cours;
import ecole.metier.Enseignant;
import ecole.metier.Infos;
import ecole.metier.Salle;
import ecole.metier.db.CoursDB;
import ecole.metier.db.EnseignantDB;
import ecole.metier.db.InfosDB;
import ecole.metier.db.SalleDB;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import myconnections.DBConnection;

/**
 *
 * @author fabia
 */
public class ModeleClasseDB implements DAOClasse {

    protected Connection dbConnect;

    public ModeleClasseDB() {
        dbConnect = DBConnection.getConnection();
    }

    @Override
    public Classe create(Classe obj) {
        String req1 = "insert into api_classe(sigle, annee, specialite, nbr_eleves) values(?,?,?,?)";

        try (PreparedStatement pstm1 = dbConnect.prepareStatement(req1);) {
            pstm1.setString(1, obj.getSigleClasse());
            pstm1.setInt(2, obj.getAnnee());
            pstm1.setString(3, obj.getSpecialite());
            pstm1.setInt(4, obj.getNbrEleves());
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
    public Classe read(Classe clrech) {
        String sigle = clrech.getSigleClasse();
        String req
                = "select cl.id_classe, cl.sigle, cl.annee, cl.specialite, cl.nbr_eleves,"
                + "e.id_enseignant, e.matricule, e.nom, e.prenom, e.tel, e.chargesem, e.chargerest, e.salaire_mensu, e.date_engag,"
                + "c.id_cours, c.code, c.intitule, c.nhs,"
                + "s.id_salle, s.sigle_salle, s.capacite,"
                + "i.id_info, i.id_classe, i.id_enseignant, i.id_cours, i.id_salle"
                + " from api_classe cl left join api_info i on cl.id_classe = i.id_classe"
                + " left join api_enseignant e on e.id_enseignant = i.id_enseignant"
                + " left join api_cours c on c.id_cours = i.id_cours"
                + " left join api_salle s on s.id_salle = i.id_salle"
                + " where cl.sigle = ? order by cl.sigle";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req);) {
            pstm.setString(1, sigle);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    /*----------TABLE CASSE----------*/
                    int id_classe = rs.getInt("ID_CLASSE");
                    int annee = rs.getInt("ANNEE");
                    String specialite = rs.getString("SPECIALITE");
                    int nbr_eleves = rs.getInt("NBR_ELEVES");

                    Classe cl = new ClasseDB(id_classe, sigle, annee, specialite, nbr_eleves);

                    List<Infos> li = new ArrayList<>();
                    int id_infos = rs.getInt("ID_INFO");

                    if (id_infos != 0) {
                        /*----------TABLE ENSEIGNANT----------*/
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

                        /*----------TABLE COURS----------*/
                        int id_cours = rs.getInt("ID_COURS");
                        String code = rs.getString("CODE");
                        String intitule = rs.getString("INTITULE");
                        int nhs = rs.getInt("NHS");

                        Cours c = new CoursDB(id_cours, code, intitule, nhs);

                        /*----------TABLE SALLE----------*/
                        int id_salle = rs.getInt("ID_SALLE");
                        String sigle_salle = rs.getString("SIGLE_SALLE");
                        int capacite = rs.getInt("CAPACITE");

                        Salle s = new SalleDB(id_salle, sigle_salle, capacite);

                        Infos i = new InfosDB(id_infos, id_cours, id_enseignant, id_classe, id_salle, c, s, e);
                        li.add(i);
                        while (rs.next()) {
                            /*----------TABLE COURS----------*/
                            id_cours = rs.getInt("ID_COURS");
                            code = rs.getString("CODE");
                            intitule = rs.getString("INTITULE");
                            nhs = rs.getInt("NHS");

                            /*----------TABLE SALLE----------*/
                            id_salle = rs.getInt("ID_SALLE");
                            sigle = rs.getString("SIGLE_SALLE");
                            capacite = rs.getInt("CAPACITE");
                            id_infos = rs.getInt("ID_INFO");
                            id_cours = rs.getInt("ID_COURS");
                            id_salle = rs.getInt("ID_SALLE");

                            /*----------TABLE ENSEIGNANT----------*/
                            id_enseignant = rs.getInt("ID_ENSEIGNANT");
                            matricule = rs.getString("MATRICULE");
                            nom = rs.getString("NOM");
                            prenom = rs.getString("PRENOM");
                            tel = rs.getString("TEL");
                            chargesem = rs.getInt("CHARGESEM");
                            chargeRest = rs.getInt("CHARGEREST");
                            salaire_mensu = rs.getBigDecimal("SALAIRE_MENSU");
                            date_engagement = rs.getDate("DATE_ENGAG").toLocalDate();
                            c = new CoursDB(id_cours, code, intitule, nhs);
                            s = new SalleDB(id_salle, sigle, capacite);
                            e = new EnseignantDB(id_enseignant, matricule, nom, prenom, tel, chargesem, chargeRest, salaire_mensu, date_engagement);
                            i = new InfosDB(id_infos, id_cours, id_enseignant, id_classe, id_salle, c, s, e);
                            li.add(i);
                        }
                    }
                    cl.setListeInfos(li);
                    return cl;
                } else {
                    return null;
                }
            } catch (Exception e) {
                System.out.println("error : " + e);
                return null;
            }
        } catch (SQLException ex) {
            System.out.println("Sqlerror : " + ex);
            return null;
        }
    }

    @Override
    public Classe update(Classe obj) {
        ClasseDB pdb = (ClasseDB) obj;
        String req = "update api_classe set annee=?,specialite=?,nbr_eleves=? where id_classe = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {

            pstm.setInt(4, pdb.getId_classe());
            pstm.setInt(1, obj.getAnnee());
            pstm.setString(2, obj.getSpecialite());
            pstm.setInt(3, obj.getNbrEleves());
            int n = pstm.executeUpdate();
            if (n == 0) {
                return null;
            }
            return read(obj);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean delete(Classe obj) {
        String req = "delete from api_classe where sigle= ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {

            pstm.setString(1, obj.getSigleClasse());
            int n = pstm.executeUpdate();
            if (n == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean add(Classe c, Infos i) {
        String req = "insert into api_info(id_classe, id_enseignant, id_cours,id_salle) values(?,?,?,?)";
        String req2 = "update api_enseignant set chargeRest = ? where id_enseignant = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req); PreparedStatement pstm1 = dbConnect.prepareStatement(req2);) {

            CoursDB cdb = (CoursDB) i.getCours();
            int idc = cdb.getId_cours();

            SalleDB s = (SalleDB) i.getSalle();
            int ids = s.getId_salle();

            ClasseDB cl = (ClasseDB) c;
            int idcl = cl.getId_classe();

            EnseignantDB e = (EnseignantDB) i.getEnseignant();
            int ide = e.getId_enseignant();

            pstm.setInt(1, idcl);
            pstm.setInt(2, ide);
            pstm.setInt(3, idc);
            pstm.setInt(4, ids);

            pstm1.setInt(1, e.getChargeRest());
            pstm1.setInt(2, e.getId_enseignant());
            int n = pstm.executeUpdate();
            int n1 = pstm1.executeUpdate();

            if (n == 0 && n1 == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            System.out.println("Impossible d'insérer les infos - Doublons (ou erreur externe)" + ex);
            return false;
        }
    }

    @Override
    public Set<Classe> readAll() {
        String req = "select * from api_classe order by sigle";
        Set<Classe> cl = new HashSet<>();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req); ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                int id_classe = rs.getInt("ID_CLASSE");
                String sigle = rs.getString("SIGLE");
                int annee = rs.getInt("ANNEE");
                String specialite = rs.getString("SPECIALITE");
                int nbr_eleves = rs.getInt("NBR_ELEVES");
                cl.add(new ClasseDB(id_classe, sigle, annee, specialite, nbr_eleves));
            }
            return cl;
        } catch (Exception ex) {
            System.out.println("error" + ex);
            return cl;
        }
    }
}
