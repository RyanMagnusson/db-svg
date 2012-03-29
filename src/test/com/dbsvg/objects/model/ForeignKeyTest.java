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

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * 
 * @author derrick.bowen
 */
public class ForeignKeyTest {

	ForeignKey instance;

	@Mock
	Column reference;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		instance = new ForeignKey();
	}

	/**
	 * Test of getDeleteRule method, of class ForeignKey.
	 */
	@Test
	public void testGetDeleteRule() {
		String deleteRule = "YES";
		instance.setDeleteRule(deleteRule);
		String result = instance.getDeleteRule();
		assertEquals(deleteRule, result);
	}

	/**
	 * Test of getUpdateRule method, of class ForeignKey.
	 */
	@Test
	public void testGetUpdateRule() {
		String updateRule = "";
		instance.setUpdateRule(updateRule);
		String result = instance.getUpdateRule();
		assertEquals(updateRule, result);
	}

	/**
	 * Test of getReference method, of class ForeignKey.
	 */
	@Test
	public void testGetReference() {
		instance.setReference(reference);
		Column result = instance.getReference();
		assertEquals(reference, result);
	}

	/**
	 * Test of getReferencedColumn method, of class ForeignKey.
	 */
	@Test
	public void testGetReferencedColumn() {
		String referencedColumn = "A COLUMN";
		instance.setReferencedColumn(referencedColumn);
		String result = instance.getReferencedColumn();
		assertEquals(referencedColumn, result);
	}

	/**
	 * Test of getReferencedTable method, of class ForeignKey.
	 */
	@Test
	public void testGetReferencedTable() {
		String referencedTable = "A TABLE";
		instance.setReferencedTable(referencedTable);
		String result = instance.getReferencedTable();
		assertEquals(referencedTable, result);
	}

	// /**
	// * Test of cloneTo method, of class ForeignKey.
	// */
	// @Test
	// public void testCloneTo() {
	// ForeignKey fk = null;
	// ForeignKey instance = new ForeignKey();
	// ForeignKey expResult = null;
	// ForeignKey result = instance.cloneTo(fk);
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }

}