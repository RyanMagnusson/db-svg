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
package com.dbsvg.services;

import java.io.Serializable;
import java.util.ArrayList;
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
 * 
 * Uses a version of the Spring/Force-Based Algorithm to calculate the best
 * positions for the tables.
 * 
 * Explanation & pseudocode taken from Wikipedia:
 * http://en.wikipedia.org/wiki/Force-based_algorithms_(graph_drawing)
 * 
 * see also: http://blog.ivank.net/force-based-graph-drawing-in-as3.html
 * 
 * Each node has x,y position and dx,dy velocity and mass m. There is usually a
 * spring constant, s, and damping: 0 < damping < 1. The force toward and away
 * from nodes is calculated according to Hooke's Law and Coulomb's law or
 * similar as discussed above.
 * 
 * 
 * set up initial node velocities to (0,0) set up initial node positions
 * randomly // make sure no 2 nodes are in exactly the same position loop
 * total_kinetic_energy := 0 // running sum of total kinetic energy over all
 * particles for each node net-force := (0, 0) // running sum of total force on
 * this particular node
 * 
 * for each other node net-force := net-force + Coulomb_repulsion( this_node,
 * other_node ) next node
 * 
 * for each spring connected to this node net-force := net-force +
 * Hooke_attraction( this_node, spring ) next spring
 * 
 * // without damping, it moves forever this_node.velocity :=
 * (this_node.velocity + timestep * net-force) * damping this_node.position :=
 * this_node.position + timestep * this_node.velocity total_kinetic_energy :=
 * total_kinetic_energy + this_node.mass * (this_node.velocity)^2 next node
 * until total_kinetic_energy is less than some small number //the simulation
 * has stopped moving
 * 
 * 
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
	public void SortPage(SchemaPage currentPage, boolean resort) {
		List<? extends Vertex> tableViews = currentPage.getTableViews();
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
