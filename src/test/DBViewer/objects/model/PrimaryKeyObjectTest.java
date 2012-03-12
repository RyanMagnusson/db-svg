/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DBViewer.objects.model;

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