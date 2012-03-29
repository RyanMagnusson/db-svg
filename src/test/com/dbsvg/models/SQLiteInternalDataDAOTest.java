/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dbsvg.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dbsvg.objects.model.Table;
import com.dbsvg.objects.view.SchemaPage;
import com.dbsvg.objects.view.TableView;

/**
 * 
 * @author derrick.bowen
 */
public class SQLiteInternalDataDAOTest {

	private String iDAOPath = "target/test.db";

	SQLiteInternalDataDAO instance;

	@Mock
	InternalDataDAO iDAO = null;

	@Mock
	Connection iDAOconn;
	@Mock
	SchemaPage page;
	@Mock
	Table table;
	@Mock
	TableView tableView;
	@Mock
	PreparedStatement ps;
	@Mock
	PreparedStatement ps2;
	@Mock
	ResultSet rs;
	@Mock
	ConnectionWrapper cw;

	UUID pageId = UUID.randomUUID();
	UUID tableId = UUID.randomUUID();

	@Before
	public void setUp() throws SQLException {
		MockitoAnnotations.initMocks(this);
		instance = new SQLiteInternalDataDAO();
		instance.path = iDAOPath;

		when(page.getId()).thenReturn(pageId);
		when(table.getId()).thenReturn(tableId);

		when(ps.executeQuery()).thenReturn(rs);
		when(rs.next()).thenReturn(true, false);
		when(tableView.getTable()).thenReturn(table);
	}

	/**
	 * Test of getConnection method, of class SQLiteInternalDataDAO.
	 */
	@Test
	public void testGetConnection() throws Exception {
		Connection result = instance.getConnection();
		assertTrue(result != null);
	}

	/**
	 * Test of setUpInternalDB method, of class SQLiteInternalDataDAO.
	 */
	@Test
	public void testSetUpInternalDB() {
		try {
			Connection conn = instance.getConnection();
			instance.setUpInternalDB(conn);
		} catch (Exception ex) {
			Logger.getLogger(SQLiteInternalDataDAOTest.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
			// TODO review the generated test code and remove the default call
			// to fail.
			fail("The internal DB failed to set up.");
		}
	}

	/**
	 * Test of setPath method, of class SQLiteInternalDataDAO.
	 */
	@Test
	public void testSetPath() {
		String path = "target/test2.db";
		instance.setPath(path);
		assertEquals(path, instance.getPath());
	}

	@Test
	public void makeViewWCoordinates_inDB() throws SQLException {
		int numTables = 10;
		int id = 20;
		Double x = new Double(30);
		Double y = new Double(40);

		when(iDAOconn.prepareStatement(SQLiteInternalDataDAO.SELECT_TV_SQL)).thenReturn(ps);
		when(page.makeViewForTable(table)).thenReturn(tableView);
		when(rs.getDouble("x_pos")).thenReturn(x);
		when(rs.getDouble("y_pos")).thenReturn(y);

		instance.makeViewWCoordinates(table, page, numTables, iDAOconn, false);

		verify(ps).setString(1, pageId.toString());
		verify(ps).setString(2, tableId.toString());
		verify(tableView).setX(x);
		verify(tableView).setY(y);
		verify(tableView).calcLinksAndRadius();
		verify(tableView).setSorted();
		verify(rs).close();
	}

	@Test
	public void makeViewWCoordinates_notInDBNoCreate() throws SQLException {
		int numTables = 10;
		int id = 20;
		Double x = new Double(30);
		Double y = new Double(40);

		when(iDAOconn.prepareStatement(SQLiteInternalDataDAO.SELECT_TV_SQL)).thenReturn(ps);
		when(page.makeViewForTable(table)).thenReturn(tableView);
		when(rs.getDouble("x_pos")).thenReturn(x);
		when(rs.getDouble("y_pos")).thenReturn(y);
		when(rs.next()).thenReturn(false);

		instance.makeViewWCoordinates(table, page, numTables, iDAOconn, false);

		verify(ps).setString(1, pageId.toString());
		verify(ps).setString(2, tableId.toString());
		verify(tableView, times(0)).setX(x);
		verify(tableView, times(0)).setY(y);
		verify(tableView, times(0)).calcLinksAndRadius();
		verify(tableView, times(0)).setSorted();
		verify(rs).close();
	}

	@Test
	public void makeViewWCoordinates_notInDBYesCreate() throws SQLException {
		int numTables = 10;
		int id = 20;
		Double x = new Double(30);
		Double y = new Double(40);

		when(iDAOconn.prepareStatement(SQLiteInternalDataDAO.SELECT_TV_SQL)).thenReturn(ps);
		when(page.makeViewForTable(table)).thenReturn(tableView);
		when(rs.getDouble("x_pos")).thenReturn(x);
		when(rs.getDouble("y_pos")).thenReturn(y);
		when(rs.next()).thenReturn(false);

		instance.makeViewWCoordinates(table, page, numTables, iDAOconn, true);

		verify(ps).setString(1, pageId.toString());
		verify(ps).setString(2, tableId.toString());
		verify(tableView, times(0)).setX(x);
		verify(tableView, times(0)).setY(y);
		verify(tableView, times(0)).calcLinksAndRadius();
		verify(tableView, times(0)).setSorted();
		verify(tableView).randomInitialize(numTables);
		verify(rs).close();
	}

	@Test
	public void makeTableSchema() throws SQLException {
		String tableName = "tableName";
		String schemaId = "schemaId";
		String id = UUID.randomUUID().toString();
		when(iDAOconn.prepareStatement(SQLiteInternalDataDAO.SELECT_T_SQL)).thenReturn(ps);
		when(rs.getString("id")).thenReturn(id);
		when(table.getName()).thenReturn(tableName);
		when(table.getSchemaId()).thenReturn(schemaId);

		instance.makeTableSchema(table, iDAOconn);

		verify(ps).setString(1, tableName);
		verify(ps).setString(2, schemaId);
		verify(table).setId(UUID.fromString(id));
		verify(rs).close();
	}

	/**
	 * Test of saveConnectionWrapper method, of class SQLiteInternalDataDAO.
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testSaveConnectionWrapper() throws SQLException {

		when(iDAOconn.prepareStatement(SQLiteInternalDataDAO.INSERT_CONNECTION_SQL)).thenReturn(ps);
		instance.saveConnectionWrapper(cw, iDAOconn);
		verify(ps).executeUpdate();
		verify(ps).close();

	}

	/**
	 * Test of saveConnectionWrapper method, of class SQLiteInternalDataDAO.
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testSaveConnectionWrapperNewId() throws SQLException {
		Integer id = new Integer(20);
		when(iDAOconn.prepareStatement(SQLiteInternalDataDAO.INSERT_CONNECTION_NEW_ID_SQL)).thenReturn(ps);
		when(iDAOconn.prepareStatement(SQLiteInternalDataDAO.LAST_ROW_ID_SQL)).thenReturn(ps2);
		when(ps2.executeQuery()).thenReturn(rs);
		when(rs.getInt("id")).thenReturn(id);

		instance.saveConnectionWrapperNewID(cw, iDAOconn);

		verify(cw).setId(id);
		verify(ps).executeUpdate();
		verify(ps).close();
		verify(ps2).close();
		verify(rs).close();

	}

	/**
	 * Test of deleteConnectionWrapper method, of class SQLiteInternalDataDAO.
	 */
	@Test
	public void testDeleteConnectionWrapper() throws SQLException {
		String id = "TEST_ID";
		when(iDAOconn.prepareStatement(SQLiteInternalDataDAO.DELETE_CONNECTION_SQL)).thenReturn(ps);
		instance.deleteConnectionWrapper(id, iDAOconn);
		verify(ps).executeUpdate();
		verify(ps).close();
	}
	//
	// /**
	// * Test of saveConnectionWrapperNewID method, of class
	// * SQLiteInternalDataDAO.
	// */
	// @Test
	// public void testSaveConnectionWrapperNewID() {
	// ConnectionWrapper c = null;
	// Connection conn = null;
	// SQLiteInternalDataDAO instance = new SQLiteInternalDataDAO(iDAOPath);
	// instance.saveConnectionWrapperNewID(c, conn);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of saveTable method, of class SQLiteInternalDataDAO.
	// */
	// @Test
	// public void testSaveTable() {
	// Table t = null;
	// Connection conn = null;
	// SQLiteInternalDataDAO instance = new SQLiteInternalDataDAO(iDAOPath);
	// instance.saveTable(t, conn);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of saveSchemaPage method, of class SQLiteInternalDataDAO.
	// */
	// @Test
	// public void testSaveSchemaPage() {
	// SchemaPage page = null;
	// Connection conn = null;
	// SQLiteInternalDataDAO instance = new SQLiteInternalDataDAO(iDAOPath);
	// instance.saveSchemaPage(page, conn);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of saveTablePosition method, of class SQLiteInternalDataDAO.
	// */
	// @Test
	// public void testSaveTablePosition() {
	// TableView tv = null;
	// Connection conn = null;
	// SQLiteInternalDataDAO instance = new SQLiteInternalDataDAO(iDAOPath);
	// instance.saveTablePosition(tv, conn);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of verifySchema method, of class SQLiteInternalDataDAO.
	// */
	// @Test
	// public void testVerifySchema() {
	// String schema = "";
	// Connection conn = null;
	// SQLiteInternalDataDAO instance = new SQLiteInternalDataDAO(iDAOPath);
	// instance.verifySchema(schema, conn);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of readConnectionWrapper method, of class SQLiteInternalDataDAO.
	// */
	// @Test
	// public void testReadConnectionWrapper() {
	// int id = 0;
	// Connection conn = null;
	// SQLiteInternalDataDAO instance = new SQLiteInternalDataDAO(iDAOPath);
	// ConnectionWrapper expResult = null;
	// ConnectionWrapper result = instance.readConnectionWrapper(id, conn);
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of readAllConnectionWrappers method, of class
	// SQLiteInternalDataDAO.
	// */
	// @Test
	// public void testReadAllConnectionWrappers() {
	// Connection conn = null;
	// SQLiteInternalDataDAO instance = new SQLiteInternalDataDAO(iDAOPath);
	// Map expResult = null;
	// Map result = instance.readAllConnectionWrappers(conn);
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of makeTableSchema method, of class SQLiteInternalDataDAO.
	// */
	// @Test
	// public void testMakeTableSchema() {
	// Table t = null;
	// Connection conn = null;
	// SQLiteInternalDataDAO instance = new SQLiteInternalDataDAO(iDAOPath);
	// instance.makeTableSchema(t, conn);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of makeAllTablesForSchema method, of class SQLiteInternalDataDAO.
	// */
	// @Test
	// public void testMakeAllTablesForSchema() {
	// String SchemaName = "";
	// Connection conn = null;
	// SQLiteInternalDataDAO instance = new SQLiteInternalDataDAO(iDAOPath);
	// Map expResult = null;
	// Map result = instance.makeAllTablesForSchema(SchemaName, conn);
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of makeViewWCoordinates method, of class SQLiteInternalDataDAO.
	// */
	// @Test
	// public void testMakeViewWCoordinates() {
	// Table t = null;
	// SchemaPage page = null;
	// int numTables = 0;
	// Connection conn = null;
	// SQLiteInternalDataDAO instance = new SQLiteInternalDataDAO(iDAOPath);
	// TableView expResult = null;
	// TableView result = instance.makeViewWCoordinates(t, page, numTables,
	// conn);
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of readSchemaPages method, of class SQLiteInternalDataDAO.
	// */
	// @Test
	// public void testReadSchemaPages() {
	// SortedSchema schema = null;
	// Connection conn = null;
	// SQLiteInternalDataDAO instance = new SQLiteInternalDataDAO(iDAOPath);
	// Map expResult = null;
	// Map result = instance.readSchemaPages(schema, conn);
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }

}