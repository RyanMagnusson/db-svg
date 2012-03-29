package com.dbsvg.services.sort;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.dbsvg.objects.view.Vertex;

/**
 * Puts the vertex with the most references at 10,10, and then puts all of its
 * neighbors next to it, and theirs after them, etc. Not intended to layout the
 * diagram all that well, but rather to get the boxes into a good initial
 * starting position for the spring sort
 * 
 * @param v
 * @param numTables
 */
public class LinkBasedInitialPositionStrategy implements InitialPositionDistributionStrategy {

	// how many times the max radius to spread the vertices apart by
	private static final int RADIUS_MULTIPLIER = 10;
	private static final int TOTAL_NUM_DEGREES = 360;
	// To reduce the chance of any point starting at 0,0
	private static final int X_START = 10;
	private static final int Y_START = 10;

	@Override
	public void distributeVertices(Collection<? extends Vertex> vertices) {

		Set<Vertex> distributedVertices = new HashSet<Vertex>();
		Set<Vertex> remainingVertices = new HashSet<Vertex>();
		Vertex mostReferences = null;
		double maxRadius = 0.0;
		int maxNumLinks = -1;

		for (Vertex v : vertices) {
			if (v.getNumLinks() > maxNumLinks) {
				mostReferences = v;
				maxNumLinks = v.getNumLinks();
			}
			if (v.getRadius() > maxRadius) {
				maxRadius = v.getRadius();
			}
		}
		remainingVertices.addAll(vertices);

		if (maxNumLinks > 0) {

			Vertex v = mostReferences;
			int level = 1;
			int availableDegrees = TOTAL_NUM_DEGREES;
			int startDegree = 0;
			int x = X_START;
			int y = Y_START;
			double distance = RADIUS_MULTIPLIER * maxRadius;
			positionVertex(distributedVertices, remainingVertices, v, level, availableDegrees, startDegree, x, y, distance);
		}

		for (Vertex v : remainingVertices) {
			randomizePosition(v, vertices.size());
		}
	}

	/**
	 * spreads the vertices around the center point based on connections, going
	 * ever further out.
	 * 
	 * @param distributedVertices
	 * @param remainingVertices
	 * @param v
	 * @param level
	 * @param availableDegrees
	 * @param startDegree
	 * @param x
	 * @param y
	 * @param distance
	 */
	private void positionVertex(Set<Vertex> distributedVertices, Set<Vertex> remainingVertices, Vertex v, int level,
			int availableDegrees, int startDegree, int x, int y, double distance) {

		// remove before setting new position because hash may rely on x,y
		// coordinates
		remainingVertices.remove(v);
		v.setX(x);
		v.setY(y);
		distributedVertices.add(v);
		for (int i = 0; i < v.getNumLinks(); i++) {
			Vertex u = v.getReferences().get(i);
			if (!distributedVertices.contains(u)) {
				// calculates as if it were distributing the links on opposite
				// sides of the circle, then divides it into the available
				// space, and translates it by the beginning of the arc
				int vertexDegree = ((availableDegrees - startDegree) / TOTAL_NUM_DEGREES) * (i * availableDegrees / v.getNumLinks()) + startDegree;
				x = (int) (X_START + distance * level * Math.cos(vertexDegree * Math.PI / 180));
				y = (int) (Y_START + distance * level * Math.sin(vertexDegree * Math.PI / 180));
				positionVertex(distributedVertices, remainingVertices, u, level + 1, availableDegrees / v.getNumLinks(), vertexDegree, x, y, distance);
			}
		}
	}

	/**
	 * A strategy for handling unconnected vertices
	 * 
	 * @param v
	 * @param numTables
	 */
	private void randomizePosition(Vertex v, int numTables) {
		v.setX(10 + (Math.random()) * 2 * numTables * 200);
		v.setY(300 + (Math.random()) * 2 * numTables * 50);
		v.setNeedsSort();
	}
}
