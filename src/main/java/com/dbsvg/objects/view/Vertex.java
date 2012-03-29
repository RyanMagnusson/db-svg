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

import java.util.ArrayList;
import java.util.List;

import com.dbsvg.common.HashCodeUtil;

public class Vertex extends Point<Integer> implements Comparable<Vertex> {

	private int x = 0;
	private int y = 0;

	private Point<Double> velocity = new Point<Double>((double) 0, (double) 0);
	private Point<Double> netForce = new Point<Double>((double) 0, (double) 0);

	private double radius = 0;
	private List<Vertex> ref = new ArrayList<Vertex>();
	private boolean needsSort = true;

	public Vertex() {
	}

	public Vertex(int x, int y, double radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
	}

	/**
	 * calculates the distance between this Vertex and another.
	 * 
	 * @param o
	 * @return
	 */
	public double calcDistance(Point<Integer> o) {
		return calcDistance(o.getX(), o.getY());
	}

	/**
	 * Calculates the distance between this Vertex and a set of coordinates
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public double calcDistance(int x, int y) {
		double distance = 0.0;
		double x1 = this.getX();
		double y1 = this.getY();

		distance = Math.sqrt(Math.pow(x1 - x, 2) + Math.pow(y1 - y, 2));

		return distance;
	}

	/**
	 * calculates the distance between this Vertex and another. Takes the radius
	 * of each Vertex into account
	 * 
	 * @param o
	 * @return
	 */
	public double calcDistanceWRadius(Vertex o) {
		double distance = 0.0;
		double x1 = this.getX();
		double y1 = this.getY();
		double r1 = this.getRadius();
		double x2 = o.getX();
		double y2 = o.getY();
		double r2 = o.getRadius();

		distance = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)) - r1 - r2;

		return distance;
	}

	/**
	 * Calculates the angle between this Vertex and another.
	 * 
	 * @param o
	 * @return
	 */
	public double calcAngle(Point<Integer> o) {
		return calcAngle(o.getX(), o.getY());
	}

	/**
	 * Calculates the angle between this Vertex and a set of coordinates.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public double calcAngle(int x, int y) {
		double angle = 0.0;

		double x1 = this.getX();
		double y1 = this.getY();

		angle = Math.atan((y1 - y) / (x1 - x));
		return angle;
	}

	public int getNumLinks() {
		return ref.size();
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public List<Vertex> getReferences() {
		return this.ref;
	}

	public void setReferences(List<Vertex> ref) {
		this.ref = ref;
	}

	public void addReference(Vertex v) {
		this.ref.add(v);
	}

	@Override
	public Integer getX() {
		return x;
	}

	@Override
	public void setX(Integer x) {
		this.x = x;
		this.needsSort = true;
	}

	public void setX(double x) {
		this.x = (int) x;
		this.needsSort = true;
	}

	@Override
	public Integer getY() {
		return y;
	}

	@Override
	public void setY(Integer y) {
		this.y = y;
		this.needsSort = true;
	}

	public void setY(double y) {
		this.y = (int) y;
		this.needsSort = true;
	}

	public Point<Double> velocity() {
		return velocity;
	}

	public Point<Double> netForce() {
		return netForce;
	}

	public boolean needsResort() {
		return needsSort;
	}

	public void setNeedsSort(boolean needsSort) {
		this.needsSort = needsSort;
	}

	public void setNeedsSort() {
		this.needsSort = true;
	}

	public void setSorted() {
		this.needsSort = false;
	}

	/**
	 * Compares Vertices by the number of nodes that they link to, and then then
	 * by position (x, then y, then r)
	 * 
	 * @param o
	 * @return
	 */
	public int compareTo(Vertex o) {
		if (o == null) {
			return 1;
		}

		// If this < o, return a negative value
		if (this.getNumLinks() < o.getNumLinks()) {
			return -1;
			// If this > o, return a positive value
		} else if (this.getNumLinks() > o.getNumLinks()) {
			return 1;
		} else {
			if (this.getX() < o.getX()) {
				return -1;
			} else if (this.getX() > o.getX()) {
				return 1;
			} else {
				if (this.getY() < o.getY()) {
					return -1;
				} else if (this.getY() > o.getY()) {
					return 1;
				} else {
					if (this.getRadius() < o.getRadius()) {
						return -1;
					} else if (this.getRadius() > o.getRadius()) {
						return 1;
					} else {
						return 0;
					}
				}
			}
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof Vertex)) {
			return false;
		}
		Vertex v = (Vertex) o;
		return compareTo(v) == 0;
	}

	@Override
	public int hashCode() {
		return HashCodeUtil.generateHash(getNumLinks(), x, y, radius);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("Vertex[x:");
		builder.append(x);
		builder.append(",y:");
		builder.append(y);
		builder.append(",r:");
		builder.append(radius);
		builder.append(']');
		return builder.toString();
	}

}