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
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * 
 * @author derrick.bowen
 */
public class ColumnObjectTest {

	@Mock
	Table table;

	ColumnObject instance;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		instance = new ColumnObject();
	}

	/**
	 * Test of clone method, of class ColumnObject.
	 */
	@Test
	public void testClone() {
		instance.setName("Larry");
		instance.setComment("Stooge #1");
		instance.setNullable("False");
		instance.setOrdinalValue(8);
		instance.setDataType("VARCHAR");
		instance.setTable(table);
		Column result = ColumnObject.clone(instance);
		assertTrue(instance != result);
		assertEquals(instance.getClass(), result.getClass());
		assertEquals(instance.getComment(), result.getComment());
		assertEquals(instance.getDataType(), result.getDataType());
		assertEquals(instance.getName(), result.getName());
		assertEquals(instance.getNullable(), result.getNullable());
		assertEquals(instance.getOrdinalValue(), result.getOrdinalValue());
		assertEquals(instance.getTable(), result.getTable());
	}

	/**
	 * Test of transformToFK method, of class ColumnObject.
	 */
	@Test
	public void testTransformToFK() {
		instance.setName("Larry");
		instance.setComment("Stooge #1");
		instance.setNullable("False");
		instance.setOrdinalValue(8);
		instance.setDataType("VARCHAR");
		instance.setTable(table);
		ForeignKey result = instance.transformToFK();
		assertTrue(instance.getClass() != result.getClass());
		assertEquals(ForeignKey.class, result.getClass());
	}

	/**
	 * Test of transformToPK method, of class ColumnObject.
	 */
	@Test
	public void testTransformToPK() {
		instance.setName("Larry");
		instance.setComment("Stooge #1");
		instance.setNullable("False");
		instance.setOrdinalValue(8);
		instance.setDataType("VARCHAR");
		instance.setTable(table);
		PrimaryKey result = instance.transformToPK();
		assertTrue(instance.getClass() != result.getClass());
		assertEquals(PrimaryKeyObject.class, result.getClass());
	}

	/**
	 * Test of getName method, of class ColumnObject.
	 */
	@Test
	public void testGetSetName() {
		instance.setName("Larry");
		String expResult = "Larry";
		String result = instance.getName();
		assertEquals(expResult, result);
	}

	/**
	 * Test of getComment method, of class ColumnObject.
	 */
	@Test
	public void testGetSetComment() {
		instance.setComment("Stooge #1");
		String expResult = "Stooge #1";
		String result = instance.getComment();
		assertEquals(expResult, result);
	}

	/**
	 * Test of getNullable method, of class ColumnObject.
	 */
	@Test
	public void testGetSetNullable() {
		instance.setNullable("False");
		String expResult = "False";
		String result = instance.getNullable();
		assertEquals(expResult, result);
	}

	/**
	 * Test of getOrdinalValue method, of class ColumnObject.
	 */
	@Test
	public void testGetSetOrdinalValue() {
		int ordinalValue = 0;
		instance.setOrdinalValue(ordinalValue);
		int result = instance.getOrdinalValue();
		assertEquals(ordinalValue, result);
	}

	/**
	 * Test of getDataType method, of class ColumnObject.
	 */
	@Test
	public void testGetSetDataType() {
		String dataType = "VARCHAR";
		instance.setDataType(dataType);
		String result = instance.getDataType();
		assertEquals(dataType, result);
	}

	/**
	 * Test of getTable method, of class ColumnObject.
	 */
	@Test
	public void testGetSetTable() {
		instance.setTable(table);
		Table result = instance.getTable();
		assertEquals(table, result);
	}

}