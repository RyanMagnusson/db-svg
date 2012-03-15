/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dbsvg.objects.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * 
 * @author derrick.bowen
 */
public class ForeignKeyTest {

	ForeignKey instance;

	@Mock
	Column reference;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		instance = new ForeignKey();
	}

	/**
	 * Test of getDeleteRule method, of class ForeignKey.
	 */
	@Test
	public void testGetDeleteRule() {
		String deleteRule = "YES";
		instance.setDeleteRule(deleteRule);
		String result = instance.getDeleteRule();
		assertEquals(deleteRule, result);
	}

	/**
	 * Test of getUpdateRule method, of class ForeignKey.
	 */
	@Test
	public void testGetUpdateRule() {
		String updateRule = "";
		instance.setUpdateRule(updateRule);
		String result = instance.getUpdateRule();
		assertEquals(updateRule, result);
	}

	/**
	 * Test of getReference method, of class ForeignKey.
	 */
	@Test
	public void testGetReference() {
		instance.setReference(reference);
		Column result = instance.getReference();
		assertEquals(reference, result);
	}

	/**
	 * Test of getReferencedColumn method, of class ForeignKey.
	 */
	@Test
	public void testGetReferencedColumn() {
		String referencedColumn = "A COLUMN";
		instance.setReferencedColumn(referencedColumn);
		String result = instance.getReferencedColumn();
		assertEquals(referencedColumn, result);
	}

	/**
	 * Test of getReferencedTable method, of class ForeignKey.
	 */
	@Test
	public void testGetReferencedTable() {
		String referencedTable = "A TABLE";
		instance.setReferencedTable(referencedTable);
		String result = instance.getReferencedTable();
		assertEquals(referencedTable, result);
	}

	// /**
	// * Test of cloneTo method, of class ForeignKey.
	// */
	// @Test
	// public void testCloneTo() {
	// ForeignKey fk = null;
	// ForeignKey instance = new ForeignKey();
	// ForeignKey expResult = null;
	// ForeignKey result = instance.cloneTo(fk);
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// // fail.
	// fail("The test case is a prototype.");
	// }

}