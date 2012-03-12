/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dbsvg.objects.view;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dbsvg.objects.model.Column;
import com.dbsvg.objects.model.ForeignKey;
import com.dbsvg.objects.model.Table;
import com.dbsvg.objects.view.LinkLine;
import com.dbsvg.objects.view.SchemaPage;
import com.dbsvg.objects.view.TableView;


/**
 * 
 * @author derrick.bowen
 */
public class LinkLineTest {

	LinkLine instance;

	@Mock
	Table t;
	@Mock
	TableView tv;

	@Mock
	Table t_foreign;
	@Mock
	TableView tv_foreign;

	@Mock
	ForeignKey fk;
	@Mock
	Column fkColumn;
	@Mock
	SchemaPage page;
	UUID pageId = UUID.randomUUID();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		when(page.getId()).thenReturn(pageId);

		when(fk.getReference()).thenReturn(fkColumn);
		when(fkColumn.getTable()).thenReturn(t_foreign);

		Map<UUID, TableView> tableViews = new HashMap<UUID, TableView>();
		tableViews.put(pageId, tv);
		when(t.getTablePageViews()).thenReturn(tableViews);

		Map<UUID, TableView> foreignTableViews = new HashMap<UUID, TableView>();
		foreignTableViews.put(pageId, tv_foreign);
		when(t_foreign.getTablePageViews()).thenReturn(foreignTableViews);

		instance = new LinkLine(t, fk, page);
	}

	/**
	 * Test of getPage method, of class LinkLine.
	 */
	@Test
	public void testGetPage() {
		SchemaPage result = instance.getPage();
		assertEquals(page, result);
	}

	// /**
	// * Test of recalculateLine method, of class LinkLine.
	// */
	// @Test
	// public void testRecalculateLine() {
	// System.out.println("recalculateLine");
	// LinkLine instance = null;
	// List expResult = null;
	// List result = instance.recalculateLine();
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }

	// /**
	// * Test of getForeignkey method, of class LinkLine.
	// */
	// @Test
	// public void testGetForeignkey() {
	// System.out.println("getForeignkey");
	// LinkLine instance = null;
	// ForeignKey expResult = null;
	// ForeignKey result = instance.getForeignkey();
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setForeignkey method, of class LinkLine.
	// */
	// @Test
	// public void testSetForeignkey() {
	// System.out.println("setForeignkey");
	// ForeignKey foreignkey = null;
	// LinkLine instance = null;
	// instance.setForeignkey(foreignkey);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getStartingTable method, of class LinkLine.
	// */
	// @Test
	// public void testGetStartingTable() {
	// System.out.println("getStartingTable");
	// LinkLine instance = null;
	// Table expResult = null;
	// Table result = instance.getStartingTable();
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setStartingTable method, of class LinkLine.
	// */
	// @Test
	// public void testSetStartingTable() {
	// System.out.println("setStartingTable");
	// Table referencedTable = null;
	// LinkLine instance = null;
	// instance.setStartingTable(referencedTable);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getX1 method, of class LinkLine.
	// */
	// @Test
	// public void testGetX1() {
	// System.out.println("getX1");
	// LinkLine instance = null;
	// double expResult = 0.0;
	// double result = instance.getX1();
	// assertEquals(expResult, result, 0.0);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setX1 method, of class LinkLine.
	// */
	// @Test
	// public void testSetX1() {
	// System.out.println("setX1");
	// double x1 = 0.0;
	// LinkLine instance = null;
	// instance.setX1(x1);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getX2 method, of class LinkLine.
	// */
	// @Test
	// public void testGetX2() {
	// System.out.println("getX2");
	// LinkLine instance = null;
	// double expResult = 0.0;
	// double result = instance.getX2();
	// assertEquals(expResult, result, 0.0);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setX2 method, of class LinkLine.
	// */
	// @Test
	// public void testSetX2() {
	// System.out.println("setX2");
	// double x2 = 0.0;
	// LinkLine instance = null;
	// instance.setX2(x2);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getY1 method, of class LinkLine.
	// */
	// @Test
	// public void testGetY1() {
	// System.out.println("getY1");
	// LinkLine instance = null;
	// double expResult = 0.0;
	// double result = instance.getY1();
	// assertEquals(expResult, result, 0.0);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setY1 method, of class LinkLine.
	// */
	// @Test
	// public void testSetY1() {
	// System.out.println("setY1");
	// double y1 = 0.0;
	// LinkLine instance = null;
	// instance.setY1(y1);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getY2 method, of class LinkLine.
	// */
	// @Test
	// public void testGetY2() {
	// System.out.println("getY2");
	// LinkLine instance = null;
	// double expResult = 0.0;
	// double result = instance.getY2();
	// assertEquals(expResult, result, 0.0);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setY2 method, of class LinkLine.
	// */
	// @Test
	// public void testSetY2() {
	// System.out.println("setY2");
	// double y2 = 0.0;
	// LinkLine instance = null;
	// instance.setY2(y2);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getAngle method, of class LinkLine.
	// */
	// @Test
	// public void testGetAngle() {
	// System.out.println("getAngle");
	// LinkLine instance = null;
	// double expResult = 0.0;
	// double result = instance.getAngle();
	// assertEquals(expResult, result, 0.0);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setAngle method, of class LinkLine.
	// */
	// @Test
	// public void testSetAngle() {
	// System.out.println("setAngle");
	// double angle = 0.0;
	// LinkLine instance = null;
	// instance.setAngle(angle);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getLength method, of class LinkLine.
	// */
	// @Test
	// public void testGetLength() {
	// System.out.println("getLength");
	// LinkLine instance = null;
	// double expResult = 0.0;
	// double result = instance.getLength();
	// assertEquals(expResult, result, 0.0);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setLength method, of class LinkLine.
	// */
	// @Test
	// public void testSetLength() {
	// System.out.println("setLength");
	// double length = 0.0;
	// LinkLine instance = null;
	// instance.setLength(length);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getXa1 method, of class LinkLine.
	// */
	// @Test
	// public void testGetXa1() {
	// System.out.println("getXa1");
	// LinkLine instance = null;
	// double expResult = 0.0;
	// double result = instance.getXa1();
	// assertEquals(expResult, result, 0.0);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setXa1 method, of class LinkLine.
	// */
	// @Test
	// public void testSetXa1() {
	// System.out.println("setXa1");
	// double xa1 = 0.0;
	// LinkLine instance = null;
	// instance.setXa1(xa1);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getXa2 method, of class LinkLine.
	// */
	// @Test
	// public void testGetXa2() {
	// System.out.println("getXa2");
	// LinkLine instance = null;
	// double expResult = 0.0;
	// double result = instance.getXa2();
	// assertEquals(expResult, result, 0.0);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setXa2 method, of class LinkLine.
	// */
	// @Test
	// public void testSetXa2() {
	// System.out.println("setXa2");
	// double xa2 = 0.0;
	// LinkLine instance = null;
	// instance.setXa2(xa2);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getXa3 method, of class LinkLine.
	// */
	// @Test
	// public void testGetXa3() {
	// System.out.println("getXa3");
	// LinkLine instance = null;
	// double expResult = 0.0;
	// double result = instance.getXa3();
	// assertEquals(expResult, result, 0.0);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setXa3 method, of class LinkLine.
	// */
	// @Test
	// public void testSetXa3() {
	// System.out.println("setXa3");
	// double xa3 = 0.0;
	// LinkLine instance = null;
	// instance.setXa3(xa3);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getYa1 method, of class LinkLine.
	// */
	// @Test
	// public void testGetYa1() {
	// System.out.println("getYa1");
	// LinkLine instance = null;
	// double expResult = 0.0;
	// double result = instance.getYa1();
	// assertEquals(expResult, result, 0.0);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setYa1 method, of class LinkLine.
	// */
	// @Test
	// public void testSetYa1() {
	// System.out.println("setYa1");
	// double ya1 = 0.0;
	// LinkLine instance = null;
	// instance.setYa1(ya1);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getYa2 method, of class LinkLine.
	// */
	// @Test
	// public void testGetYa2() {
	// System.out.println("getYa2");
	// LinkLine instance = null;
	// double expResult = 0.0;
	// double result = instance.getYa2();
	// assertEquals(expResult, result, 0.0);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setYa2 method, of class LinkLine.
	// */
	// @Test
	// public void testSetYa2() {
	// System.out.println("setYa2");
	// double ya2 = 0.0;
	// LinkLine instance = null;
	// instance.setYa2(ya2);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getYa3 method, of class LinkLine.
	// */
	// @Test
	// public void testGetYa3() {
	// System.out.println("getYa3");
	// LinkLine instance = null;
	// double expResult = 0.0;
	// double result = instance.getYa3();
	// assertEquals(expResult, result, 0.0);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setYa3 method, of class LinkLine.
	// */
	// @Test
	// public void testSetYa3() {
	// System.out.println("setYa3");
	// double ya3 = 0.0;
	// LinkLine instance = null;
	// instance.setYa3(ya3);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getEndRadius method, of class LinkLine.
	// */
	// @Test
	// public void testGetEndRadius() {
	// System.out.println("getEndRadius");
	// LinkLine instance = null;
	// double expResult = 0.0;
	// double result = instance.getEndRadius();
	// assertEquals(expResult, result, 0.0);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }

	// /**
	// * Test of setPage method, of class LinkLine.
	// */
	// @Test
	// public void testSetPage() {
	// System.out.println("setPage");
	// SchemaPage page = null;
	// LinkLine instance = null;
	// instance.setPage(page);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }

}