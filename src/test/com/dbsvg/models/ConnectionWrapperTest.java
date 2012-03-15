/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dbsvg.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;

import org.junit.Test;

/**
 * 
 * @author derrick.bowen
 */
public class ConnectionWrapperTest {

	private String iDAOPath = "C:\\DB-SVG\\test.db";

	public ConnectionWrapperTest() {
	}

	// @BeforeClass
	// public static void setUpClass() throws Exception {
	// }
	//
	// @AfterClass
	// public static void tearDownClass() throws Exception {
	// }

	/**
	 * Test of setDriver method, of class ConnectionWrapper.
	 */
	@Test
	public void testGetSetDriver() {
		String driver = "testDriver";
		ConnectionWrapper instance = new ConnectionWrapper();
		instance.setDriver(driver);
		assertEquals(driver, instance.getDriver());
	}

	/**
	 * Test of getId method, of class ConnectionWrapper.
	 */
	@Test
	public void testGetSetId() {
		int id = 10;
		ConnectionWrapper instance = new ConnectionWrapper();
		instance.setId(id);
		int result = instance.getId();
		assertEquals(id, result);
	}

	/**
	 * Test of getTitle method, of class ConnectionWrapper.
	 */
	@Test
	public void testGetSetTitle() {
		String title = "";
		ConnectionWrapper instance = new ConnectionWrapper();
		instance.setTitle(title);
		String result = instance.getTitle();
		assertEquals(title, result);
	}

	/**
	 * Test of getPassword method, of class ConnectionWrapper.
	 */
	@Test
	public void testGetSetPassword() {
		String password = "Password1";
		ConnectionWrapper instance = new ConnectionWrapper();
		instance.setPassword(password);
		String result = instance.getPassword();
		assertEquals(password, result);
	}

	/**
	 * Test of getUrl method, of class ConnectionWrapper.
	 */
	@Test
	public void testGetSetUrl() {
		String url = "test://url";
		ConnectionWrapper instance = new ConnectionWrapper();
		instance.setUrl(url);
		String result = instance.getUrl();
		assertEquals(url, result);
	}

	/**
	 * Test of getUsername method, of class ConnectionWrapper.
	 */
	@Test
	public void testGetSetUsername() {
		String username = "testuname";
		ConnectionWrapper instance = new ConnectionWrapper();
		instance.setUsername(username);
		String result = instance.getUsername();
		assertEquals(username, result);
	}

	/**
	 * Test of getConnection method, of class ConnectionWrapper.
	 */
	@Test
	public void testGetConnection() throws Exception {
		String url = "jdbc:sqlite:";
		String driver = "org.sqlite.JDBC";

		ConnectionWrapper instance = new ConnectionWrapper();
		instance.setDriver(driver);
		instance.setUrl(url + iDAOPath);
		instance.setUsername("");
		instance.setPassword("");
		Connection conn = instance.connectToDB();
		assertTrue(conn != null);
	}

	/**
	 * Test of compareTo method, of class ConnectionWrapper.
	 */
	@Test
	public void testCompareTo() {
		ConnectionWrapper o = new ConnectionWrapper();
		ConnectionWrapper instance = new ConnectionWrapper();
		o.setTitle("Test Connection Wrapper");
		instance.setTitle("Test Connection Wrapper");
		int expResult = 0;
		int result = instance.compareTo(o);
		assertEquals(expResult, result);

		o.setTitle("A Test Connection Wrapper");
		result = instance.compareTo(o);
		assertTrue(expResult < result);

		o.setTitle("Z Test Connection Wrapper");
		result = instance.compareTo(o);
		assertTrue(expResult > result);
	}

}