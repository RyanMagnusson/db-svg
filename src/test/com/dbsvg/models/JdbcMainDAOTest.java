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