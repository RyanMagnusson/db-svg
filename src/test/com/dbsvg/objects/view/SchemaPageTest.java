/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dbsvg.objects.view;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author derrick.bowen
 */
public class SchemaPageTest {

	SchemaPage page;
	UUID pageId = UUID.randomUUID();

	@Before
	public void setUp() throws Exception {
		page = new SchemaPage(pageId);
	}

	/**
	 * Test of getId method, of class SchemaPage.
	 */
	@Test
	public void testGetId() {
		assertEquals(pageId, page.getId());
	}

	/**
	 * Test of calcDimensions method, of class SchemaPage.
	 */
	// @Test
	// public void testCalcDimensions() {
	// SchemaPage instance = new SchemaPage();
	// instance.calcDimensions();
	// // TODO review the generated test code and remove the default call to
	// fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setTableViewPosition method, of class SchemaPage.
	// */
	// @Test
	// public void testSetTableViewPosition() {
	// int i = 0;
	// String x_pos = "";
	// String y_pos = "";
	// SchemaPage instance = new SchemaPage();
	// instance.setTableViewPosition(i, x_pos, y_pos);
	// // TODO review the generated test code and remove the default call to
	// fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getTableViews method, of class SchemaPage.
	// */
	// @Test
	// public void testGetTableViews() {
	// SchemaPage instance = new SchemaPage();
	// List expResult = null;
	// List result = instance.getTableViews();
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setTableViews method, of class SchemaPage.
	// */
	// @Test
	// public void testSetTableViews() {
	// List<TableView> tableViews = null;
	// SchemaPage instance = new SchemaPage();
	// instance.setTableViews(tableViews);
	// // TODO review the generated test code and remove the default call to
	// fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setId method, of class SchemaPage.
	// */
	// @Test
	// public void testSetId_UUID() {
	// UUID id = null;
	// SchemaPage instance = new SchemaPage();
	// instance.setId(id);
	// // TODO review the generated test code and remove the default call to
	// fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setId method, of class SchemaPage.
	// */
	// @Test
	// public void testSetId_String() {
	// String id = "";
	// SchemaPage instance = new SchemaPage();
	// instance.setId(id);
	// // TODO review the generated test code and remove the default call to
	// fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getOrderid method, of class SchemaPage.
	// */
	// @Test
	// public void testGetOrderid() {
	// SchemaPage instance = new SchemaPage();
	// int expResult = 0;
	// int result = instance.getOrderid();
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setOrderid method, of class SchemaPage.
	// */
	// @Test
	// public void testSetOrderid() {
	// int orderid = 0;
	// SchemaPage instance = new SchemaPage();
	// instance.setOrderid(orderid);
	// // TODO review the generated test code and remove the default call to
	// fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getTitle method, of class SchemaPage.
	// */
	// @Test
	// public void testGetTitle() {
	// SchemaPage instance = new SchemaPage();
	// String expResult = "";
	// String result = instance.getTitle();
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setTitle method, of class SchemaPage.
	// */
	// @Test
	// public void testSetTitle() {
	// String title = "";
	// SchemaPage instance = new SchemaPage();
	// instance.setTitle(title);
	// // TODO review the generated test code and remove the default call to
	// fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getHeight method, of class SchemaPage.
	// */
	// @Test
	// public void testGetHeight() {
	// SchemaPage instance = new SchemaPage();
	// int expResult = 0;
	// int result = instance.getHeight();
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setHeight method, of class SchemaPage.
	// */
	// @Test
	// public void testSetHeight() {
	// int height = 0;
	// SchemaPage instance = new SchemaPage();
	// instance.setHeight(height);
	// // TODO review the generated test code and remove the default call to
	// fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getTransx method, of class SchemaPage.
	// */
	// @Test
	// public void testGetTransx() {
	// SchemaPage instance = new SchemaPage();
	// int expResult = 0;
	// int result = instance.getTransx();
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setTransx method, of class SchemaPage.
	// */
	// @Test
	// public void testSetTransx() {
	// int transx = 0;
	// SchemaPage instance = new SchemaPage();
	// instance.setTransx(transx);
	// // TODO review the generated test code and remove the default call to
	// fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getTransy method, of class SchemaPage.
	// */
	// @Test
	// public void testGetTransy() {
	// SchemaPage instance = new SchemaPage();
	// int expResult = 0;
	// int result = instance.getTransy();
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setTransy method, of class SchemaPage.
	// */
	// @Test
	// public void testSetTransy() {
	// int transy = 0;
	// SchemaPage instance = new SchemaPage();
	// instance.setTransy(transy);
	// // TODO review the generated test code and remove the default call to
	// fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getWidth method, of class SchemaPage.
	// */
	// @Test
	// public void testGetWidth() {
	// SchemaPage instance = new SchemaPage();
	// int expResult = 0;
	// int result = instance.getWidth();
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setWidth method, of class SchemaPage.
	// */
	// @Test
	// public void testSetWidth() {
	// int width = 0;
	// SchemaPage instance = new SchemaPage();
	// instance.setWidth(width);
	// // TODO review the generated test code and remove the default call to
	// fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getSchema method, of class SchemaPage.
	// */
	// @Test
	// public void testGetSchema() {
	// SchemaPage instance = new SchemaPage();
	// SortedSchema expResult = null;
	// SortedSchema result = instance.getSchema();
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setSchema method, of class SchemaPage.
	// */
	// @Test
	// public void testSetSchema() {
	// SortedSchema schema = null;
	// SchemaPage instance = new SchemaPage();
	// instance.setSchema(schema);
	// // TODO review the generated test code and remove the default call to
	// fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getLines method, of class SchemaPage.
	// */
	// @Test
	// public void testGetLines() {
	// SchemaPage instance = new SchemaPage();
	// List expResult = null;
	// List result = instance.getLines();
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setLines method, of class SchemaPage.
	// */
	// @Test
	// public void testSetLines() {
	// List<LinkLine> lines = null;
	// SchemaPage instance = new SchemaPage();
	// instance.setLines(lines);
	// // TODO review the generated test code and remove the default call to
	// fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of areTableViewsClean method, of class SchemaPage.
	// */
	// @Test
	// public void testAreTableViewsClean() {
	// SchemaPage instance = new SchemaPage();
	// boolean expResult = false;
	// boolean result = instance.areTableViewsClean();
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of compareTo method, of class SchemaPage.
	// */
	// @Test
	// public void testCompareTo() {
	// SchemaPage o = null;
	// SchemaPage instance = new SchemaPage();
	// int expResult = 0;
	// int result = instance.compareTo(o);
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of contains method, of class SchemaPage.
	// */
	// @Test
	// public void testContains() {
	// Table t = null;
	// SchemaPage instance = new SchemaPage();
	// boolean expResult = false;
	// boolean result = instance.contains(t);
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of isSorted method, of class SchemaPage.
	// */
	// @Test
	// public void testIsSorted() {
	// SchemaPage instance = new SchemaPage();
	// Boolean expResult = null;
	// Boolean result = instance.isSorted();
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setSorted method, of class SchemaPage.
	// */
	// @Test
	// public void testSetSorted() {
	// Boolean sorted = null;
	// SchemaPage instance = new SchemaPage();
	// instance.setSorted(sorted);
	// // TODO review the generated test code and remove the default call to
	// fail.
	// fail("The test case is a prototype.");
	// }

}