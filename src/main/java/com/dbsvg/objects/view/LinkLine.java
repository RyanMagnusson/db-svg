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
package com.dbsvg.objects.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dbsvg.objects.model.ForeignKey;
import com.dbsvg.objects.model.Table;

/**
 * A View Object representing a line between two tables in the diagram
 */
@SuppressWarnings("serial")
public class LinkLine implements Serializable {
	double x1;
	double y1;
	double x2;
	double y2;

	double xa1;
	double ya1;
	double xa2;
	double ya2;
	double xa3;
	double ya3;

	ForeignKey foreignkey;
	Table startingTable;
	double angle = 0.0;
	double length = 0.0;

	protected static int arrowLength = 25;
	protected static double arrowAngle = 0.52;

	SchemaPage page;

	public LinkLine(Table t, ForeignKey fk, SchemaPage page) {
		this.page = page;
		calculateLine(t, fk);
	}

	/**
	 * a private method for calculating the endpoints and arrow of a line.
	 * 
	 * @param t
	 * @param fk
	 */
	private void calculateLine(Table t, ForeignKey fk) {
		this.startingTable = t;
		this.foreignkey = fk;

		// line main end points
		if (t.getTablePageViews().get(page.getId()) != null
				&& fk.getReference().getTable().getTablePageViews()
						.get(page.getId()) != null) {
			this.x1 = t.getTablePageViews().get(page.getId()).getX()
					+ t.getWidth() / 2;
			this.y1 = t.getTablePageViews().get(page.getId()).getY()
					+ t.getHeight() / 2;
			this.x2 = fk.getReference().getTable().getTablePageViews()
					.get(page.getId()).getX()
					+ fk.getReference().getTable().getWidth() / 2;
			this.y2 = fk.getReference().getTable().getTablePageViews()
					.get(page.getId()).getY()
					+ fk.getReference().getTable().getHeight() / 2;
		}
		if ((x1 - x2) != 0) {
			this.angle = Math.atan((y1 - y2) / (x1 - x2));
		} else {
			this.angle = 0;
		}

		this.length = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));

		boolean rtl = ((x2 - x1) > 0);
		double radius = this.getEndRadius();

		// arrow head end points
		this.xa1 = x1 + (((length - radius) / length) * (x2 - x1));
		this.ya1 = y1 + (((length - radius) / length) * (y2 - y1));// + (radius
		// *
		// Math.sin(angle));
		this.xa2 = xa1 + (rtl ? -1 : 1)
				* (arrowLength * Math.cos(angle + arrowAngle));
		this.ya2 = ya1 + (rtl ? -1 : 1)
				* (arrowLength * Math.sin(angle + arrowAngle));
		this.xa3 = xa1 + (rtl ? -1 : 1)
				* (arrowLength * Math.cos(angle - arrowAngle));
		this.ya3 = ya1 + (rtl ? -1 : 1)
				* (arrowLength * Math.sin(angle - arrowAngle));
	}

	/**
	 * recalculate the endpoints and arrow of a line.
	 * 
	 * @return
	 */
	public List<TableView> recalculateLine() {
		List<TableView> returner = new ArrayList<TableView>();
		if (pageIdIsNotNull() && eitherEndOftheLineIsDirty()) {
			calculateLine(this.startingTable, this.foreignkey);
			returner.add(this.startingTable.getTablePageViews().get(
						page.getId()));
			returner.add(this.foreignkey.getReference().getTable()
						.getTablePageViews().get(page.getId()));
		}
		return returner;
	}

	private boolean eitherEndOftheLineIsDirty() {
		return this.startingTable.getTablePageViews().get(page.getId())
				.needsResort()
				|| this.foreignkey.getReference().getTable()
						.getTablePageViews().get(page.getId()).needsResort();
	}

	private boolean pageIdIsNotNull() {
		return this.startingTable.getTablePageViews().get(page.getId()) != null
				&& this.foreignkey.getReference().getTable()
						.getTablePageViews().get(page.getId()) != null;
	}

	public ForeignKey getForeignkey() {
		return foreignkey;
	}

	public void setForeignkey(ForeignKey foreignkey) {
		this.foreignkey = foreignkey;
	}

	public Table getStartingTable() {
		return startingTable;
	}

	public void setStartingTable(Table referencedTable) {
		this.startingTable = referencedTable;
	}

	public double getX1() {
		return x1;
	}

	public void setX1(double x1) {
		this.x1 = x1;
	}

	public double getX2() {
		return x2;
	}

	public void setX2(double x2) {
		this.x2 = x2;
	}

	public double getY1() {
		return y1;
	}

	public void setY1(double y1) {
		this.y1 = y1;
	}

	public double getY2() {
		return y2;
	}

	public void setY2(double y2) {
		this.y2 = y2;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getXa1() {
		return xa1;
	}

	public void setXa1(double xa1) {
		this.xa1 = xa1;
	}

	public double getXa2() {
		return xa2;
	}

	public void setXa2(double xa2) {
		this.xa2 = xa2;
	}

	public double getXa3() {
		return xa3;
	}

	public void setXa3(double xa3) {
		this.xa3 = xa3;
	}

	public double getYa1() {
		return ya1;
	}

	public void setYa1(double ya1) {
		this.ya1 = ya1;
	}

	public double getYa2() {
		return ya2;
	}

	public void setYa2(double ya2) {
		this.ya2 = ya2;
	}

	public double getYa3() {
		return ya3;
	}

	public void setYa3(double ya3) {
		this.ya3 = ya3;
	}

	public double getEndRadius() {
		return this.foreignkey.getReference().getTable().getTablePageViews()
				.get(page.getId()).getRadius();
	}

	public SchemaPage getPage() {
		return page;
	}

	public void setPage(SchemaPage page) {
		this.page = page;
	}

}
