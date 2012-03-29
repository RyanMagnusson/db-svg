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
package com.dbsvg.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dbsvg.objects.view.SchemaPage;

/**
 * Displays setup pages
 */
@Controller
public class DiagramController extends AbstractDiagramInitializingController {

	@Autowired
	JsonView jsonView;

	public static final String MESSAGE = "message";

	protected static final Logger LOG = LoggerFactory.getLogger(DiagramController.class);

	@RequestMapping(value = "/diagram")
	public String showDiagram(@RequestParam("dbi") String dbi, @RequestParam(value = "page", required = false) String page, HttpServletRequest request)
			throws Exception {

		initialize(dbi, page, request);

		return "diagram";
	}

	@RequestMapping(value = "/diagram/download")
	public String downloadDiagram(@RequestParam("dbi") String dbi, @RequestParam(value = "page", required = false) String page,
			HttpServletRequest request) throws Exception {

		initialize(dbi, page, request);

		return "schema.svg";
	}

	@RequestMapping(value = "/diagram/refresh", method = RequestMethod.POST)
	public ModelAndView refreshDiagram(@RequestParam("dbi") String dbi, @RequestParam(value = "page", required = false) String page,
			HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		SchemaPage currentPage = initialize(dbi, page, request);
		schemaService.resortTableViews(currentPage);

		LOG.info("View Resorted");
		model.addAttribute(MESSAGE, "View Resorted");
		return jsonView.Render(model, response);
	}

	@RequestMapping(value = "/diagram/setTablePosition", method = RequestMethod.POST)
	public ModelAndView setTablePosition(@RequestParam("tableid") int tableid, @RequestParam("x") double x_pos, @RequestParam("y") double y_pos,
			HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		SchemaPage currentPage = (SchemaPage) request.getSession().getAttribute("CurrentPage");
		currentPage.setTableViewPosition(tableid, x_pos, y_pos);

		LOG.info("Table position set: {} {},{}", new Object[] { tableid, x_pos, y_pos });
		model.addAttribute(MESSAGE, "Table position set");
		return jsonView.Render(model, response);
	}

	@RequestMapping(value = "/diagram/save", method = RequestMethod.POST)
	public ModelAndView saveDiagram(@RequestParam("dbi") String dbi, @RequestParam(value = "page", required = false) String page,
			HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		SchemaPage currentPage = initialize(dbi, page, request);
		schemaService.saveTablePositions(currentPage);
		LOG.info("Table Positions Saved.");
		model.addAttribute(MESSAGE, "Table Positions Saved.");

		return jsonView.Render(model, response);
	}
}
