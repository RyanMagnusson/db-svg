/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dbsvg.objects.model;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dbsvg.objects.model.Column;
import com.dbsvg.objects.model.ForeignKey;
import com.dbsvg.objects.model.PrimaryKey;
import com.dbsvg.objects.model.Table;

/**
 * 
 * @author derrick.bowen
 */
public class TableTest {

	Table instance;

	@Mock
	Column column;

	@Mock
	ForeignKey fk;

	@Mock
	Table referencingTable;

	@Mock
	PrimaryKey primaryKey;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		instance = new Table();
	}

	/**
	 * Test of getId method, of class Table.
	 */
	@Test
	public void testGetId() {
		UUID id = UUID.randomUUID();
		instance.setId(id);
		UUID result = instance.getId();
		assertEquals(id, result);
	}

	/**
	 * Test of getName method, of class Table.
	 */
	@Test
	public void testGetName() {
		String name = "NAME";
		instance.setName(name);
		String result = instance.getName();
		assertEquals(name, result);
	}

	/**
	 * Test of getSchemaName method, of class Table.
	 */
	@Test
	public void testGetSchemaName() {
		String schemaName = "SCH NAME";
		instance.setSchemaName(schemaName);
		String result = instance.getSchemaName();
		assertEquals(schemaName, result);
	}

	/**
	 * Test of getColumns method, of class Table.
	 */
	@Test
	public void testGetColumns() {
		Map<String, Column> columns = new HashMap<String, Column>();
		columns.put("column", column);
		instance.setColumns(columns);
		Map<String, Column> result = instance.getColumns();
		assertEquals(columns, result);
	}

	/**
	 * Test of getForeignKeys method, of class Table.
	 */
	@Test
	public void testGetForeignKeys() {
		Map<String, ForeignKey> foreignKeys = new HashMap<String, ForeignKey>();
		foreignKeys.put("fk", fk);
		instance.setForeignKeys(foreignKeys);
		Map<String, ForeignKey> result = instance.getForeignKeys();
		assertEquals(foreignKeys, result);
	}

	/**
	 * Test of getReferencingTables method, of class Table.
	 */
	@Test
	public void testGetReferencingTables() {
		Map<String, Table> referencingTables = new HashMap<String, Table>();
		referencingTables.put("referencingTable", referencingTable);
		instance.setReferencingTables(referencingTables);
		Map<String, Table> result = instance.getReferencingTables();
		assertEquals(referencingTables, result);
	}

	/**
	 * Test of getPrimaryKeys method, of class Table.
	 */
	@Test
	public void testGetPrimaryKeys() {
		Map<String, PrimaryKey> primaryKeys = new HashMap<String, PrimaryKey>();
		primaryKeys.put("primaryKey", primaryKey);
		instance.setPrimaryKeys(primaryKeys);
		Map<String, PrimaryKey> result = instance.getPrimaryKeys();
		assertEquals(primaryKeys, result);
	}

	// /**
	// * Test of generateWidth method, of class Table.
	// */
	// @Test
	// public void testGenerateWidth() {
	// System.out.println("generateWidth");
	// Table instance = new Table();
	// int expResult = 0;
	// int result = instance.generateWidth();
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getTablePageViews method, of class Table.
	// */
	// @Test
	// public void testGetTablePageViews() {
	// System.out.println("getTablePageViews");
	// Table instance = new Table();
	// Map expResult = null;
	// Map result = instance.getTablePageViews();
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of addTableViewForPage method, of class Table.
	// */
	// @Test
	// public void testAddTableViewForPage_TableView_UUID() {
	// System.out.println("addTableViewForPage");
	// TableView tv = null;
	// UUID pageId = null;
	// Table instance = new Table();
	// instance.addTableViewForPage(tv, pageId);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of addTableViewForPage method, of class Table.
	// */
	// @Test
	// public void testAddTableViewForPage_TableView_SchemaPage() {
	// System.out.println("addTableViewForPage");
	// TableView tv = null;
	// SchemaPage page = null;
	// Table instance = new Table();
	// instance.addTableViewForPage(tv, page);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getHeight method, of class Table.
	// */
	// @Test
	// public void testGetHeight() {
	// System.out.println("getHeight");
	// Table instance = new Table();
	// int expResult = 0;
	// int result = instance.getHeight();
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setHeight method, of class Table.
	// */
	// @Test
	// public void testSetHeight() {
	// System.out.println("setHeight");
	// int height = 0;
	// Table instance = new Table();
	// instance.setHeight(height);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of getWidth method, of class Table.
	// */
	// @Test
	// public void testGetWidth() {
	// System.out.println("getWidth");
	// Table instance = new Table();
	// int expResult = 0;
	// int result = instance.getWidth();
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setWidth method, of class Table.
	// */
	// @Test
	// public void testSetWidth() {
	// System.out.println("setWidth");
	// int width = 0;
	// Table instance = new Table();
	// instance.setWidth(width);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of compareTo method, of class Table.
	// */
	// @Test
	// public void testCompareTo() {
	// System.out.println("compareTo");
	// Table o = null;
	// Table instance = new Table();
	// int expResult = 0;
	// int result = instance.compareTo(o);
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }

}