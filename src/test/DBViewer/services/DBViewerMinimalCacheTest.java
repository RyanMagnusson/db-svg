/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DBViewer.services;

import DBViewer.models.ConnectionWrapper;
import DBViewer.objects.view.SchemaPage;
import java.util.Map;
import java.util.UUID;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author derrick.bowen
 */
public class DBViewerMinimalCacheTest {

    public DBViewerMinimalCacheTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of getConnection method, of class DBViewerMinimalCache.
     */
    @Test
    public void testGetConnection() {
        System.out.println("getConnection");
        String dbi = "";
        DBViewerMinimalCache instance = new DBViewerMinimalCache();
        ConnectionWrapper expResult = null;
        ConnectionWrapper result = instance.getConnection(dbi);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeConnection method, of class DBViewerMinimalCache.
     */
    @Test
    public void testRemoveConnection() {
        System.out.println("removeConnection");
        String dbi = "";
        DBViewerMinimalCache instance = new DBViewerMinimalCache();
        instance.removeConnection(dbi);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAllConnections method, of class DBViewerMinimalCache.
     */
    @Test
    public void testSetAllConnections() {
        System.out.println("setAllConnections");
        Map<String, ConnectionWrapper> cwmap = null;
        DBViewerMinimalCache instance = new DBViewerMinimalCache();
        instance.setAllConnections(cwmap);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of putConnection method, of class DBViewerMinimalCache.
     */
    @Test
    public void testPutConnection() {
        System.out.println("putConnection");
        String dbi = "";
        ConnectionWrapper cw = null;
        DBViewerMinimalCache instance = new DBViewerMinimalCache();
        instance.putConnection(dbi, cw);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllConnections method, of class DBViewerMinimalCache.
     */
    @Test
    public void testGetAllConnections() {
        System.out.println("getAllConnections");
        DBViewerMinimalCache instance = new DBViewerMinimalCache();
        Map expResult = null;
        Map result = instance.getAllConnections();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSchemaPage method, of class DBViewerMinimalCache.
     */
    @Test
    public void testGetSchemaPage() {
        System.out.println("getSchemaPage");
        UUID pageId = null;
        DBViewerMinimalCache instance = new DBViewerMinimalCache();
        SchemaPage expResult = null;
        SchemaPage result = instance.getSchemaPage(pageId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of putSchemaPage method, of class DBViewerMinimalCache.
     */
    @Test
    public void testPutSchemaPage() {
        System.out.println("putSchemaPage");
        SchemaPage sPage = null;
        DBViewerMinimalCache instance = new DBViewerMinimalCache();
        instance.putSchemaPage(sPage);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}