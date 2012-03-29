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
	int x1;
	int y1;
	int x2;
	int y2;

	int xa1;
	int ya1;
	int xa2;
	int ya2;
	int xa3;
	int ya3;
	boolean visible;

	ForeignKey foreignkey;
	Table startingTable;
	double angle = 0.0;
	double length = 0.0;

	protected static final int ARROW_LENGTH = 25;
	protected static final double ARROW_ANGLE = 0.52;

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
	protected void calculateLine(Table t, ForeignKey fk) {
		this.startingTable = t;
		this.foreignkey = fk;

		// line main end points
		if (t.getTablePageViews().get(page.getId()) != null && fk.getReference().getTable().getTablePageViews().get(page.getId()) != null) {
			this.x1 = t.getTablePageViews().get(page.getId()).getX() + t.getWidth() / 2;
			this.y1 = t.getTablePageViews().get(page.getId()).getY() + t.getHeight() / 2;
			this.x2 = fk.getReference().getTable().getTablePageViews().get(page.getId()).getX() + fk.getReference().getTable().getWidth() / 2;
			this.y2 = fk.getReference().getTable().getTablePageViews().get(page.getId()).getY() + fk.getReference().getTable().getHeight() / 2;
			visible = true;
		} else {
			// do not create a line if both tables are not on this page.
			this.x1 = 0;
			this.x2 = 0;
			this.xa1 = 0;
			this.xa2 = 0;
			this.xa3 = 0;
			this.y1 = 0;
			this.y2 = 0;
			this.ya1 = 0;
			this.ya2 = 0;
			this.ya3 = 0;
			visible = false;
			return;
		}
		if ((x1 - x2) != 0) {
			this.angle = Math.atan((double) (y1 - y2) / (x1 - x2));
		} else {
			this.angle = 0;
		}

		this.length = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));

		boolean rtl = ((x2 - x1) > 0);
		double radius = this.getEndRadius();

		// arrow head end points
		this.xa1 = (int) (x1 + (((length - radius) / length) * (x2 - x1)));
		this.ya1 = (int) (y1 + (((length - radius) / length) * (y2 - y1)));

		this.xa2 = xa1 + (int) ((rtl ? -1 : 1) * (ARROW_LENGTH * Math.cos(angle + ARROW_ANGLE)));
		this.ya2 = ya1 + (int) ((rtl ? -1 : 1) * (ARROW_LENGTH * Math.sin(angle + ARROW_ANGLE)));
		this.xa3 = xa1 + (int) ((rtl ? -1 : 1) * (ARROW_LENGTH * Math.cos(angle - ARROW_ANGLE)));
		this.ya3 = ya1 + (int) ((rtl ? -1 : 1) * (ARROW_LENGTH * Math.sin(angle - ARROW_ANGLE)));
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
			returner.add(this.startingTable.getTablePageViews().get(page.getId()));
			returner.add(this.foreignkey.getReference().getTable().getTablePageViews().get(page.getId()));
		}
		return returner;
	}

	private boolean eitherEndOftheLineIsDirty() {
		return this.startingTable.getTablePageViews().get(page.getId()).needsResort()
				|| this.foreignkey.getReference().getTable().getTablePageViews().get(page.getId()).needsResort();
	}

	private boolean pageIdIsNotNull() {
		return this.startingTable.getTablePageViews().get(page.getId()) != null
				&& this.foreignkey.getReference().getTable().getTablePageViews().get(page.getId()) != null;
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

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
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

	public int getXa1() {
		return xa1;
	}

	public void setXa1(int xa1) {
		this.xa1 = xa1;
	}

	public int getXa2() {
		return xa2;
	}

	public void setXa2(int xa2) {
		this.xa2 = xa2;
	}

	public int getXa3() {
		return xa3;
	}

	public void setXa3(int xa3) {
		this.xa3 = xa3;
	}

	public int getYa1() {
		return ya1;
	}

	public void setYa1(int ya1) {
		this.ya1 = ya1;
	}

	public int getYa2() {
		return ya2;
	}

	public void setYa2(int ya2) {
		this.ya2 = ya2;
	}

	public int getYa3() {
		return ya3;
	}

	public void setYa3(int ya3) {
		this.ya3 = ya3;
	}

	public boolean isVisible() {
		return visible;
	}

	public Double getEndRadius() {
		if (this.foreignkey.getReference().getTable().getTablePageViews().get(page.getId()) != null) {
			return this.foreignkey.getReference().getTable().getTablePageViews().get(page.getId()).getRadius();
		} else {
			return (Double) null;
		}
	}

	public SchemaPage getPage() {
		return page;
	}

	public void setPage(SchemaPage page) {
		this.page = page;
	}

}
