package com.dbsvg.objects.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

public class VertexSetTest {

	VertexSet instance = null;

	Vertex v1 = null;
	Vertex v2 = null;
	Vertex v3 = null;
	Vertex v4 = null;

	int x1 = 100;
	int y1 = 50;
	double r1 = 10;

	int x2 = 50;
	int y2 = 20;
	double r2 = 5;

	int x3 = 150;
	int y3 = 120;
	double r3 = 15;

	int x4 = 103;
	int y4 = -5;
	double r4 = 2;

	@Before
	public void setUp() throws Exception {
		instance = new VertexSet();

		v1 = new Vertex();
		v1.setX(x1);
		v1.setY(y1);
		v1.setRadius(r1);
		v1.setSorted();

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
		v4 = new Vertex();
		v4.setX(x4);
		v4.setY(y4);
		v4.setRadius(r4);
		v4.setSorted();

		List<Vertex> ref1 = new ArrayList<Vertex>();
		ref1.add(v2);
		v1.setReferences(ref1);

		List<Vertex> ref2 = new ArrayList<Vertex>();
		ref2.add(v1);
		v2.setReferences(ref2);

		Set<Vertex> vertices = new TreeSet<Vertex>();
		vertices.add(v1);
		vertices.add(v2);
		vertices.add(v3);
		vertices.add(v4);

		instance.addVertices(vertices);
	}

	@Test
	public void calcCenterAndRadius() {
		instance.calcCenterAndRadius();
		assertEquals(119, (int) instance.getX());
		assertEquals(58, (int) instance.getY());
		assertEquals(84, (int) instance.getRadius(), 0.0);
	}

	@Test
	public void calcMinimalEnclosingCircle_setEnclosesV1() {
		instance.setX(200);
		instance.setY(200);
		instance.setRadius(200);
		instance.calcEnclosingCircle(v1);

		assertEquals(200, (int) instance.getX());
		assertEquals(200, (int) instance.getY());
		assertEquals(200, instance.getRadius(), 0.0);
	}

	@Test
	public void calcMinimalEnclosingCircle_setEnclosedByV1() {
		instance.setX(100);
		instance.setY(50);
		instance.setRadius(5);
		instance.calcEnclosingCircle(v1);

		assertEquals(100, (int) instance.getX());
		assertEquals(50, (int) instance.getY());
		assertEquals(10, instance.getRadius(), 0.0);
	}

	@Test
	public void calcMinimalEnclosingCircle_grows() {
		instance.setX(50);
		instance.setY(50);
		instance.setRadius(25);
		instance.calcEnclosingCircle(v1);

		assertEquals(67, (int) instance.getX());
		assertEquals(50, (int) instance.getY());
		assertEquals(42.5, instance.getRadius(), 0.0);
	}

	@Test
	public void contains() {
		Vertex v5 = new Vertex();
		assertTrue(instance.contains(v1));
		assertFalse(instance.contains(v5));
	}

	@Test
	public void snapshotPosition() {
		instance.setX(50);
		instance.setY(60);
		instance.setRadius(25);
		instance.snapshotPosition();
		instance.setX(150);
		instance.setY(160);

		assertEquals(50, (int) instance.getSnapshot().getX());
		assertEquals(60, (int) instance.getSnapshot().getY());

	}

	@Test
	public void snapshotAndTranslate() {
		instance.calcCenterAndRadius();
		assertEquals(119, (int) instance.getX());
		assertEquals(58, (int) instance.getY());
		assertEquals(84, (int) instance.getRadius(), 0.0);

		instance.snapshotPosition();
		instance.setX(129);
		instance.setY(48);
		instance.translateVerticesPositionsSinceSnapshot();

		assertEquals(x1 + 10, (int) v1.getX());
		assertEquals(y1 - 10, (int) v1.getY());
		assertFalse(v1.needsResort());
		assertEquals(x2 + 10, (int) v2.getX());
		assertEquals(y2 - 10, (int) v2.getY());
		assertFalse(v2.needsResort());
		assertEquals(x3 + 10, (int) v3.getX());
		assertEquals(y3 - 10, (int) v3.getY());
		assertFalse(v3.needsResort());
		assertEquals(x4 + 10, (int) v4.getX());
		assertEquals(y4 - 10, (int) v4.getY());
		assertFalse(v4.needsResort());

		assertEquals(129, (int) instance.getSnapshot().getX());
		assertEquals(48, (int) instance.getSnapshot().getY());

	}
}
