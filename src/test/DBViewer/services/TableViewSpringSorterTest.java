/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DBViewer.services;

import DBViewer.models.InternalDataDAO;
import DBViewer.objects.view.SchemaPage;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author derrick.bowen
 */
public class TableViewSpringSorterTest {

    public TableViewSpringSorterTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of SortPage method, of class TableViewSpringSorter.
     */
    @Test
    public void testSortPage() {
        System.out.println("SortPage");
        SchemaPage currentPage = null;
        boolean resort = false;
        TableViewSpringSorter instance = null;
        List expResult = null;
        List result = instance.SortPage(currentPage, resort);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calcLines method, of class TableViewSpringSorter.
     */
    @Test
    public void testCalcLines() {
        System.out.println("calcLines");
        SchemaPage page = null;
        TableViewSpringSorter instance = null;
        List expResult = null;
        List result = instance.calcLines(page);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIDAO method, of class TableViewSpringSorter.
     */
    @Test
    public void testGetIDAO() {
        System.out.println("getIDAO");
        TableViewSpringSorter instance = null;
        InternalDataDAO expResult = null;
        InternalDataDAO result = instance.getIDAO();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setIDAO method, of class TableViewSpringSorter.
     */
    @Test
    public void testSetIDAO() {
        System.out.println("setIDAO");
        InternalDataDAO iDAO = null;
        TableViewSpringSorter instance = null;
        instance.setIDAO(iDAO);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}