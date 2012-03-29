/*
 * DB-SVG Copyright 2012 Derrick Bowen
 *
 * This file is part of DB-SVG.
 *
 *   DB-SVG is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   DB-SVG is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with DB-SVG.  If not, see <http://www.gnu.org/licenses/>.
 *   
 *   @author Derrick Bowen derrickbowen@dbsvg.com
 */
package com.dbsvg.objects.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
	public void testGetSchemaId() {
		String schemaId = "SCH NAME";
		instance.setSchemaId(schemaId);
		String result = instance.getSchemaId();
		assertEquals(schemaId, result);
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

	/**
	 * Test of compareTo method, of class Table.
	 */
	@Test
	public void testCompareTo() {
		Table instance = new Table("Name1");
		Table equal = new Table("Name1");
		Table more = new Table("Drudge");
		Table less = new Table("Xray");
		assertEquals(0, instance.compareTo(instance));
		assertEquals(0, instance.compareTo(equal));
		assertEquals(0, equal.compareTo(instance));
		assertEquals(10, instance.compareTo(more));
		assertEquals(-10, more.compareTo(instance));
		assertEquals(-10, instance.compareTo(less));
		assertEquals(10, less.compareTo(instance));
		assertEquals(1, instance.compareTo(null));
	}

	/**
	 * Test of compareTo method, of class Table.
	 */
	@Test
	public void testEquals() {
		Table instance = new Table("Name1");
		Table equal = new Table("Name1");
		Table more = new Table("Drudge");
		Table less = new Table("Xray");
		assertTrue(instance.equals(instance));
		assertTrue(instance.equals(equal));
		assertTrue(equal.equals(instance));
		assertFalse(instance.equals(more));
		assertFalse(more.equals(instance));
		assertFalse(instance.equals(less));
		assertFalse(less.equals(instance));
		assertFalse(instance.equals(null));
	}

	/**
	 * Test of compareTo method, of class Table.
	 */
	@Test
	public void testHash() {
		Table instance = new Table("Name1");
		Table equal = new Table("Name1");
		Table different = new Table("Drudge");
		HashSet<Table> set = new HashSet<Table>();
		set.add(instance);

		assertTrue(set.contains(instance));
		assertTrue(set.contains(equal));
		assertFalse(set.contains(different));
	}

	// /**
	// * Test of generateWidth method, of class Table.
	// */
	// @Test
	// public void testGenerateWidth() {
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
	// int width = 0;
	// Table instance = new Table();
	// instance.setWidth(width);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//

}