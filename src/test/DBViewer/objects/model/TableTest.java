/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DBViewer.objects.model;

import DBViewer.objects.view.SchemaPage;
import DBViewer.objects.view.TableView;
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
public class TableTest {

    public TableTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of getId method, of class Table.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Table instance = new Table();
        UUID expResult = null;
        UUID result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setId method, of class Table.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        UUID id = null;
        Table instance = new Table();
        instance.setId(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class Table.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Table instance = new Table();
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class Table.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        Table instance = new Table();
        instance.setName(name);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSchemaName method, of class Table.
     */
    @Test
    public void testGetSchemaName() {
        System.out.println("getSchemaName");
        Table instance = new Table();
        String expResult = "";
        String result = instance.getSchemaName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSchemaName method, of class Table.
     */
    @Test
    public void testSetSchemaName() {
        System.out.println("setSchemaName");
        String schemaName = "";
        Table instance = new Table();
        instance.setSchemaName(schemaName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getColumns method, of class Table.
     */
    @Test
    public void testGetColumns() {
        System.out.println("getColumns");
        Table instance = new Table();
        Map expResult = null;
        Map result = instance.getColumns();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setColumns method, of class Table.
     */
    @Test
    public void testSetColumns() {
        System.out.println("setColumns");
        Map<String, Column> columns = null;
        Table instance = new Table();
        instance.setColumns(columns);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getForeignKeys method, of class Table.
     */
    @Test
    public void testGetForeignKeys() {
        System.out.println("getForeignKeys");
        Table instance = new Table();
        Map expResult = null;
        Map result = instance.getForeignKeys();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setForeignKeys method, of class Table.
     */
    @Test
    public void testSetForeignKeys() {
        System.out.println("setForeignKeys");
        Map<String, ForeignKey> foreignKeys = null;
        Table instance = new Table();
        instance.setForeignKeys(foreignKeys);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReferencingTables method, of class Table.
     */
    @Test
    public void testGetReferencingTables() {
        System.out.println("getReferencingTables");
        Table instance = new Table();
        Map expResult = null;
        Map result = instance.getReferencingTables();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setReferencingTables method, of class Table.
     */
    @Test
    public void testSetReferencingTables() {
        System.out.println("setReferencingTables");
        Map<String, Table> referencingTables = null;
        Table instance = new Table();
        instance.setReferencingTables(referencingTables);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPrimaryKeys method, of class Table.
     */
    @Test
    public void testGetPrimaryKeys() {
        System.out.println("getPrimaryKeys");
        Table instance = new Table();
        Map expResult = null;
        Map result = instance.getPrimaryKeys();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPrimaryKeys method, of class Table.
     */
    @Test
    public void testSetPrimaryKeys() {
        System.out.println("setPrimaryKeys");
        Map<String, PrimaryKey> primaryKeys = null;
        Table instance = new Table();
        instance.setPrimaryKeys(primaryKeys);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateWidth method, of class Table.
     */
    @Test
    public void testGenerateWidth() {
        System.out.println("generateWidth");
        Table instance = new Table();
        int expResult = 0;
        int result = instance.generateWidth();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTablePageViews method, of class Table.
     */
    @Test
    public void testGetTablePageViews() {
        System.out.println("getTablePageViews");
        Table instance = new Table();
        Map expResult = null;
        Map result = instance.getTablePageViews();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addTableViewForPage method, of class Table.
     */
    @Test
    public void testAddTableViewForPage_TableView_UUID() {
        System.out.println("addTableViewForPage");
        TableView tv = null;
        UUID pageId = null;
        Table instance = new Table();
        instance.addTableViewForPage(tv, pageId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addTableViewForPage method, of class Table.
     */
    @Test
    public void testAddTableViewForPage_TableView_SchemaPage() {
        System.out.println("addTableViewForPage");
        TableView tv = null;
        SchemaPage page = null;
        Table instance = new Table();
        instance.addTableViewForPage(tv, page);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHeight method, of class Table.
     */
    @Test
    public void testGetHeight() {
        System.out.println("getHeight");
        Table instance = new Table();
        int expResult = 0;
        int result = instance.getHeight();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setHeight method, of class Table.
     */
    @Test
    public void testSetHeight() {
        System.out.println("setHeight");
        int height = 0;
        Table instance = new Table();
        instance.setHeight(height);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWidth method, of class Table.
     */
    @Test
    public void testGetWidth() {
        System.out.println("getWidth");
        Table instance = new Table();
        int expResult = 0;
        int result = instance.getWidth();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setWidth method, of class Table.
     */
    @Test
    public void testSetWidth() {
        System.out.println("setWidth");
        int width = 0;
        Table instance = new Table();
        instance.setWidth(width);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of compareTo method, of class Table.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        Table o = null;
        Table instance = new Table();
        int expResult = 0;
        int result = instance.compareTo(o);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}