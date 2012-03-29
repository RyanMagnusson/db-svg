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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Displays setup pages
 */
@Controller
public class SetupController extends AbstractDiagramInitializingController {

	protected static final Logger LOG = LoggerFactory.getLogger(SetupController.class);

	@RequestMapping(value = "/setup")
	public String showFrontPage(@RequestParam("dbi") String dbi,
			HttpServletRequest request) throws Exception {

		initialize(dbi, null, request);

		return "setup";
	}

	@RequestMapping(value = "/setup/info")
	public String setupInfo(@RequestParam("dbi") String dbi,
			HttpServletRequest request) throws Exception {

		initialize(dbi, null, request);

		return "setupInfo";
	}

	@RequestMapping(value = "/setup/pages")
	public String setupPages(@RequestParam("dbi") String dbi,
			HttpServletRequest request) throws Exception {

		initialize(dbi, null, request);

		return "setupPages";
	}
}
