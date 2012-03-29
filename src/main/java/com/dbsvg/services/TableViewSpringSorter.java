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
package com.dbsvg.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbsvg.objects.model.ForeignKey;
import com.dbsvg.objects.view.LinkLine;
import com.dbsvg.objects.view.SchemaPage;
import com.dbsvg.objects.view.TableView;
import com.dbsvg.objects.view.Vertex;
import com.dbsvg.services.sort.VertexSpringSorter;

/**
 * Prepares TableViews for sort, and handles a few extra TableView specific
 * functions.
 * 
 * Sorting is done through the injected VertexSpringSorter object.
 */
@SuppressWarnings("serial")
@Service
public class TableViewSpringSorter implements Serializable {

	protected static final Logger LOG = LoggerFactory.getLogger(TableViewSpringSorter.class);

	@Autowired
	VertexSpringSorter vertexSorter;

	/**
	 * Main action method, gets everything set up and runs the Force-Based
	 * sorting algorithm
	 * 
	 * @param tables
	 * @return
	 */
	public void sortPage(SchemaPage currentPage, boolean resort) {
		Collection<? extends Vertex> tableViews = currentPage.getTableViews();
		if (resort) {
			vertexSorter.sort(tableViews);
			currentPage.setSorted(true);
		}
	}

	/**
	 * generates lines for the diagram.
	 * 
	 * @param tableViews
	 * @return
	 */
	public List<LinkLine> calcLines(SchemaPage page) {
		List<LinkLine> lines = new ArrayList<LinkLine>();
		for (TableView tv : page.getTableViews()) {
			for (ForeignKey fk : tv.getTable().getForeignKeys().values()) {
				lines.add(new LinkLine(tv.getTable(), fk, page));
			}
		}
		page.setLines(lines);
		return lines;
	}

}
