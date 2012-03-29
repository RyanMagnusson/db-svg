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
