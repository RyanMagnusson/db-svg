/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dbsvg.objects.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.dbsvg.objects.model.PrimaryForeignKey;

/**
 * 
 * @author derrick.bowen
 */
public class PrimaryForeignKeyTest {

	PrimaryForeignKey instance;

	@Before
	public void setUp() {
		instance = new PrimaryForeignKey();
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

}