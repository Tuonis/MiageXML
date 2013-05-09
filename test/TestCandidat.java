/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.miage.m1.Candidature.model.Candidat;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kentish
 */
public class TestCandidat {

    private Candidat candidat;

    @Before
    public void setUp() {
    }

    /*@Test
    public void testInsert() {
        try {
            candidat = new Candidat();
            candidat.setNom("Munusami");
            candidat.setPrenom("Kentish");
            candidat.setMail("kentish.munusami@gmail.com");
            candidat.setSituation("master miage");
            candidat.setTelephone("0612345678");
            candidat.setAdresse("34 rue des sculpteurs 93240 Stains");
            candidat.setMdp("ken");
            candidat.insert();
        } catch (SQLException ex) {
            Logger.getLogger(TestCandidat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/

    @Test
    public void testGetById() {
        try {
            candidat = new Candidat();
            Candidat c1=candidat.getById(9);
            assertEquals("Munusami",c1.getNom());
            assertEquals("Kentish",c1.getPrenom());
            assertEquals("0612345678",c1.getTelephone());
            assertEquals("kentish.munusami@gmail.com",c1.getMail());
            assertEquals("master miage",c1.getSituation());
            assertEquals("34 rue des sculpteurs 93240 Stains",c1.getAdresse());
            assertEquals("ken",c1.getMdp());
        } catch (SQLException ex) {
            Logger.getLogger(TestCandidat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testGetByNom() {
        try {
            candidat = new Candidat();
            Candidat c1=candidat.getByNom("Munusami");
            assertEquals("Munusami",c1.getNom());
            assertEquals("Kentish",c1.getPrenom());
            assertEquals("0612345678",c1.getTelephone());
            assertEquals("kentish.munusami@gmail.com",c1.getMail());
            assertEquals("master miage",c1.getSituation());
            assertEquals("34 rue des sculpteurs 93240 Stains",c1.getAdresse());
            assertEquals("ken",c1.getMdp());
        } catch (SQLException ex) {
            Logger.getLogger(TestCandidat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testUpdate() {
        try {
            candidat = new Candidat();
            Candidat c1=candidat.getByNom("Rooney");
            c1.setNom("Rooney jr");
            c1.update();
            Candidat c2=candidat.getByNom("Rooney jr");
             
        } catch (SQLException ex) {
            Logger.getLogger(TestCandidat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    @After
    public void tearDown() {
        //suppression des donnees manipulees dans cette classe de test
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
