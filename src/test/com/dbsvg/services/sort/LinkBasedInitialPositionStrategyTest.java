/*
 * DB-SVG Copyright 2012 Derrick Bowen
 *
 * This file is part of DB-SVG.
 *
 *   DB-SVG is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   DB-SVG is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with DB-SVG.  If not, see <http://www.gnu.org/licenses/>.
 *   
 *   @author Derrick Bowen derrickbowen@dbsvg.com
 */
package com.dbsvg.services.sort;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.dbsvg.objects.view.Vertex;

public class LinkBasedInitialPositionStrategyTest {

	LinkBasedInitialPositionStrategy instance;

	Vertex v1 = null;
	Vertex v2 = null;
	Vertex v3 = null;
	Vertex v4 = null;
	Vertex v5 = null;
	Vertex v6 = null;

	double r1 = 20;
	double r2 = 10;
	double r3 = 30;
	double r4 = 2.5;
	double r5 = 10;
	double r6 = 10;

	List<Vertex> vertices;

	@Before
	public void setUp() throws Exception {
		instance = new LinkBasedInitialPositionStrategy();
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
		v5 = new Vertex();
		v5.setRadius(r5);
		v5.setSorted();
		v6 = new Vertex();
		v6.setRadius(r6);
		v6.setSorted();

		vertices = new ArrayList<Vertex>();
		vertices.add(v1);
		vertices.add(v2);
		vertices.add(v3);
		vertices.add(v4);
		vertices.add(v5);
		vertices.add(v6);
	}

	@Test
	public void distributeBasedOnLinks_v2TopLinked() {

		v2.addReference(v1);
		v2.addReference(v4);
		v4.addReference(v2);
		v1.addReference(v2);
		v2.addReference(v3);
		v3.addReference(v2);
		v4.addReference(v5);
		v5.addReference(v4);
		v5.addReference(v6);
		v6.addReference(v5);

		instance.distributeVertices(vertices);
		assertEquals(10, v2.getX(), 0.0);
		assertEquals(10, v2.getY(), 0.0);
		assertEquals(r2, v2.getRadius(), 0.0);
		assertEquals(310, v1.getX(), 0.0);
		assertEquals(10, v1.getY(), 0.0);
		assertEquals(r1, v1.getRadius(), 0.0);
		assertEquals(-140, v3.getX(), 0.0);
		assertEquals(-249, v3.getY(), 0.0);
		assertEquals(r3, v3.getRadius(), 0.0);
		assertEquals(-139, v4.getX(), 0.0);
		assertEquals(269, v4.getY(), 0.0);
		assertEquals(r4, v4.getRadius(), 0.0);
		assertEquals(-289, v5.getX(), 0.0);
		assertEquals(529, v5.getY(), 0.0);
		assertEquals(r5, v5.getRadius(), 0.0);
		assertEquals(-439, v6.getX(), 0.0);
		assertEquals(789, v6.getY(), 0.0);
		assertEquals(r6, v6.getRadius(), 0.0);
	}

	@Test
	public void distributeBasedOnLinks_extraUnreferenced() {

		v2.addReference(v1);
		v2.addReference(v4);
		v4.addReference(v2);
		v1.addReference(v2);
		v2.addReference(v3);
		v3.addReference(v2);
		v4.addReference(v5);
		v5.addReference(v4);
		v5.addReference(v6);
		v6.addReference(v5);
		Vertex v7 = new Vertex();
		v7.setRadius(r6);
		v7.setSorted();
		vertices.add(v7);

		instance.distributeVertices(vertices);
		assertEquals(10, v2.getX(), 0.0);
		assertEquals(10, v2.getY(), 0.0);
		assertEquals(r2, v2.getRadius(), 0.0);
		assertEquals(310, v1.getX(), 0.0);
		assertEquals(10, v1.getY(), 0.0);
		assertEquals(r1, v1.getRadius(), 0.0);
		assertEquals(-140, v3.getX(), 0.0);
		assertEquals(-249, v3.getY(), 0.0);
		assertEquals(r3, v3.getRadius(), 0.0);
		assertEquals(-139, v4.getX(), 0.0);
		assertEquals(269, v4.getY(), 0.0);
		assertEquals(r4, v4.getRadius(), 0.0);
		assertEquals(-289, v5.getX(), 0.0);
		assertEquals(529, v5.getY(), 0.0);
		assertEquals(r5, v5.getRadius(), 0.0);
		assertEquals(-439, v6.getX(), 0.0);
		assertEquals(789, v6.getY(), 0.0);
		assertEquals(r6, v6.getRadius(), 0.0);
		assertTrue(v7.getX() != 0);
		assertTrue(v7.getY() != 0);
	}

}
