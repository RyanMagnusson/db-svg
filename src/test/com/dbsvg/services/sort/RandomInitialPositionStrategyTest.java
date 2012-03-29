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
