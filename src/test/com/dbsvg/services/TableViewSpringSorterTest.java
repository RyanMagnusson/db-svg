/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dbsvg.services;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dbsvg.models.InternalDataDAO;

/**
 * 
 * @author derrick.bowen
 */
public class TableViewSpringSorterTest {

	TableViewSpringSorter instance;

	@Mock
	InternalDataDAO iDAO;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		instance = new TableViewSpringSorter();
		instance.iDAO = iDAO;
	}

	/**
	 * Test of getIDAO method, of class TableViewSpringSorter.
	 */
	@Test
	public void testGetIDAO() {
		InternalDataDAO result = instance.getIDAO();
		assertEquals(iDAO, result);
	}

	// /**
	// * Test of SortPage method, of class TableViewSpringSorter.
	// */
	// @Test
	// public void testSortPage() {
	// SchemaPage currentPage = null;
	// boolean resort = false;
	// TableViewSpringSorter instance = null;
	// List expResult = null;
	// List result = instance.SortPage(currentPage, resort);
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of calcLines method, of class TableViewSpringSorter.
	// */
	// @Test
	// public void testCalcLines() {
	// SchemaPage page = null;
	// TableViewSpringSorter instance = null;
	// List expResult = null;
	// List result = instance.calcLines(page);
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }

	// /**
	// * Test of setIDAO method, of class TableViewSpringSorter.
	// */
	// @Test
	// public void testSetIDAO() {
	// InternalDataDAO iDAO = null;
	// TableViewSpringSorter instance = null;
	// instance.setIDAO(iDAO);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }

}