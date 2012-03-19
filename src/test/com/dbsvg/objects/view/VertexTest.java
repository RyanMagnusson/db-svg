package com.dbsvg.objects.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class VertexTest {

	Vertex instance = null;
	Vertex v2 = null;
	Vertex v3 = null;

	double x1 = 100;
	double y1 = 50;
	double r1 = 10;

	double x2 = 50;
	double y2 = 20;
	double r2 = 5;

	double x3 = 150;
	double y3 = 120;
	double r3 = 15;

	@Before
	public void setUp() {
		instance = new Vertex();
		instance.setX(x1);
		instance.setY(y1);
		instance.setRadius(r1);
		instance.setSorted();

		v2 = new Vertex();
		v2.setX(x2);
		v2.setY(y2);
		v2.setRadius(r2);
		v2.setSorted();

		v3 = new Vertex();
		v3.setX(x3);
		v3.setY(y3);
		v3.setRadius(r3);
		v3.setSorted();

		List<Vertex> ref1 = new ArrayList<Vertex>();
		ref1.add(v2);
		ref1.add(v3);
		instance.setReferences(ref1);

		List<Vertex> ref2 = new ArrayList<Vertex>();
		ref2.add(instance);
		v2.setReferences(ref2);

		List<Vertex> ref3 = new ArrayList<Vertex>();
		ref3.add(instance);
		v3.setReferences(ref3);
	}

	/**
	 * Test of calcDistance method, of class TableView.
	 */
	@Test
	public void testCalcDistance_Vertex() {
		double expResult = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
		double result = instance.calcDistance(v2);
		assertEquals(expResult, result, 0.0);
	}

	/**
	 * Test of calcDistance method, of class TableView.
	 */
	@Test
	public void testCalcDistance_int_int() {
		int x = 0;
		int y = 0;
		double expResult = Math.sqrt(Math.pow(x1 - x, 2) + Math.pow(y1 - y, 2));
		double result = instance.calcDistance(x, y);
		assertEquals(expResult, result, 0.0);
	}

	/**
	 * Test of calcDistanceWRadius method, of class TableView.
	 */
	@Test
	public void testCalcDistanceWRadius() {
		double expResult = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)) - r1 - r2;
		double result = instance.calcDistanceWRadius(v2);
		assertEquals(expResult, result, 0.0);
	}

	/**
	 * Test of calcAngle method, of class TableView.
	 */
	@Test
	public void testCalcAngle_Vertex() {
		double expResult = Math.atan((y1 - y2) / (x1 - x2));
		double result = instance.calcAngle(v2);
		assertEquals(expResult, result, 0.0);
	}

	/**
	 * Test of calcAngle method, of class TableView.
	 */
	@Test
	public void testCalcAngle_int_int() {
		int x = 0;
		int y = 0;
		double expResult = Math.atan((y1 - y) / (x1 - x));
		double result = instance.calcAngle(x, y);
		assertEquals(expResult, result, 0.0);
	}

	/**
	 * Test of getNumLinks method, of class TableView.
	 */
	@Test
	public void testGetNumLinks() {
		assertEquals(2, instance.getNumLinks());
		assertEquals(1, v2.getNumLinks());
		assertEquals(1, v3.getNumLinks());
	}

	/**
	 * Test of getNumLinks method, of class TableView.
	 */
	@Test
	public void testReferences() {
		assertTrue(instance.getReferences().contains(v2));
		assertTrue(instance.getReferences().contains(v3));
	}

	/**
	 * Test of getRadius method, of class TableView.
	 */
	@Test
	public void testGetRadius() {
		double expResult = 10;
		double result = instance.getRadius();
		assertEquals(expResult, result, 0.0);
	}

	/**
	 * Test of setRadius method, of class TableView.
	 */
	@Test
	public void testSetRadius() {
		double radius = 20;
		instance.setRadius(radius);
		double result = instance.getRadius();
		assertEquals(20, result, 0.0);
		assertFalse(instance.needsResort());
	}

	/**
	 * Test of getVelocityX method, of class TableView.
	 */
	@Test
	public void testGetVelocityX() {
		double expResult = 0.0;
		double result = instance.velocity().getX();
		assertEquals(expResult, result, 0.0);
		assertFalse(instance.needsResort());
	}

	/**
	 * Test of setVelocityX method, of class TableView.
	 */
	@Test
	public void testSetVelocityX() {
		double velocityX = 50.0;
		instance.velocity().setX(velocityX);
		assertEquals(velocityX, instance.velocity().getX(), 0.0);
		assertFalse(instance.needsResort());
	}

	/**
	 * Test of getVelocityY method, of class TableView.
	 */
	@Test
	public void testGetVelocityY() {
		double expResult = 0.0;
		double result = instance.velocity().getY();
		assertEquals(expResult, result, 0.0);
		assertFalse(instance.needsResort());
	}

	/**
	 * Test of setVelocityY method, of class TableView.
	 */
	@Test
	public void testSetVelocityY() {
		double velocityY = 50.0;
		instance.velocity().setY(velocityY);
		assertEquals(velocityY, instance.velocity().getY(), 0.0);
		assertFalse(instance.needsResort());
	}

	/**
	 * Test of getVelocityX method, of class TableView.
	 */
	@Test
	public void testGetNetForceX() {
		double expResult = 0.0;
		double result = instance.netForce().getX();
		assertEquals(expResult, result, 0.0);
		assertFalse(instance.needsResort());
	}

	/**
	 * Test of setVelocityX method, of class TableView.
	 */
	@Test
	public void testSetNetForceX() {
		double netForceX = 50.0;
		instance.netForce().setX(netForceX);
		assertEquals(netForceX, instance.netForce().getX(), 0.0);
		assertFalse(instance.needsResort());
	}

	/**
	 * Test of getVelocityY method, of class TableView.
	 */
	@Test
	public void testGetNetForceY() {
		double expResult = 0.0;
		double result = instance.netForce().getY();
		assertEquals(expResult, result, 0.0);
		assertFalse(instance.needsResort());
	}

	/**
	 * Test of setVelocityY method, of class TableView.
	 */
	@Test
	public void testSetNetForceY() {
		double netForceY = 50.0;
		instance.netForce().setY(netForceY);
		assertEquals(netForceY, instance.netForce().getY(), 0.0);
		assertFalse(instance.needsResort());
	}

	/**
	 * Test of compareTo method, of class TableView.
	 */
	@Test
	public void testCompareTo() {
		assertEquals(0, instance.compareTo(instance));
		assertEquals(1, instance.compareTo(v2));
		assertEquals(-1, v2.compareTo(instance));
		assertEquals(1, instance.compareTo(null));

		Vertex v = new Vertex();
		v.setX(10);
		v.setY(10);
		v.setRadius(10);
		v.setSorted();

		Vertex vsame = new Vertex();
		vsame.setX(10);
		vsame.setY(10);
		vsame.setRadius(10);
		vsame.setSorted();

		assertEquals(0, v.compareTo(vsame));
		assertEquals(0, vsame.compareTo(v));

		Vertex vx = new Vertex();
		vx.setX(100);
		vx.setY(10);
		vx.setRadius(10);
		vx.setSorted();

		assertEquals(-1, v.compareTo(vx));
		assertEquals(1, vx.compareTo(v));

		Vertex vy = new Vertex();
		vy.setX(10);
		vy.setY(100);
		vy.setRadius(10);
		vy.setSorted();

		assertEquals(-1, v.compareTo(vy));
		assertEquals(1, vy.compareTo(v));

		Vertex vr = new Vertex();
		vr.setX(10);
		vr.setY(10);
		vr.setRadius(100);
		vr.setSorted();

		assertEquals(-1, v.compareTo(vr));
		assertEquals(1, vr.compareTo(v));
	}

	/**
	 * Test of compareTo method, of class Table.
	 */
	@Test
	public void testEquals() {

		Vertex v = new Vertex();
		v.setX(10);
		v.setY(10);
		v.setRadius(10);
		v.setSorted();

		Vertex vsame = new Vertex();
		vsame.setX(10);
		vsame.setY(10);
		vsame.setRadius(10);
		vsame.setSorted();

		assertTrue(v.equals(vsame));
		assertTrue(v.equals(v));
		assertTrue(vsame.equals(v));

		Vertex vx = new Vertex();
		vx.setX(100);
		vx.setY(10);
		vx.setRadius(10);
		vx.setSorted();

		assertFalse(v.equals(vx));
		assertFalse(vx.equals(v));

		Vertex vy = new Vertex();
		vy.setX(10);
		vy.setY(100);
		vy.setRadius(10);
		vy.setSorted();

		assertFalse(v.equals(vy));
		assertFalse(vy.equals(v));

		Vertex vr = new Vertex();
		vr.setX(10);
		vr.setY(10);
		vr.setRadius(100);
		vr.setSorted();

		assertFalse(v.equals(vr));
		assertFalse(vr.equals(v));

		Vertex vl = new Vertex();
		vl.setX(10);
		vl.setY(10);
		vl.setRadius(10);
		vl.addReference(vr);
		vl.setSorted();

		assertFalse(v.equals(vl));
		assertFalse(vl.equals(v));

		assertFalse(instance.equals(null));
	}

	/**
	 * Test of compareTo method, of class Table.
	 */
	@Test
	public void testEqualsWorksOnSubClasses() {
		Vertex v = new Vertex();
		v.setX(10);
		v.setY(10);
		v.setRadius(10);
		v.setSorted();

		Vertex vsame = new VertexSet();
		vsame.setX(10);
		vsame.setY(10);
		vsame.setRadius(10);
		vsame.setSorted();

		assertTrue(v.equals(v));
		assertTrue(v.equals(vsame));
		assertTrue(vsame.equals(v));
	}

	/**
	 * Test of compareTo method, of class Table.
	 */
	@Test
	public void testHash() {
		Vertex v = new Vertex();
		v.setX(10);
		v.setY(10);
		v.setRadius(10);
		v.setSorted();

		Vertex vsame = new Vertex();
		vsame.setX(10);
		vsame.setY(10);
		vsame.setRadius(10);
		vsame.setSorted();

		Vertex vx = new Vertex();
		vx.setX(100);
		vx.setY(10);
		vx.setRadius(10);
		vx.setSorted();

		Vertex vy = new Vertex();
		vy.setX(10);
		vy.setY(100);
		vy.setRadius(10);
		vy.setSorted();

		Vertex vr = new Vertex();
		vr.setX(10);
		vr.setY(10);
		vr.setRadius(100);
		vr.setSorted();

		Vertex vl = new Vertex();
		vl.setX(10);
		vl.setY(10);
		vl.setRadius(10);
		vl.addReference(vr);
		vl.setSorted();

		HashSet<Vertex> set = new HashSet<Vertex>();
		set.add(v);

		assertTrue(set.contains(v));
		assertTrue(set.contains(vsame));
		assertFalse(set.contains(vx));
		assertFalse(set.contains(vy));
		assertFalse(set.contains(vr));
		assertFalse(set.contains(vl));
	}

}
