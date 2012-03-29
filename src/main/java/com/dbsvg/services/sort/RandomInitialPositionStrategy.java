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

import java.util.Collection;

import com.dbsvg.objects.view.Vertex;

/**
 * Randomizes the position of the vertex on the graph. Neither X or Y should be
 * 0. The spread increases with the number of tables in the graph.
 * 
 * @param v
 * @param numTables
 */
public class RandomInitialPositionStrategy implements InitialPositionDistributionStrategy {

	@Override
	public void distributeVertices(Collection<? extends Vertex> vertices) {
		for (Vertex v : vertices) {
			randomizePosition(v, vertices.size());
		}
	}

	private void randomizePosition(Vertex v, int numTables) {
		v.setX(10 + (Math.random()) * 2 * numTables * 200);
		v.setY(300 + (Math.random()) * 2 * numTables * 50);
		v.setNeedsSort();
	}

}
