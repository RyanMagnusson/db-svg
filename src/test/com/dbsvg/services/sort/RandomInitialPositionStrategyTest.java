package com.dbsvg.services.sort;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.dbsvg.objects.view.Vertex;

public class RandomInitialPositionStrategyTest {

	RandomInitialPositionStrategy instance;

	Vertex v1 = null;
	Vertex v2 = null;
	Vertex v3 = null;
	Vertex v4 = null;

	double r1 = 20;
	double r2 = 30;
	double r3 = 10;
	double r4 = 2.5;

	List<Vertex> vertices;

	@Before
	public void setUp() throws Exception {
		instance = new RandomInitialPositionStrategy();
		v1 = new Vertex();
		v1.setRadius(r1);
		v1.setSorted();
		v2 = new Vertex();
		v2.setRadius(r2);
		v2.setSorted();
		v3 = new Vertex();
		v3.setRadius(r3);
		v3.setSorted();
		v4 = new Vertex();
		v4.setRadius(r4);
		v4.setSorted();
		v1.addReference(v2);
		v2.addReference(v1);
		v1.addReference(v3);
		v3.addReference(v1);
		v1.addReference(v4);
		v4.addReference(v1);

		vertices = new ArrayList<Vertex>();
		vertices.add(v1);
		vertices.add(v2);
		vertices.add(v3);
		vertices.add(v4);
	}

	@Test
	public void distributeRandomly() {
		instance.distributeVertices(vertices);
		assertTrue(v1.getX() != 0);
		assertTrue(v1.getY() != 0);
		assertTrue(v1.getRadius() == r1);
		assertTrue(v2.getX() != 0);
		assertTrue(v2.getY() != 0);
		assertTrue(v2.getRadius() == r2);
		assertTrue(v3.getX() != 0);
		assertTrue(v3.getY() != 0);
		assertTrue(v3.getRadius() == r3);
		assertTrue(v4.getX() != 0);
		assertTrue(v4.getY() != 0);
		assertTrue(v4.getRadius() == r4);
	}

}
