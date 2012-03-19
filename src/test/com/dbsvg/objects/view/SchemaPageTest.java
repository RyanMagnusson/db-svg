/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dbsvg.objects.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * 
 * @author derrick.bowen
 */
public class SchemaPageTest {

	SchemaPage instance;
	UUID pageId = UUID.randomUUID();

	SchemaPage samePage;
	SchemaPage differentPageName;
	SchemaPage differentPageOrderId;
	SchemaPage differentPageId;
	SchemaPage differentPageOrderIdTrumpsPageName;
	UUID diffpageId = UUID.randomUUID();

	@Mock
	TableView tableView;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		instance = new SchemaPage(pageId);
		instance.setTitle("page");
		List<TableView> tableViews = new ArrayList<TableView>();
		tableViews.add(tableView);
		instance.setTableViews(tableViews);

		samePage = new SchemaPage(pageId);
		samePage.setTitle("page");

		differentPageName = new SchemaPage(pageId);
		differentPageName.setTitle("page2");

		differentPageOrderId = new SchemaPage(pageId);
		differentPageOrderId.setTitle("page");
		differentPageOrderId.setOrderid(10);

		differentPageId = new SchemaPage(diffpageId);
		differentPageId.setTitle("page");

		differentPageOrderIdTrumpsPageName = new SchemaPage(pageId);
		differentPageOrderIdTrumpsPageName.setTitle("apage");
		differentPageOrderIdTrumpsPageName.setOrderid(10);
	}

	/**
	 * Test of getId method, of class SchemaPage.
	 */
	@Test
	public void testGetId() {
		assertEquals(pageId, instance.getId());
	}

	/**
	 * Test of compareTo method, of class TableView.
	 */
	@Test
	public void testCompareTo() {

		assertEquals(0, instance.compareTo(samePage));
		assertEquals(0, samePage.compareTo(instance));

		assertEquals(-1, instance.compareTo(differentPageName));
		assertEquals(1, differentPageName.compareTo(instance));

		assertEquals(-1, instance.compareTo(differentPageOrderId));
		assertEquals(1, differentPageOrderId.compareTo(instance));

		assertEquals(pageId.compareTo(diffpageId), instance.compareTo(differentPageId));
		assertEquals(diffpageId.compareTo(pageId), differentPageId.compareTo(instance));

		assertEquals(-1, instance.compareTo(differentPageOrderIdTrumpsPageName));
		assertEquals(1, differentPageOrderIdTrumpsPageName.compareTo(instance));
	}

	/**
	 * Test of compareTo method, of class Table.
	 */
	@Test
	public void testEquals() {

		assertTrue(instance.equals(samePage));
		assertTrue(instance.equals(instance));
		assertTrue(samePage.equals(instance));

		assertFalse(instance.equals(differentPageName));
		assertFalse(differentPageName.equals(instance));

		assertFalse(instance.equals(differentPageOrderId));
		assertFalse(differentPageOrderId.equals(instance));

		assertFalse(instance.equals(differentPageId));
		assertFalse(differentPageId.equals(instance));

		assertFalse(instance.equals(null));
	}

	/**
	 * Test of compareTo method, of class Table.
	 */
	@Test
	public void testHash() {

		HashSet<SchemaPage> set = new HashSet<SchemaPage>();
		set.add(instance);

		assertTrue(set.contains(instance));
		assertTrue(set.contains(samePage));
		assertFalse(set.contains(differentPageName));
		assertFalse(set.contains(differentPageOrderId));
		assertFalse(set.contains(differentPageId));
	}

	/**
	 * Test of setTableViewPosition method, of class SchemaPage.
	 */
	@Test
	public void testSetTableViewPosition() {
		int i = 0;
		double x_pos = 200;
		double y_pos = 100;
		instance.setTableViewPosition(i, x_pos, y_pos);

		verify(tableView).setX(x_pos);
		verify(tableView).setY(y_pos);
		verify(tableView).setSorted();
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