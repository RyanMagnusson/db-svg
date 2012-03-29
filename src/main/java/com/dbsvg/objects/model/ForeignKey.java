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
package com.dbsvg.objects.model;

@SuppressWarnings("serial")
public class ForeignKey extends ColumnObject {

	Column reference;
	String referencedColumn;
	String referencedTable;
	String updateRule;
	String deleteRule;

	/**
    * 
    */
	public ForeignKey() {
	}

	/**
	 * 
	 * @param name
	 */
	public ForeignKey(String name) {
		super.name = name;
	}

	public ForeignKey cloneTo(ForeignKey fk) {
		fk.setReference(this.getReference());
		fk.setReferencedTable(this.getReferencedTable());
		fk.setReferencedColumn(this.getReferencedColumn());
		fk.setUpdateRule(this.getUpdateRule());
		fk.setDeleteRule(this.getDeleteRule());
		return fk;
	}

	/**
	 * 
	 * @return
	 */
	public String getDeleteRule() {
		return deleteRule;
	}

	/**
	 * 
	 * @param deleteRule
	 */
	public void setDeleteRule(String deleteRule) {
		this.deleteRule = deleteRule;
	}

	/**
	 * 
	 * @return
	 */
	public String getUpdateRule() {
		return updateRule;
	}

	/**
	 * 
	 * @param updateRule
	 */
	public void setUpdateRule(String updateRule) {
		this.updateRule = updateRule;
	}

	/**
	 * 
	 * @return
	 */
	public Column getReference() {
		return reference;
	}

	/**
	 * 
	 * @param reference
	 */
	public void setReference(Column reference) {
		this.reference = reference;
	}

	public String getReferencedColumn() {
		return referencedColumn;
	}

	public void setReferencedColumn(String referencedColumn) {
		this.referencedColumn = referencedColumn;
	}

	public String getReferencedTable() {
		return referencedTable;
	}

	public void setReferencedTable(String referencedTable) {
		this.referencedTable = referencedTable;
	}

}
