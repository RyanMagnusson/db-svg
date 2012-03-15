/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dbsvg.models;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * 
 * @author derrick.bowen
 */
public class JdbcMainDAOTest {

	JdbcMainDAO instance;

	@Mock
	InternalDataDAO iDAO = null;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		instance = new JdbcMainDAO();
	}

	/**
	 * Test of getDefaultConnection method, of class JdbcMainDAO.
	 */
	@Test
	public void testGetDefaultConnection() {
		Connection expResult = null;
		Connection result = instance.getDefaultConnection();
		assertEquals(expResult, result);
		// TODO replace connectionpool with a mock
		// fail("The test case is a prototype.");
	}

	// /**
	// * Test of getTables method, of class JdbcMainDAO.
	// */
	// @Test
	// public void testGetTables() throws Exception {
	// Connection conn = null;
	// String schemaName = "";
	// JdbcMainDAO instance = new JdbcMainDAO();
	// Map expResult = null;
	// Map result = instance.getTables(conn, schemaName);
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }

}