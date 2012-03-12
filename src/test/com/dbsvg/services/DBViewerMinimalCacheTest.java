/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
import com.dbsvg.services.DBViewerMinimalCache;


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
		System.out.println("putSchemaPage");
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
	// System.out.println("removeConnection");
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
	// System.out.println("setAllConnections");
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
	// System.out.println("getAllConnections");
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