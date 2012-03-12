/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dbsvg.objects.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dbsvg.objects.model.Column;
import com.dbsvg.objects.model.ForeignKey;
import com.dbsvg.objects.model.Table;
import com.dbsvg.objects.view.SchemaPage;
import com.dbsvg.objects.view.TableView;


/**
 * 
 * @author derrick.bowen
 */
public class TableViewTest {

	@Mock
	Table table;

	@Mock
	Table table_ref_by;
	@Mock
	Table table_ref_to;

	@Mock
	ForeignKey fk;

	@Mock
	Column fkColumn;

	@Mock
	SchemaPage page;
	UUID pageId = UUID.randomUUID();

	@Mock
	SchemaPage page_o;
	UUID pageOId = UUID.randomUUID();

	TableView instance = null;

	TableView referencedBy = null;
	TableView referencedTo = null;

	double x1 = 100;
	double y1 = 50;
	double r1 = 10;

	double x2 = 50;
	double y2 = 20;
	double r2 = 5;

	List<TableView> referencedByList = new ArrayList<TableView>();

	List<TableView> referencesToList = new ArrayList<TableView>();

	Map<String, ForeignKey> fkeys = null;
	Map<String, Table> referencers = null;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(page.getId()).thenReturn(pageId);

		instance = new TableView(table, page);
		instance.setX(x1);
		instance.setY(y1);
		instance.setRadius(r1);
		instance.setNumLinks(1);
		instance.setClean();

		referencedBy = new TableView(table_ref_by, page);
		referencedBy.setX(x2);
		referencedBy.setY(y2);
		referencedBy.setRadius(r2);
		referencedByList.add(referencedBy);
		referencedBy.setClean();

		Map<UUID, TableView> refByTableViews = new HashMap<UUID, TableView>();
		refByTableViews.put(pageId, referencedBy);
		when(table_ref_by.getTablePageViews()).thenReturn(refByTableViews);

		referencedTo = new TableView(table_ref_to, page);
		referencedTo.setX(x2);
		referencedTo.setY(y2);
		referencedTo.setRadius(r2);
		referencesToList.add(referencedTo);
		referencedTo.setClean();

		Map<UUID, TableView> refToTableViews = new HashMap<UUID, TableView>();
		refToTableViews.put(pageId, referencedTo);
		when(table_ref_to.getTablePageViews()).thenReturn(refToTableViews);

		instance.setReferencedBy(referencedByList);
		instance.setReferencesTo(referencesToList);

		fkeys = new HashMap<String, ForeignKey>();

		fkeys.put("table ref to", fk);
		when(fk.getReference()).thenReturn(fkColumn);
		when(fkColumn.getTable()).thenReturn(table_ref_to);
		when(table.getForeignKeys()).thenReturn(fkeys);
		referencers = new HashMap<String, Table>();
		referencers.put("table ref by", table_ref_by);
		when(table.getReferencingTables()).thenReturn(referencers);
	}

	/**
	 * Test of calcLinksAndRadius method, of class TableView.
	 */
	@Test
	public void testCalcLinksAndRadius_Radius() {

		int height = 30;
		int width = 130;

		when(table.getHeight()).thenReturn(height);
		when(table.getWidth()).thenReturn(width);

		double diagonal = Math.sqrt(Math.pow(height, 2) + Math.pow(width, 2));

		double expected = (11.0 / 20.0) * diagonal;

		instance.calcLinksAndRadius();
		assertEquals(expected, instance.getRadius(), 0.0);

	}

	/**
	 * Test of calcLinksAndRadius method, of class TableView.
	 */
	@Test
	public void testCalcLinksAndRadius_NumLinks() {

		instance.setReferencedBy(new ArrayList<TableView>());
		instance.setReferencesTo(new ArrayList<TableView>());

		int expected = 2;

		instance.calcLinksAndRadius();
		assertEquals(expected, instance.getNumLinks());

	}

	/**
	 * Test of calcDistance method, of class TableView.
	 */
	@Test
	public void testCalcDistance_TableView() {
		double expResult = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
		double result = instance.calcDistance(referencedBy);
		assertEquals(expResult, result, 0.0);
	}

	/**
	 * Test of calcDistance method, of class TableView.
	 */
	@Test
	public void testCalcDistance_int_int() {
		int x = 0;
		int y = 0;
		double expResult = Math.sqrt(Math.pow(x1 - x, 2) + Math.pow(y1 - y, 2));
		double result = instance.calcDistance(x, y);
		assertEquals(expResult, result, 0.0);
	}

	/**
	 * Test of calcDistanceWRadius method, of class TableView.
	 */
	@Test
	public void testCalcDistanceWRadius() {
		double expResult = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)) - r1 - r2;
		double result = instance.calcDistanceWRadius(referencedBy);
		assertEquals(expResult, result, 0.0);
	}

	/**
	 * Test of calcAngle method, of class TableView.
	 */
	@Test
	public void testCalcAngle_TableView() {
		double expResult = Math.atan((y1 - y2) / (x1 - x2));
		double result = instance.calcAngle(referencedBy);
		assertEquals(expResult, result, 0.0);
	}

	/**
	 * Test of calcAngle method, of class TableView.
	 */
	@Test
	public void testCalcAngle_int_int() {
		int x = 0;
		int y = 0;
		double expResult = Math.atan((y1 - y) / (x1 - x));
		double result = instance.calcAngle(x, y);
		assertEquals(expResult, result, 0.0);
	}

	/**
	 * Test of getNumLinks method, of class TableView.
	 */
	@Test
	public void testGetNumLinks() {
		int expResult = 1;
		int result = instance.getNumLinks();
		assertEquals(expResult, result);
	}

	/**
	 * Test of setNumLinks method, of class TableView.
	 */
	@Test
	public void testSetNumLinks() {
		int numLinks = 10;
		instance.setNumLinks(numLinks);
		assertEquals(numLinks, instance.getNumLinks());
		assertFalse(instance.isDirty());
	}

	/**
	 * Test of getRadius method, of class TableView.
	 */
	@Test
	public void testGetRadius() {
		double expResult = 10;
		double result = instance.getRadius();
		assertEquals(expResult, result, 0.0);
	}

	/**
	 * Test of setRadius method, of class TableView.
	 */
	@Test
	public void testSetRadius() {
		double radius = 20;
		instance.setRadius(radius);
		double result = instance.getRadius();
		assertEquals(20, result, 0.0);
		assertFalse(instance.isDirty());
	}

	/**
	 * Test of getReferencesTo method, of class TableView.
	 */
	@Test
	public void testGetReferencesTo() {
		List<TableView> result = instance.getReferencesTo();
		assertEquals(referencesToList, result);
	}

	// /**
	// * Test of setReferencesTo method, of class TableView.
	// */
	// @Test
	// public void testSetReferencesTo() {
	// List<TableView> referencesTo = null;
	// instance.setReferencesTo(referencesTo);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }

	/**
	 * Test of getReferencedBy method, of class TableView.
	 */
	@Test
	public void testGetReferencedBy() {
		List<TableView> result = instance.getReferencedBy();
		assertEquals(referencedByList, result);
		assertFalse(instance.isDirty());
	}

	// /**
	// * Test of setReferencedBy method, of class TableView.
	// */
	// @Test
	// public void testSetReferencedBy() {
	// List<TableView> referencedBy = null;
	// instance.setReferencedBy(referencedBy);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }

	/**
	 * Test of getReferences method, of class TableView.
	 */
	@Test
	public void testGetReferences() {
		List<TableView> expResult = new ArrayList<TableView>();
		expResult.add(referencedBy);
		expResult.add(referencedTo);
		List<TableView> result = instance.getReferences();
		assertEquals(expResult, result);
	}

	/**
	 * Test of getTable method, of class TableView.
	 */
	@Test
	public void testGetTable() {
		Table expResult = table;
		Table result = instance.getTable();
		assertEquals(expResult, result);
		assertFalse(instance.isDirty());
	}

	/**
	 * Test of setTable method, of class TableView.
	 */
	@Test
	public void testSetTable() {
		instance.setTable(table_ref_by);
		Table result = instance.getTable();
		assertEquals(table_ref_by, result);
	}

	/**
	 * Test of getX method, of class TableView.
	 */
	@Test
	public void testGetX() {
		int expResult = 100;
		int result = instance.getX();
		assertEquals(expResult, result);
		assertFalse(instance.isDirty());
	}

	/**
	 * Test of setX method, of class TableView.
	 */
	@Test
	public void testSetX_int() {
		int x = 0;
		instance.setX(x);
		assertEquals(x, instance.getX());
		assertTrue(instance.isDirty());
	}

	/**
	 * Test of setX method, of class TableView.
	 */
	@Test
	public void testSetX_double() {
		double x = 0.0;
		instance.setX(x);
		assertEquals(x, instance.getX(), 0.0);
		assertTrue(instance.isDirty());

	}

	/**
	 * Test of getY method, of class TableView.
	 */
	@Test
	public void testGetY() {
		int expResult = 50;
		assertEquals(expResult, instance.getY());
		assertFalse(instance.isDirty());
	}

	/**
	 * Test of setY method, of class TableView.
	 */
	@Test
	public void testSetY_int() {
		int y = 0;
		instance.setY(y);
		assertEquals(y, instance.getY());
		assertTrue(instance.isDirty());
	}

	/**
	 * Test of setY method, of class TableView.
	 */
	@Test
	public void testSetY_double() {
		double y = 0.0;
		instance.setY(y);
		assertEquals(y, instance.getY(), 0.0);
		assertTrue(instance.isDirty());
	}

	/**
	 * Test of getVelocityX method, of class TableView.
	 */
	@Test
	public void testGetVelocityX() {
		double expResult = 0.0;
		double result = instance.getVelocityX();
		assertEquals(expResult, result, 0.0);
		assertFalse(instance.isDirty());
	}

	/**
	 * Test of setVelocityX method, of class TableView.
	 */
	@Test
	public void testSetVelocityX() {
		double velocityX = 50.0;
		instance.setVelocityX(velocityX);
		assertEquals(velocityX, instance.getVelocityX(), 0.0);
		assertFalse(instance.isDirty());
	}

	/**
	 * Test of getVelocityY method, of class TableView.
	 */
	@Test
	public void testGetVelocityY() {
		double expResult = 0.0;
		double result = instance.getVelocityY();
		assertEquals(expResult, result, 0.0);
		assertFalse(instance.isDirty());
	}

	/**
	 * Test of setVelocityY method, of class TableView.
	 */
	@Test
	public void testSetVelocityY() {
		double velocityY = 50.0;
		instance.setVelocityY(velocityY);
		assertEquals(velocityY, instance.getVelocityY(), 0.0);
		assertFalse(instance.isDirty());
	}

	/**
	 * Test of getId method, of class TableView.
	 */
	@Test
	public void testGetId() {
		int expResult = 0;
		int result = instance.getId();
		assertEquals(expResult, result);
		assertFalse(instance.isDirty());
	}

	/**
	 * Test of setId method, of class TableView.
	 */
	@Test
	public void testSetId() {
		int id = 60;
		instance.setId(id);
		assertEquals(id, instance.getId());
		assertFalse(instance.isDirty());
	}

	/**
	 * Test of getPage method, of class TableView.
	 */
	@Test
	public void testGetPage() {
		instance.setPage(page);
		SchemaPage result = instance.getPage();
		assertEquals(page, result);
		assertFalse(instance.isDirty());
	}

	/**
	 * Test of setPage method, of class TableView.
	 */
	@Test
	public void testSetPage() {
		SchemaPage page = page_o;
		instance.setPage(page);
		assertEquals(page, instance.getPage());
		assertFalse(instance.isDirty());
	}

	/**
	 * Test of isDirty method, of class TableView.
	 */
	@Test
	public void testConstructedDirty() {
		instance = new TableView(table);
		boolean expResult = true;
		boolean result = instance.isDirty();
		assertEquals(expResult, result);
	}

	/**
	 * Test of setDirty method, of class TableView.
	 */
	@Test
	public void testSetDirty_boolean() {
		boolean dirty = false;
		instance.setDirty(dirty);
		assertEquals(dirty, instance.isDirty());
	}

	/**
	 * Test of setDirty method, of class TableView.
	 */
	@Test
	public void testSetDirty_0args() {
		instance.setDirty();
		assertEquals(true, instance.isDirty());
	}

	/**
	 * Test of setClean method, of class TableView.
	 */
	@Test
	public void testSetClean() {
		instance.setClean();
		assertEquals(false, instance.isDirty());
	}

	/**
	 * Test of compareTo method, of class TableView.
	 */
	@Test
	public void testCompareTo() {
		int expResult = 1;
		int result = instance.compareTo(referencedBy);
		assertEquals(expResult, result);
	}

}