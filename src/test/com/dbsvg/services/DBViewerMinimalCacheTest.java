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
package com.dbsvg.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dbsvg.models.ConnectionWrapper;
import com.dbsvg.objects.view.SchemaPage;

/**
 * 
 * @author derrick.bowen
 */
public class DBViewerMinimalCacheTest {

	DBViewerMinimalCache instance;

	@Mock
	ConnectionWrapper cw;

	@Mock
	SchemaPage page;
	UUID pageId = UUID.randomUUID();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(page.getId()).thenReturn(pageId);

		instance = new DBViewerMinimalCache();
	}

	/**
	 * Test of putConnection method, of class DBViewerMinimalCache.
	 */
	@Test
	public void testPutConnection() {
		String dbi = "test";
		instance.putConnection(dbi, cw);
		ConnectionWrapper result = instance.getConnection(dbi);
		assertEquals(cw, result);
	}

	/**
	 * Test of putSchemaPage method, of class DBViewerMinimalCache.
	 */
	@Test
	public void testPutSchemaPage() {
		DBViewerMinimalCache instance = new DBViewerMinimalCache();
		instance.putSchemaPage(page);
		SchemaPage result = instance.getSchemaPage(pageId);
		assertEquals(page, result);
	}

	// /**
	// * Test of removeConnection method, of class DBViewerMinimalCache.
	// */
	// @Test
	// public void testRemoveConnection() {
	// String dbi = "";
	// DBViewerMinimalCache instance = new DBViewerMinimalCache();
	// instance.removeConnection(dbi);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of setAllConnections method, of class DBViewerMinimalCache.
	// */
	// @Test
	// public void testSetAllConnections() {
	// Map<String, ConnectionWrapper> cwmap = null;
	// DBViewerMinimalCache instance = new DBViewerMinimalCache();
	// instance.setAllConnections(cwmap);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	//
	// /**
	// * Test of getAllConnections method, of class DBViewerMinimalCache.
	// */
	// @Test
	// public void testGetAllConnections() {
	// DBViewerMinimalCache instance = new DBViewerMinimalCache();
	// Map expResult = null;
	// Map result = instance.getAllConnections();
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//

}