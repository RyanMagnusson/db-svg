/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DBViewer.models;

import java.sql.Connection;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author derrick.bowen
 */
public class JdbcMainDAOTest {

    public JdbcMainDAOTest() {
    }

//    @BeforeClass
//    public static void setUpClass() throws Exception {
//    }
//
//    @AfterClass
//    public static void tearDownClass() throws Exception {
//    }

    /**
     * Test of getDefaultConnection method, of class JdbcMainDAO.
     */
    @Test
    public void testGetDefaultConnection() {
        System.out.println("getDefaultConnection");
        JdbcMainDAO instance = new JdbcMainDAO();
        Connection expResult = null;
        Connection result = instance.getDefaultConnection();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTables method, of class JdbcMainDAO.
     */
    @Test
    public void testGetTables() throws Exception {
        System.out.println("getTables");
        Connection conn = null;
        String schemaName = "";
        JdbcMainDAO instance = new JdbcMainDAO();
        Map expResult = null;
        Map result = instance.getTables(conn, schemaName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}