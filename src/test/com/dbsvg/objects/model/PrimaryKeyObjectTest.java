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

/**
 * 
 * @author derrick.bowen
 */
public class PrimaryKeyObjectTest {

	PrimaryKeyObject instance;

	@Before
	public void setUp() throws Exception {
		instance = new PrimaryKeyObject();
	}

	/**
	 * Test of isAutoIncrement method, of class PrimaryKeyObject.
	 */
	@Test
	public void testIsAutoIncrement() {
		boolean expResult = false;
		boolean result = instance.isAutoIncrement();
		assertEquals(expResult, result);
	}

	/**
	 * Test of setAutoIncrement method, of class PrimaryKeyObject.
	 */
	@Test
	public void testSetAutoIncrement() {
		boolean autoIncrement = true;
		instance.setAutoIncrement(autoIncrement);
		assertEquals(autoIncrement, instance.isAutoIncrement());
	}

	// /**
	// * Test of cloneTo method, of class PrimaryKeyObject.
	// */
	// @Test
	// public void testCloneTo() {
	// PrimaryKey pk = null;
	// PrimaryKeyObject instance = new PrimaryKeyObject();
	// PrimaryKey expResult = null;
	// PrimaryKey result = instance.cloneTo(pk);
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }

}