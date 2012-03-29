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
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dbsvg.common.HashCodeUtil;
import com.dbsvg.objects.model.ForeignKey;
import com.dbsvg.objects.model.Table;

/**
 * A view object for Tables. Holds a table, its coordinates, references, and
 * velocity(used during sorting) in the schema.
 * 
 * 
 * @author horizon
 */
@SuppressWarnings("serial")
public class TableView extends Vertex implements Serializable {

	private Table table;
	private Set<TableView> referencesTo = new HashSet<TableView>();
	private Set<TableView> referencedBy = new HashSet<TableView>();
	private SchemaPage page;
	private int id = 0;

	public TableView(Table t, SchemaPage p) {
		table = t;
		page = p;
		setId(t.getViewId());
	}

	/**
	 * goes through the foreign keys, populates the list of TableViews to get
	 * all references for the current page and the radius of the table.
	 */
	public void calcLinksAndRadius() {
		Map<String, ForeignKey> fkeys = this.table.getForeignKeys();
		Map<String, Table> referencers = this.table.getReferencingTables();
		for (ForeignKey fk : fkeys.values()) {
			if (fk.getReference().getTable().getTablePageViews().get(this.page.getId()) != null) {
				this.getReferencesTo().add(fk.getReference().getTable().getTablePageViews().get(this.page.getId()));
			}
		}
		for (Table t : referencers.values()) {
			if (t.getTablePageViews().get(this.page.getId()) != null) {
				this.getReferencedBy().add(t.getTablePageViews().get(this.page.getId()));
			}
		}

		int height = this.getTable().getHeight();
		int width = this.getTable().getWidth();

		double diagonal = Math.sqrt(Math.pow(height, 2) + Math.pow(width, 2));

		setRadius((11.0 / 20.0) * diagonal);

		setReferences(null);
		initializeReferences();
	}

	public Set<TableView> getReferencesTo() {
		return referencesTo;
	}

	public void setReferencesTo(Collection<TableView> referencesTo) {
		this.referencesTo.clear();
		this.referencesTo.addAll(referencesTo);
		setReferences(null);
		initializeReferences();
	}

	public Set<TableView> getReferencedBy() {
		return referencedBy;
	}

	public void setReferencedBy(Collection<TableView> referencedBy) {
		this.referencedBy.clear();
		this.referencedBy.addAll(referencedBy);
		setReferences(null);
		initializeReferences();
	}

	private void initializeReferences() {
		if (getReferences() == null) {
			List<Vertex> ref = new ArrayList<Vertex>();
			for (TableView t : this.referencedBy) {
				if (!ref.contains(t))
					ref.add(t);
			}
			for (TableView t : this.referencesTo) {
				if (!ref.contains(t))
					ref.add(t);
			}
			setReferences(ref);
		}
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
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

	@Override
	public int getNumLinks() {
		initializeReferences();
		return super.getNumLinks();
	}

	/**
	 * Compares Vertices by the number of nodes that they link to, and then then
	 * by position (x, then y, then r)
	 * 
	 * @param o
	 * @return
	 */
	@Override
	public int compareTo(Vertex o) {
		if (o == null) {
			return 1;
		}
		if (!o.getClass().equals(TableView.class)) {
			return 1;
		}
		TableView tv = (TableView) o;

		int result = super.compareTo(tv);
		if (result != 0) {
			return result;
		} else {
			return this.getTable().compareTo(tv.getTable());
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof TableView)) {
			return false;
		}
		TableView v = (TableView) o;
		if (!this.page.equals(v.getPage())) {
			return false;
		}
		return this.compareTo(v) == 0;
	}

	@Override
	public int hashCode() {
		return HashCodeUtil.generateHash(super.hashCode(), table);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("TableView[");
		builder.append(table.getName());
		builder.append(" x:");
		builder.append(getX());
		builder.append(",y:");
		builder.append(getY());
		builder.append(",r:");
		builder.append(getRadius());
		builder.append("] page:");
		builder.append(page.toString());
		return builder.toString();
	}

	public void randomInitialize(int numTables) {
		this.setX((Math.random()) * 2 * numTables * 200);
		this.setY((Math.random()) * 2 * numTables * 50 + 300);
		this.calcLinksAndRadius();
		this.setNeedsSort();
	}

}
