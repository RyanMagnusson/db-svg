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
package com.dbsvg.objects.view;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class VertexSet extends Vertex {

	Set<Vertex> vertices = new TreeSet<Vertex>();

	private Point<Integer> snapshot = new Point<Integer>();

	public Set<Vertex> getVertices() {
		return vertices;
	}

	public void addVertex(Vertex vertex) {
		this.vertices.add(vertex);
	}

	public void addVertices(Collection<Vertex> vertices) {
		this.vertices.addAll(vertices);
	}

	public void clearVertices() {
		this.vertices.clear();
	}

	public boolean contains(Vertex v) {
		return vertices.contains(v);
	}

	public void calcCenterAndRadius() {
		boolean first = true;
		for (Vertex v : vertices) {
			if (first) {
				setX(v.getX());
				setY(v.getY());
				setRadius(v.getRadius());
				first = false;
			} else {
				calcEnclosingCircle(v);
			}
		}
	}

	protected void calcEnclosingCircle(Vertex v) {
		double angle = Math.atan2(v.getY() - this.getY(), v.getX() - this.getX());
		Point<Integer> a = new Point<Integer>((int) (v.getX() + Math.cos(angle) * v.getRadius()), (int) (v.getY() + Math.sin(angle) * v.getRadius()));
		angle += Math.PI;
		Point<Integer> b = new Point<Integer>((int) (this.getX() + Math.cos(angle) * this.getRadius()), (int) (this.getY() + Math.sin(angle)
				* this.getRadius()));
		double rad = Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2.0)) / 2;
		if (rad < this.getRadius()) {
			return;
		} else if (rad < v.getRadius()) {
			this.setX(v.getX());
			this.setY(v.getY());
			this.setRadius(v.getRadius());
		} else {
			this.setX(((a.getX() + b.getX()) / 2));
			this.setY(((a.getY() + b.getY()) / 2));
			this.setRadius(rad);
		}
	}

	/**
	 * remember the current center of the set so all vertices can be translated
	 * later
	 */
	public void snapshotPosition() {
		snapshot.setX(getX());
		snapshot.setY(getY());
	}

	/**
	 * @return the center point as of last snapshot
	 */
	public Point<Integer> getSnapshot() {
		return snapshot;
	}

	/**
	 * Translate all vertices the delta of however much the set has moved since
	 * the last snapshot. Retakes a new snapshot upon completion.
	 */
	public void translateVerticesPositionsSinceSnapshot() {
		int deltaX = getX() - getSnapshot().getX();
		int deltaY = getY() - getSnapshot().getY();

		for (Vertex v : getVertices()) {
			v.setX(v.getX() + deltaX);
			v.setY(v.getY() + deltaY);
			v.setSorted();
		}
		snapshotPosition();
	}

}
