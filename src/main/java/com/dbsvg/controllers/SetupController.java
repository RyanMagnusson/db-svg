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
