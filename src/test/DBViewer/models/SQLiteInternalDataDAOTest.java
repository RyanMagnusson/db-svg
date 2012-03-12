/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DBViewer.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * 
 * @author derrick.bowen
 */
public class SQLiteInternalDataDAOTest {

	private String iDAOPath = "target/test.db";

	SQLiteInternalDataDAO instance;

	@Mock
	InternalDataDAO iDAO = null;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		instance = new SQLiteInternalDataDAO(iDAOPath);
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
	// /**
	// * Test of saveConnectionWrapper method, of class SQLiteInternalDataDAO.
	// */
	// @Test
	// public void testSaveConnectionWrapper() {
	// System.out.println("saveConnectionWrapper");
	// ConnectionWrapper c = null;
	// Connection conn = null;
	// SQLiteInternalDataDAO instance = new SQLiteInternalDataDAO(iDAOPath);
	// instance.saveConnectionWrapper(c, conn);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of deleteConnectionWrapper method, of class SQLiteInternalDataDAO.
	// */
	// @Test
	// public void testDeleteConnectionWrapper() {
	// System.out.println("deleteConnectionWrapper");
	// String id = "";
	// Connection conn = null;
	// SQLiteInternalDataDAO instance = new SQLiteInternalDataDAO(iDAOPath);
	// instance.deleteConnectionWrapper(id, conn);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of saveConnectionWrapperNewID method, of class
	// * SQLiteInternalDataDAO.
	// */
	// @Test
	// public void testSaveConnectionWrapperNewID() {
	// System.out.println("saveConnectionWrapperNewID");
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
	// System.out.println("saveTable");
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
	// System.out.println("saveSchemaPage");
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
	// System.out.println("saveTablePosition");
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
	// System.out.println("verifySchema");
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
	// System.out.println("readConnectionWrapper");
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
	// System.out.println("readAllConnectionWrappers");
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
	// System.out.println("makeTableSchema");
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
	// System.out.println("makeAllTablesForSchema");
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
	// System.out.println("makeViewWCoordinates");
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
	// System.out.println("readSchemaPages");
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