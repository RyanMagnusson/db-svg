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
public class PrimaryKeyObjectTest {

    public PrimaryKeyObjectTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of isAutoIncrement method, of class PrimaryKeyObject.
     */
    @Test
    public void testIsAutoIncrement() {
        System.out.println("isAutoIncrement");
        PrimaryKeyObject instance = new PrimaryKeyObject();
        boolean expResult = false;
        boolean result = instance.isAutoIncrement();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAutoIncrement method, of class PrimaryKeyObject.
     */
    @Test
    public void testSetAutoIncrement() {
        System.out.println("setAutoIncrement");
        boolean autoIncrement = false;
        PrimaryKeyObject instance = new PrimaryKeyObject();
        instance.setAutoIncrement(autoIncrement);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cloneTo method, of class PrimaryKeyObject.
     */
    @Test
    public void testCloneTo() {
        System.out.println("cloneTo");
        PrimaryKey pk = null;
        PrimaryKeyObject instance = new PrimaryKeyObject();
        PrimaryKey expResult = null;
        PrimaryKey result = instance.cloneTo(pk);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}