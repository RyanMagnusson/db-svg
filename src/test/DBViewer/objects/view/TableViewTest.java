/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DBViewer.objects.view;

import DBViewer.objects.model.Table;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author derrick.bowen
 */
public class TableViewTest {

    public TableViewTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of calcLinksAndRadius method, of class TableView.
     */
    @Test
    public void testCalcLinksAndRadius() {
        System.out.println("calcLinksAndRadius");
        TableView instance = null;
        instance.calcLinksAndRadius();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calcDistance method, of class TableView.
     */
    @Test
    public void testCalcDistance_TableView() {
        System.out.println("calcDistance");
        TableView o = null;
        TableView instance = null;
        double expResult = 0.0;
        double result = instance.calcDistance(o);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calcDistance method, of class TableView.
     */
    @Test
    public void testCalcDistance_int_int() {
        System.out.println("calcDistance");
        int x = 0;
        int y = 0;
        TableView instance = null;
        double expResult = 0.0;
        double result = instance.calcDistance(x, y);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calcDistanceWRadius method, of class TableView.
     */
    @Test
    public void testCalcDistanceWRadius() {
        System.out.println("calcDistanceWRadius");
        TableView o = null;
        TableView instance = null;
        double expResult = 0.0;
        double result = instance.calcDistanceWRadius(o);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calcAngle method, of class TableView.
     */
    @Test
    public void testCalcAngle_TableView() {
        System.out.println("calcAngle");
        TableView o = null;
        TableView instance = null;
        double expResult = 0.0;
        double result = instance.calcAngle(o);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calcAngle method, of class TableView.
     */
    @Test
    public void testCalcAngle_int_int() {
        System.out.println("calcAngle");
        int x = 0;
        int y = 0;
        TableView instance = null;
        double expResult = 0.0;
        double result = instance.calcAngle(x, y);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNumLinks method, of class TableView.
     */
    @Test
    public void testGetNumLinks() {
        System.out.println("getNumLinks");
        TableView instance = null;
        int expResult = 0;
        int result = instance.getNumLinks();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNumLinks method, of class TableView.
     */
    @Test
    public void testSetNumLinks() {
        System.out.println("setNumLinks");
        int numLinks = 0;
        TableView instance = null;
        instance.setNumLinks(numLinks);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRadius method, of class TableView.
     */
    @Test
    public void testGetRadius() {
        System.out.println("getRadius");
        TableView instance = null;
        double expResult = 0.0;
        double result = instance.getRadius();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setRadius method, of class TableView.
     */
    @Test
    public void testSetRadius() {
        System.out.println("setRadius");
        double radius = 0.0;
        TableView instance = null;
        instance.setRadius(radius);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReferencesTo method, of class TableView.
     */
    @Test
    public void testGetReferencesTo() {
        System.out.println("getReferencesTo");
        TableView instance = null;
        List expResult = null;
        List result = instance.getReferencesTo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setReferencesTo method, of class TableView.
     */
    @Test
    public void testSetReferencesTo() {
        System.out.println("setReferencesTo");
        List<TableView> referencesTo = null;
        TableView instance = null;
        instance.setReferencesTo(referencesTo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReferencedBy method, of class TableView.
     */
    @Test
    public void testGetReferencedBy() {
        System.out.println("getReferencedBy");
        TableView instance = null;
        List expResult = null;
        List result = instance.getReferencedBy();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setReferencedBy method, of class TableView.
     */
    @Test
    public void testSetReferencedBy() {
        System.out.println("setReferencedBy");
        List<TableView> referencedBy = null;
        TableView instance = null;
        instance.setReferencedBy(referencedBy);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReferences method, of class TableView.
     */
    @Test
    public void testGetReferences() {
        System.out.println("getReferences");
        TableView instance = null;
        List expResult = null;
        List result = instance.getReferences();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTable method, of class TableView.
     */
    @Test
    public void testGetTable() {
        System.out.println("getTable");
        TableView instance = null;
        Table expResult = null;
        Table result = instance.getTable();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTable method, of class TableView.
     */
    @Test
    public void testSetTable() {
        System.out.println("setTable");
        Table table = null;
        TableView instance = null;
        instance.setTable(table);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getX method, of class TableView.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        TableView instance = null;
        int expResult = 0;
        int result = instance.getX();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setX method, of class TableView.
     */
    @Test
    public void testSetX_int() {
        System.out.println("setX");
        int x = 0;
        TableView instance = null;
        instance.setX(x);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setX method, of class TableView.
     */
    @Test
    public void testSetX_double() {
        System.out.println("setX");
        double x = 0.0;
        TableView instance = null;
        instance.setX(x);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getY method, of class TableView.
     */
    @Test
    public void testGetY() {
        System.out.println("getY");
        TableView instance = null;
        int expResult = 0;
        int result = instance.getY();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setY method, of class TableView.
     */
    @Test
    public void testSetY_int() {
        System.out.println("setY");
        int y = 0;
        TableView instance = null;
        instance.setY(y);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setY method, of class TableView.
     */
    @Test
    public void testSetY_double() {
        System.out.println("setY");
        double y = 0.0;
        TableView instance = null;
        instance.setY(y);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVelocityX method, of class TableView.
     */
    @Test
    public void testGetVelocityX() {
        System.out.println("getVelocityX");
        TableView instance = null;
        double expResult = 0.0;
        double result = instance.getVelocityX();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setVelocityX method, of class TableView.
     */
    @Test
    public void testSetVelocityX() {
        System.out.println("setVelocityX");
        double velocityX = 0.0;
        TableView instance = null;
        instance.setVelocityX(velocityX);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVelocityY method, of class TableView.
     */
    @Test
    public void testGetVelocityY() {
        System.out.println("getVelocityY");
        TableView instance = null;
        double expResult = 0.0;
        double result = instance.getVelocityY();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setVelocityY method, of class TableView.
     */
    @Test
    public void testSetVelocityY() {
        System.out.println("setVelocityY");
        double velocityY = 0.0;
        TableView instance = null;
        instance.setVelocityY(velocityY);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getId method, of class TableView.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        TableView instance = null;
        int expResult = 0;
        int result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setId method, of class TableView.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        int id = 0;
        TableView instance = null;
        instance.setId(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPage method, of class TableView.
     */
    @Test
    public void testGetPage() {
        System.out.println("getPage");
        TableView instance = null;
        SchemaPage expResult = null;
        SchemaPage result = instance.getPage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPage method, of class TableView.
     */
    @Test
    public void testSetPage() {
        System.out.println("setPage");
        SchemaPage page = null;
        TableView instance = null;
        instance.setPage(page);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isDirty method, of class TableView.
     */
    @Test
    public void testIsDirty() {
        System.out.println("isDirty");
        TableView instance = null;
        boolean expResult = false;
        boolean result = instance.isDirty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDirty method, of class TableView.
     */
    @Test
    public void testSetDirty_boolean() {
        System.out.println("setDirty");
        boolean dirty = false;
        TableView instance = null;
        instance.setDirty(dirty);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDirty method, of class TableView.
     */
    @Test
    public void testSetDirty_0args() {
        System.out.println("setDirty");
        TableView instance = null;
        instance.setDirty();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setClean method, of class TableView.
     */
    @Test
    public void testSetClean() {
        System.out.println("setClean");
        TableView instance = null;
        instance.setClean();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of compareTo method, of class TableView.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        TableView o = null;
        TableView instance = null;
        int expResult = 0;
        int result = instance.compareTo(o);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}