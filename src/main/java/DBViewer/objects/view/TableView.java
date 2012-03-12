/*
 * DB-SVG Copyright 2009 Derrick Bowen
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
 *
 */
package DBViewer.objects.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import DBViewer.objects.model.ForeignKey;
import DBViewer.objects.model.Table;

/**
 * A view object for Tables. Holds a table, its coordinates, references, and
 * velocity(used during sorting) in the schema.
 * 
 * 
 * @author horizon
 */
@SuppressWarnings("serial")
public class TableView implements Comparable<TableView>, Serializable {

	Table table;
	int x = 0;
	int y = 0;
	double velocityX = 0.0;
	double velocityY = 0.0;
	double radius = 0;
	int numLinks = 0;
	List<TableView> referencesTo = new ArrayList<TableView>();
	List<TableView> referencedBy = new ArrayList<TableView>();
	List<TableView> ref = null;
	SchemaPage page;
	int id = 0;
	boolean dirty = true;

	public TableView(Table t) {
		table = t;
	}

	public TableView(Table t, SchemaPage p) {
		table = t;
		page = p;
	}

	/**
	 * goes through the foreign keys, populates the list of TableViews to get
	 * all references for the current page and the radius of the table.
	 */
	public void calcLinksAndRadius() {
		this.numLinks = 0;
		Map<String, ForeignKey> fkeys = this.table.getForeignKeys();
		Map<String, Table> referencers = this.table.getReferencingTables();
		for (ForeignKey fk : fkeys.values()) {
			if (fk.getReference().getTable().getTablePageViews().get(this.page.getId()) != null) {
				this.getReferencesTo().add(fk.getReference().getTable().getTablePageViews().get(this.page.getId()));
				this.numLinks++;
			}
		}
		for (Table t : referencers.values()) {
			if (t.getTablePageViews().get(this.page.getId()) != null) {
				this.getReferencesTo().add(t.getTablePageViews().get(this.page.getId()));
				this.numLinks++;
			}
		}

		int height = this.getTable().getHeight();
		int width = this.getTable().getWidth();

		double diagonal = Math.sqrt(Math.pow(height, 2) + Math.pow(width, 2));

		this.radius = (11.0 / 20.0) * diagonal;
	}

	/**
	 * calculates the distance between this TableView and another.
	 * 
	 * @param o
	 * @return
	 */
	public double calcDistance(TableView o) {
		return calcDistance(o.getX(), o.getY());
	}

	/**
	 * Calculates the distance between this tableView and a set of coordinates
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
	 * calculates the distance between this TableView and another. Takes the
	 * radius of each table into account
	 * 
	 * @param o
	 * @return
	 */
	public double calcDistanceWRadius(TableView o) {
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
	 * Calculates the angle between this TableView and another.
	 * 
	 * @param o
	 * @return
	 */
	public double calcAngle(TableView o) {
		return calcAngle(o.getX(), o.getY());
	}

	/**
	 * Calculates the angle between this TableView and a set of coordinates.
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
		return numLinks;
	}

	public void setNumLinks(int numLinks) {
		this.numLinks = numLinks;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public List<TableView> getReferencesTo() {
		return referencesTo;
	}

	public void setReferencesTo(List<TableView> referencesTo) {
		this.referencesTo = referencesTo;
		this.ref = null;
		getReferences();
	}

	public List<TableView> getReferencedBy() {
		return referencedBy;
	}

	public void setReferencedBy(List<TableView> referencedBy) {
		this.referencedBy = referencedBy;
		this.ref = null;
		getReferences();
	}

	public List<TableView> getReferences() {
		if (this.ref == null) {
			this.ref = new ArrayList<TableView>();
			for (TableView t : this.referencedBy) {
				if (!this.ref.contains(t))
					this.ref.add(t);
			}
			for (TableView t : this.referencesTo) {
				if (!this.ref.contains(t))
					this.ref.add(t);
			}
		}
		return this.ref;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		this.dirty = true;
	}

	public void setX(double x) {
		this.x = (int) x;
		this.dirty = true;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		this.dirty = true;
	}

	public void setY(double y) {
		this.y = (int) y;
		this.dirty = true;
	}

	public double getVelocityX() {
		return velocityX;
	}

	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}

	public double getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SchemaPage getPage() {
		return page;
	}

	public void setPage(SchemaPage page) {
		this.page = page;
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	public void setDirty() {
		this.dirty = true;
	}

	public void setClean() {
		this.dirty = false;
	}

	/**
	 * Compares TableViews by the number of nodes that they contain.
	 * 
	 * @param o
	 * @return
	 */
	public int compareTo(TableView o) {
		// If this < o, return a negative value
		if (this.getNumLinks() < o.getNumLinks())
			return -1;
		// If this = o, return 0
		if (this.getNumLinks() == o.getNumLinks())
			return 0;
		// If this > o, return a positive value
		if (this.getNumLinks() > o.getNumLinks())
			return 1;

		return 0;
	}

}
