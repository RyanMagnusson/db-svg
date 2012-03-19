package com.dbsvg.services.sort;

import java.util.List;

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
	public void distributeVertices(List<? extends Vertex> vertices) {
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
