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
public class ForeignKeyTest {

    public ForeignKeyTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of cloneTo method, of class ForeignKey.
     */
    @Test
    public void testCloneTo() {
        System.out.println("cloneTo");
        ForeignKey fk = null;
        ForeignKey instance = new ForeignKey();
        ForeignKey expResult = null;
        ForeignKey result = instance.cloneTo(fk);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDeleteRule method, of class ForeignKey.
     */
    @Test
    public void testGetDeleteRule() {
        System.out.println("getDeleteRule");
        ForeignKey instance = new ForeignKey();
        String expResult = "";
        String result = instance.getDeleteRule();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDeleteRule method, of class ForeignKey.
     */
    @Test
    public void testSetDeleteRule() {
        System.out.println("setDeleteRule");
        String deleteRule = "";
        ForeignKey instance = new ForeignKey();
        instance.setDeleteRule(deleteRule);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUpdateRule method, of class ForeignKey.
     */
    @Test
    public void testGetUpdateRule() {
        System.out.println("getUpdateRule");
        ForeignKey instance = new ForeignKey();
        String expResult = "";
        String result = instance.getUpdateRule();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setUpdateRule method, of class ForeignKey.
     */
    @Test
    public void testSetUpdateRule() {
        System.out.println("setUpdateRule");
        String updateRule = "";
        ForeignKey instance = new ForeignKey();
        instance.setUpdateRule(updateRule);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReference method, of class ForeignKey.
     */
    @Test
    public void testGetReference() {
        System.out.println("getReference");
        ForeignKey instance = new ForeignKey();
        Column expResult = null;
        Column result = instance.getReference();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setReference method, of class ForeignKey.
     */
    @Test
    public void testSetReference() {
        System.out.println("setReference");
        Column reference = null;
        ForeignKey instance = new ForeignKey();
        instance.setReference(reference);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReferencedColumn method, of class ForeignKey.
     */
    @Test
    public void testGetReferencedColumn() {
        System.out.println("getReferencedColumn");
        ForeignKey instance = new ForeignKey();
        String expResult = "";
        String result = instance.getReferencedColumn();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setReferencedColumn method, of class ForeignKey.
     */
    @Test
    public void testSetReferencedColumn() {
        System.out.println("setReferencedColumn");
        String referencedColumn = "";
        ForeignKey instance = new ForeignKey();
        instance.setReferencedColumn(referencedColumn);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReferencedTable method, of class ForeignKey.
     */
    @Test
    public void testGetReferencedTable() {
        System.out.println("getReferencedTable");
        ForeignKey instance = new ForeignKey();
        String expResult = "";
        String result = instance.getReferencedTable();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setReferencedTable method, of class ForeignKey.
     */
    @Test
    public void testSetReferencedTable() {
        System.out.println("setReferencedTable");
        String referencedTable = "";
        ForeignKey instance = new ForeignKey();
        instance.setReferencedTable(referencedTable);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}