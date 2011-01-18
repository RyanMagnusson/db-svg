/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DBViewer.objects.model;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author derrick.bowen
 */
public class PrimaryForeignKeyTest {

    public PrimaryForeignKeyTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of isAutoIncrement method, of class PrimaryForeignKey.
     */
    @Test
    public void testIsAutoIncrement() {
        System.out.println("isAutoIncrement");
        PrimaryForeignKey instance = new PrimaryForeignKey();
        boolean expResult = false;
        boolean result = instance.isAutoIncrement();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAutoIncrement method, of class PrimaryForeignKey.
     */
    @Test
    public void testSetAutoIncrement() {
        System.out.println("setAutoIncrement");
        boolean autoIncrement = false;
        PrimaryForeignKey instance = new PrimaryForeignKey();
        instance.setAutoIncrement(autoIncrement);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}