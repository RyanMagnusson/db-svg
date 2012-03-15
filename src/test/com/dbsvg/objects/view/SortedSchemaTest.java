/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dbsvg.objects.view;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dbsvg.models.ConnectionWrapper;

/**
 * 
 * @author derrick.bowen
 */
public class SortedSchemaTest {
	SortedSchema instance;

	@Mock
	ConnectionWrapper currentConn;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		instance = new SortedSchema(currentConn);
	}

	/**
	 * Test of getHeight method, of class SortedSchema.
	 */
	@Test
	public void testGetSetHeight() {
		int height = 100;
		instance.setHeight(height);
		int result = instance.getHeight();
		assertEquals(height, result);
	}

	// /**
	// * Test of setTableViewPosition method, of class SortedSchema.
	// */
	// @Test
	// public void testSetTableViewPosition() {
	// int i = 0;
	// String x_pos = "";
	// String y_pos = "";
	// SchemaPage p = null;
	// SortedSchema instance = new SortedSchema();
	// instance.setTableViewPosition(i, x_pos, y_pos, p);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	// /**
	// * Test of getFirstPage method, of class SortedSchema.
	// */
	// @Test
	// public void testGetFirstPage() {
	// SortedSchema instance = new SortedSchema();
	// SchemaPage expResult = null;
	// SchemaPage result = instance.getFirstPage();
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getDefaultTableViews method, of class SortedSchema.
	// */
	// @Test
	// public void testGetDefaultTableViews() {
	// SortedSchema instance = new SortedSchema();
	// List expResult = null;
	// List result = instance.getDefaultTableViews();
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getTransx method, of class SortedSchema.
	// */
	// @Test
	// public void testGetTransx() {
	// SortedSchema instance = new SortedSchema();
	// int expResult = 0;
	// int result = instance.getTransx();
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setTransx method, of class SortedSchema.
	// */
	// @Test
	// public void testSetTransx() {
	// int transx = 0;
	// SortedSchema instance = new SortedSchema();
	// instance.setTransx(transx);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getTransy method, of class SortedSchema.
	// */
	// @Test
	// public void testGetTransy() {
	// SortedSchema instance = new SortedSchema();
	// int expResult = 0;
	// int result = instance.getTransy();
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setTransy method, of class SortedSchema.
	// */
	// @Test
	// public void testSetTransy() {
	// int transy = 0;
	// SortedSchema instance = new SortedSchema();
	// instance.setTransy(transy);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getWidth method, of class SortedSchema.
	// */
	// @Test
	// public void testGetWidth() {
	// SortedSchema instance = new SortedSchema();
	// int expResult = 0;
	// int result = instance.getWidth();
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setWidth method, of class SortedSchema.
	// */
	// @Test
	// public void testSetWidth() {
	// int width = 0;
	// SortedSchema instance = new SortedSchema();
	// instance.setWidth(width);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getName method, of class SortedSchema.
	// */
	// @Test
	// public void testGetName() {
	// SortedSchema instance = new SortedSchema();
	// String expResult = "";
	// String result = instance.getName();
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setName method, of class SortedSchema.
	// */
	// @Test
	// public void testSetName() {
	// String name = "";
	// SortedSchema instance = new SortedSchema();
	// instance.setName(name);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getDbi method, of class SortedSchema.
	// */
	// @Test
	// public void testGetDbi() {
	// SortedSchema instance = new SortedSchema();
	// String expResult = "";
	// String result = instance.getDbi();
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setDbi method, of class SortedSchema.
	// */
	// @Test
	// public void testSetDbi() {
	// String dbi = "";
	// SortedSchema instance = new SortedSchema();
	// instance.setDbi(dbi);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getPages method, of class SortedSchema.
	// */
	// @Test
	// public void testGetPages() {
	// SortedSchema instance = new SortedSchema();
	// Map expResult = null;
	// Map result = instance.getPages();
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setPages method, of class SortedSchema.
	// */
	// @Test
	// public void testSetPages() {
	// Map<UUID, SchemaPage> pages = null;
	// SortedSchema instance = new SortedSchema();
	// instance.setPages(pages);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getTables method, of class SortedSchema.
	// */
	// @Test
	// public void testGetTables() {
	// SortedSchema instance = new SortedSchema();
	// Map expResult = null;
	// Map result = instance.getTables();
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setTables method, of class SortedSchema.
	// */
	// @Test
	// public void testSetTables() {
	// Map<String, Table> tables = null;
	// SortedSchema instance = new SortedSchema();
	// instance.setTables(tables);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of isTablesSorted method, of class SortedSchema.
	// */
	// @Test
	// public void testIsTablesSorted() {
	// SortedSchema instance = new SortedSchema();
	// boolean expResult = false;
	// boolean result = instance.isTablesSorted();
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setTablesSorted method, of class SortedSchema.
	// */
	// @Test
	// public void testSetTablesSorted() {
	// boolean tablesSorted = false;
	// SortedSchema instance = new SortedSchema();
	// instance.setTablesSorted(tablesSorted);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getNumTables method, of class SortedSchema.
	// */
	// @Test
	// public void testGetNumTables() {
	// SortedSchema instance = new SortedSchema();
	// int expResult = 0;
	// int result = instance.getNumTables();
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }

}