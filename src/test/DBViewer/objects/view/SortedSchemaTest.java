/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DBViewer.objects.view;

import DBViewer.objects.model.Table;
import java.util.List;
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
public class SortedSchemaTest {

    public SortedSchemaTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of setTableViewPosition method, of class SortedSchema.
     */
    @Test
    public void testSetTableViewPosition() {
        System.out.println("setTableViewPosition");
        int i = 0;
        String x_pos = "";
        String y_pos = "";
        SchemaPage p = null;
        SortedSchema instance = new SortedSchema();
        instance.setTableViewPosition(i, x_pos, y_pos, p);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHeight method, of class SortedSchema.
     */
    @Test
    public void testGetHeight() {
        System.out.println("getHeight");
        SortedSchema instance = new SortedSchema();
        int expResult = 0;
        int result = instance.getHeight();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setHeight method, of class SortedSchema.
     */
    @Test
    public void testSetHeight() {
        System.out.println("setHeight");
        int height = 0;
        SortedSchema instance = new SortedSchema();
        instance.setHeight(height);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFirstPage method, of class SortedSchema.
     */
    @Test
    public void testGetFirstPage() {
        System.out.println("getFirstPage");
        SortedSchema instance = new SortedSchema();
        SchemaPage expResult = null;
        SchemaPage result = instance.getFirstPage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDefaultTableViews method, of class SortedSchema.
     */
    @Test
    public void testGetDefaultTableViews() {
        System.out.println("getDefaultTableViews");
        SortedSchema instance = new SortedSchema();
        List expResult = null;
        List result = instance.getDefaultTableViews();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTransx method, of class SortedSchema.
     */
    @Test
    public void testGetTransx() {
        System.out.println("getTransx");
        SortedSchema instance = new SortedSchema();
        int expResult = 0;
        int result = instance.getTransx();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTransx method, of class SortedSchema.
     */
    @Test
    public void testSetTransx() {
        System.out.println("setTransx");
        int transx = 0;
        SortedSchema instance = new SortedSchema();
        instance.setTransx(transx);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTransy method, of class SortedSchema.
     */
    @Test
    public void testGetTransy() {
        System.out.println("getTransy");
        SortedSchema instance = new SortedSchema();
        int expResult = 0;
        int result = instance.getTransy();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTransy method, of class SortedSchema.
     */
    @Test
    public void testSetTransy() {
        System.out.println("setTransy");
        int transy = 0;
        SortedSchema instance = new SortedSchema();
        instance.setTransy(transy);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWidth method, of class SortedSchema.
     */
    @Test
    public void testGetWidth() {
        System.out.println("getWidth");
        SortedSchema instance = new SortedSchema();
        int expResult = 0;
        int result = instance.getWidth();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setWidth method, of class SortedSchema.
     */
    @Test
    public void testSetWidth() {
        System.out.println("setWidth");
        int width = 0;
        SortedSchema instance = new SortedSchema();
        instance.setWidth(width);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class SortedSchema.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        SortedSchema instance = new SortedSchema();
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class SortedSchema.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        SortedSchema instance = new SortedSchema();
        instance.setName(name);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDbi method, of class SortedSchema.
     */
    @Test
    public void testGetDbi() {
        System.out.println("getDbi");
        SortedSchema instance = new SortedSchema();
        String expResult = "";
        String result = instance.getDbi();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDbi method, of class SortedSchema.
     */
    @Test
    public void testSetDbi() {
        System.out.println("setDbi");
        String dbi = "";
        SortedSchema instance = new SortedSchema();
        instance.setDbi(dbi);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPages method, of class SortedSchema.
     */
    @Test
    public void testGetPages() {
        System.out.println("getPages");
        SortedSchema instance = new SortedSchema();
        Map expResult = null;
        Map result = instance.getPages();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPages method, of class SortedSchema.
     */
    @Test
    public void testSetPages() {
        System.out.println("setPages");
        Map<UUID, SchemaPage> pages = null;
        SortedSchema instance = new SortedSchema();
        instance.setPages(pages);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTables method, of class SortedSchema.
     */
    @Test
    public void testGetTables() {
        System.out.println("getTables");
        SortedSchema instance = new SortedSchema();
        Map expResult = null;
        Map result = instance.getTables();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTables method, of class SortedSchema.
     */
    @Test
    public void testSetTables() {
        System.out.println("setTables");
        Map<String, Table> tables = null;
        SortedSchema instance = new SortedSchema();
        instance.setTables(tables);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isTablesSorted method, of class SortedSchema.
     */
    @Test
    public void testIsTablesSorted() {
        System.out.println("isTablesSorted");
        SortedSchema instance = new SortedSchema();
        boolean expResult = false;
        boolean result = instance.isTablesSorted();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTablesSorted method, of class SortedSchema.
     */
    @Test
    public void testSetTablesSorted() {
        System.out.println("setTablesSorted");
        boolean tablesSorted = false;
        SortedSchema instance = new SortedSchema();
        instance.setTablesSorted(tablesSorted);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNumTables method, of class SortedSchema.
     */
    @Test
    public void testGetNumTables() {
        System.out.println("getNumTables");
        SortedSchema instance = new SortedSchema();
        int expResult = 0;
        int result = instance.getNumTables();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}