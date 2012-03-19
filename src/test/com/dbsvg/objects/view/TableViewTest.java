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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dbsvg.objects.model.Column;
import com.dbsvg.objects.model.ForeignKey;
import com.dbsvg.objects.model.Table;

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

	Set<TableView> referencedByList;
	Set<TableView> referencesToList;

	Map<String, ForeignKey> fkeys = null;
	Map<String, Table> referencers = null;

	TableView tv;
	TableView tvsame;
	TableView tvx;
	TableView tvy;
	TableView tvr;
	TableView tvl;
	TableView tvt;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(page.getId()).thenReturn(pageId);

		referencedByList = new HashSet<TableView>();
		referencesToList = new HashSet<TableView>();

		instance = new TableView(table, page);
		instance.setX(x1);
		instance.setY(y1);
		instance.setRadius(r1);
		instance.setSorted();

		referencedBy = new TableView(table_ref_by, page);
		referencedBy.setX(x2);
		referencedBy.setY(y2);
		referencedBy.setRadius(r2);
		referencedByList.add(referencedBy);
		referencedBy.setSorted();

		when(table.getName()).thenReturn("table");
		when(table_ref_by.getName()).thenReturn("table_ref_by");
		when(table_ref_to.getName()).thenReturn("table_ref_to");

		when(table.compareTo(table_ref_by)).thenReturn(-1);
		when(table.compareTo(table_ref_to)).thenReturn(-1);
		when(table_ref_by.compareTo(table)).thenReturn(1);
		when(table_ref_to.compareTo(table)).thenReturn(1);
		when(table_ref_by.compareTo(table_ref_to)).thenReturn(-1);
		when(table_ref_to.compareTo(table_ref_by)).thenReturn(1);

		Map<UUID, TableView> refByTableViews = new HashMap<UUID, TableView>();
		refByTableViews.put(pageId, referencedBy);
		when(table_ref_by.getTablePageViews()).thenReturn(refByTableViews);

		referencedTo = new TableView(table_ref_to, page);
		referencedTo.setX(x2);
		referencedTo.setY(y2);
		referencedTo.setRadius(r2);
		referencesToList.add(referencedTo);
		referencedTo.setSorted();

		Map<UUID, TableView> refToTableViews = new HashMap<UUID, TableView>();
		refToTableViews.put(pageId, referencedTo);
		when(table_ref_to.getTablePageViews()).thenReturn(refToTableViews);

		fkeys = new HashMap<String, ForeignKey>();

		fkeys.put("table ref to", fk);
		when(fk.getReference()).thenReturn(fkColumn);
		when(fkColumn.getTable()).thenReturn(table_ref_to);
		when(table.getForeignKeys()).thenReturn(fkeys);
		referencers = new HashMap<String, Table>();
		referencers.put("table ref by", table_ref_by);
		when(table.getReferencingTables()).thenReturn(referencers);

		instance.setReferencedBy(referencedByList);
		instance.setReferencesTo(referencesToList);

		tv = new TableView(table, page);
		tv.setX(10);
		tv.setY(10);
		tv.setRadius(10);
		tv.setSorted();

		tvsame = new TableView(table, page);
		tvsame.setX(10);
		tvsame.setY(10);
		tvsame.setRadius(10);
		tvsame.setSorted();

		tvx = new TableView(table, page);
		tvx.setX(100);
		tvx.setY(10);
		tvx.setRadius(10);
		tvx.setSorted();

		tvy = new TableView(table, page);
		tvy.setX(10);
		tvy.setY(100);
		tvy.setRadius(10);
		tvy.setSorted();

		tvr = new TableView(table, page);
		tvr.setX(10);
		tvr.setY(10);
		tvr.setRadius(100);
		tvr.setSorted();

		tvl = new TableView(table, page);
		tvl.setX(10);
		tvl.setY(10);
		tvl.setRadius(10);
		tvl.addReference(tvr);
		tvl.setSorted();

		tvt = new TableView(table_ref_by, page);
		tvt.setX(10);
		tvt.setY(10);
		tvt.setRadius(10);
		tvt.setSorted();

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
	 * Tests that equals is working properly for the mocks
	 */
	@Test
	public void testReferencesContains() {
		List<Vertex> ref = new ArrayList<Vertex>();
		for (TableView t : referencedByList) {
			if (!ref.contains(t))
				ref.add(t);
		}
		for (TableView t : referencesToList) {
			if (!ref.contains(t))
				ref.add(t);
		}

		assertEquals(2, ref.size());
	}

	/**
	 * Test of calcLinksAndRadius method, of class TableView.
	 */
	@Test
	public void testCalcLinksAndRadius_NumLinks() {

		int expected = 2;

		instance.calcLinksAndRadius();
		assertEquals(expected, instance.getNumLinks());

	}

	/**
	 * Test of getNumLinks method, of class TableView.
	 */
	@Test
	public void testGetNumLinks() {
		int expResult = 2;
		int result = instance.getNumLinks();
		assertEquals(expResult, result);
	}

	/**
	 * Test of getReferencesTo method, of class TableView.
	 */
	@Test
	public void testGetReferencesTo() {
		Set<TableView> result = instance.getReferencesTo();
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
		Set<TableView> result = instance.getReferencedBy();
		assertEquals(referencedByList, result);
		assertFalse(instance.needsResort());
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
		List<Vertex> expResult = new ArrayList<Vertex>();
		expResult.add(referencedBy);
		expResult.add(referencedTo);
		List<Vertex> result = instance.getReferences();
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
		assertFalse(instance.needsResort());
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
		assertFalse(instance.needsResort());
	}

	/**
	 * Test of setX method, of class TableView.
	 */
	@Test
	public void testSetX_int() {
		int x = 0;
		instance.setX(x);
		assertEquals(x, (int) instance.getX());
		assertTrue(instance.needsResort());
	}

	/**
	 * Test of setX method, of class TableView.
	 */
	@Test
	public void testSetX_double() {
		double x = 0.0;
		instance.setX(x);
		assertEquals(x, instance.getX(), 0.0);
		assertTrue(instance.needsResort());

	}

	/**
	 * Test of getY method, of class TableView.
	 */
	@Test
	public void testGetY() {
		int expResult = 50;
		assertEquals(expResult, (int) instance.getY());
		assertFalse(instance.needsResort());
	}

	/**
	 * Test of setY method, of class TableView.
	 */
	@Test
	public void testSetY_int() {
		int y = 0;
		instance.setY(y);
		assertEquals(y, (int) instance.getY());
		assertTrue(instance.needsResort());
	}

	/**
	 * Test of setY method, of class TableView.
	 */
	@Test
	public void testSetY_double() {
		double y = 0.0;
		instance.setY(y);
		assertEquals(y, instance.getY(), 0.0);
		assertTrue(instance.needsResort());
	}

	/**
	 * Test of getId method, of class TableView.
	 */
	@Test
	public void testGetId() {
		int expResult = 0;
		int result = instance.getId();
		assertEquals(expResult, result);
		assertFalse(instance.needsResort());
	}

	/**
	 * Test of setId method, of class TableView.
	 */
	@Test
	public void testSetId() {
		int id = 60;
		instance.setId(id);
		assertEquals(id, instance.getId());
		assertFalse(instance.needsResort());
	}

	/**
	 * Test of getPage method, of class TableView.
	 */
	@Test
	public void testGetPage() {
		instance.setPage(page);
		SchemaPage result = instance.getPage();
		assertEquals(page, result);
		assertFalse(instance.needsResort());
	}

	/**
	 * Test of setPage method, of class TableView.
	 */
	@Test
	public void testSetPage() {
		SchemaPage page = page_o;
		instance.setPage(page);
		assertEquals(page, instance.getPage());
		assertFalse(instance.needsResort());
	}

	/**
	 * Test of isDirty method, of class TableView.
	 */
	@Test
	public void testConstructedDirty() {
		instance = new TableView(table, page);
		boolean expResult = true;
		boolean result = instance.needsResort();
		assertEquals(expResult, result);
	}

	/**
	 * Test of setDirty method, of class TableView.
	 */
	@Test
	public void testSetDirty_boolean() {
		boolean dirty = false;
		instance.setNeedsSort(dirty);
		assertEquals(dirty, instance.needsResort());
	}

	/**
	 * Test of setDirty method, of class TableView.
	 */
	@Test
	public void testSetDirty_0args() {
		instance.setNeedsSort();
		assertEquals(true, instance.needsResort());
	}

	/**
	 * Test of setClean method, of class TableView.
	 */
	@Test
	public void testSetClean() {
		instance.setSorted();
		assertEquals(false, instance.needsResort());
	}

	/**
	 * Test of compareTo method, of class TableView.
	 */
	@Test
	public void testCompareTo() {

		assertEquals(0, tv.compareTo(tvsame));
		assertEquals(0, tvsame.compareTo(tv));

		assertEquals(-1, tv.compareTo(tvx));
		assertEquals(1, tvx.compareTo(tv));

		assertEquals(-1, tv.compareTo(tvy));
		assertEquals(1, tvy.compareTo(tv));

		assertEquals(-1, tv.compareTo(tvr));
		assertEquals(1, tvr.compareTo(tv));

		assertEquals(-1, tv.compareTo(tvl));
		assertEquals(1, tvl.compareTo(tv));

		assertEquals(-1, tv.compareTo(tvt));
		assertEquals(1, tvt.compareTo(tv));
	}

	/**
	 * Test of compareTo method, of class Table.
	 */
	@Test
	public void testEquals() {

		assertTrue(tv.equals(tvsame));
		assertTrue(tv.equals(tv));
		assertTrue(tvsame.equals(tv));

		assertFalse(tv.equals(tvx));
		assertFalse(tvx.equals(tv));

		assertFalse(tv.equals(tvy));
		assertFalse(tvy.equals(tv));

		assertFalse(tv.equals(tvr));
		assertFalse(tvr.equals(tv));

		assertFalse(tv.equals(tvl));
		assertFalse(tvl.equals(tv));

		assertFalse(tv.equals(tvt));
		assertFalse(tvt.equals(tv));

		assertFalse(instance.equals(null));
	}

	/**
	 * Test of compareTo method, of class Table.
	 */
	@Test
	public void testHash() {

		HashSet<TableView> set = new HashSet<TableView>();
		set.add(tv);

		assertTrue(set.contains(tv));
		assertTrue(set.contains(tvsame));
		assertFalse(set.contains(tvx));
		assertFalse(set.contains(tvy));
		assertFalse(set.contains(tvr));
		assertFalse(set.contains(tvl));
		assertFalse(set.contains(tvt));
	}

}