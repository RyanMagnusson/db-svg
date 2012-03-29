package com.dbsvg.controllers;

import java.sql.Connection;

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

import com.dbsvg.common.StringUtils;
import com.dbsvg.models.InternalDataDAO;
import com.dbsvg.objects.model.Table;
import com.dbsvg.objects.view.SchemaPage;
import com.dbsvg.objects.view.SortedSchema;
import com.dbsvg.objects.view.TableView;
import com.dbsvg.services.IDBViewerCache;

/**
 * Handles requests for the application menu.
 */
@Controller
public class PageController {

	protected static final String VAL = "val";
	protected static final String MESSAGE = "message";

	@Autowired
	JsonView jsonView;

	@Autowired
	InternalDataDAO iDAO;

	@Autowired
	IDBViewerCache programCache;

	private static final Logger LOG = LoggerFactory.getLogger(PageController.class);

	@RequestMapping(value = "/pages/add", method = RequestMethod.POST)
	public ModelAndView addPage(@RequestParam("dbi") String dbi, @RequestParam("title") String title, Model model, HttpServletResponse response,
			HttpServletRequest request) {

		LOG.debug("Adding page: {} {} ", dbi, title);

		if (StringUtils.isBlank(dbi) || StringUtils.isBlank(title)) {
			LOG.warn("Invalid Parameters");
			model.addAttribute(VAL, "0");
			model.addAttribute(MESSAGE, "Invalid Parameters");
			return jsonView.Render(model, response);
		}

		SortedSchema currentSchema = (SortedSchema) request.getSession().getAttribute("CurrentSchema");

		if (currentSchema == null || !currentSchema.getDbi().equals(dbi)) {
			LOG.warn("Schema {} not currently loaded.", dbi);
			model.addAttribute(VAL, "0");
			model.addAttribute(MESSAGE, "Schema " + dbi + " not currently loaded.");
			return jsonView.Render(model, response);
		}

		Connection conn = null;
		SchemaPage newPage = new SchemaPage();
		newPage.setTitle(title);
		newPage.setSchema(currentSchema);

		try {
			conn = iDAO.getConnection();
			iDAO.saveSchemaPage(newPage, conn);
			LOG.debug("Successfully saved SchemaPage with id {}", newPage.getId());
			model.addAttribute(VAL, newPage.getId());

			currentSchema.getPages().put(newPage.getId(), newPage);
			request.getSession().setAttribute("CurrentSchema", currentSchema);
			request.getSession().setAttribute("CurrentPage", null);
		} catch (Exception e) {
			LOG.error("Unable to save page to Internal DAO", e);
			model.addAttribute(VAL, "0");
			model.addAttribute(MESSAGE, "Unable to save page to Internal DAO:" + e.getMessage());

		} finally {
			closeIDaoConnection(model, conn);
		}
		return jsonView.Render(model, response);
	}

	@RequestMapping(value = "/pages/setPageForTable", method = RequestMethod.POST)
	public ModelAndView setPageForTable(@RequestParam("dbi") String dbi, @RequestParam("page") String pageId,
			@RequestParam("table") String tableName,
			@RequestParam("checked") boolean checked, Model model, HttpServletResponse response, HttpServletRequest request) {
		LOG.debug("setting page for table: {} {} checked:{}", new Object[] { dbi, pageId, checked });

		if (StringUtils.isBlank(dbi) || StringUtils.isBlank(pageId)) {
			LOG.warn("Invalid Parameters");
			model.addAttribute(VAL, "0");
			model.addAttribute(MESSAGE, "Invalid Parameters");
			return jsonView.Render(model, response);
		}

		SortedSchema currentSchema = (SortedSchema) request.getSession().getAttribute("CurrentSchema");

		if (currentSchema == null || !currentSchema.getDbi().equals(dbi)) {
			LOG.warn("Schema {} not currently loaded.", dbi);
			model.addAttribute(VAL, "0");
			model.addAttribute(MESSAGE, "Schema " + dbi + " not currently loaded.");
			return jsonView.Render(model, response);
		}

		SchemaPage page = currentSchema.getPage(pageId);
		Table table = currentSchema.getTable(tableName);
		TableView tv = null;
		if (checked) {
			tv = page.makeViewForTable(table);
			tv.randomInitialize(currentSchema.getNumTables());
		} else {
			tv = page.removeViewForTable(table);
		}

		Connection conn = null;
		try {
			conn = iDAO.getConnection();

			if (checked) {
				iDAO.saveTablePosition(tv, conn);
				LOG.debug("Successfully saved TableView for table {} in page id {}", tableName, pageId);
			} else {
				if (tv != null) {
					iDAO.deleteTablePosition(tv, conn);
					LOG.debug("Successfully deleted TableView for table {} in page id {}", tableName, pageId);
				} else {
					LOG.warn("No tableView to delete for table {} in page id {}", tableName, pageId);
				}
			}
			model.addAttribute(VAL, page.getId());
			model.addAttribute(MESSAGE, "Success!");

			currentSchema.getPages().put(page.getId(), page);
			request.getSession().setAttribute("CurrentSchema", currentSchema);
			request.getSession().setAttribute("CurrentPage", null);
		} catch (Exception e) {
			LOG.error("Unable to save to Internal DAO", e);
			model.addAttribute(VAL, "0");
			model.addAttribute(MESSAGE, "Unable to save to Internal DAO:" + e.getMessage());
		} finally {
			closeIDaoConnection(model, conn);
		}

		return jsonView.Render(model, response);
	}

	@RequestMapping(value = "/pages/save", method = RequestMethod.POST)
	public ModelAndView savePage(@RequestParam("dbi") String dbi, @RequestParam("page") String pageId, @RequestParam("title") String title,
			Model model, HttpServletResponse response, HttpServletRequest request) {
		LOG.debug("Saving page: {} {} ", new Object[] { pageId, title });
		if (StringUtils.isBlank(dbi) || StringUtils.isBlank(pageId) || StringUtils.isBlank(title)) {
			LOG.warn("Invalid Parameters");
			model.addAttribute(VAL, "0");
			model.addAttribute(MESSAGE, "Invalid Parameters");
			return jsonView.Render(model, response);
		}

		SortedSchema currentSchema = (SortedSchema) request.getSession().getAttribute("CurrentSchema");

		if (currentSchema == null || !currentSchema.getDbi().equals(dbi)) {
			LOG.warn("Schema {} not currently loaded.", dbi);
			model.addAttribute(VAL, "0");
			model.addAttribute(MESSAGE, "Schema " + dbi + " not currently loaded.");
			return jsonView.Render(model, response);
		}

		SchemaPage page = currentSchema.getPage(pageId);
		page.setTitle(title);

		Connection conn = null;
		try {
			conn = iDAO.getConnection();

			iDAO.saveSchemaPage(page, conn);

			model.addAttribute(VAL, page.getId());
			model.addAttribute(MESSAGE, "Success!");

			currentSchema.getPages().put(page.getId(), page);
			request.getSession().setAttribute("CurrentSchema", currentSchema);
			request.getSession().setAttribute("CurrentPage", null);
		} catch (Exception e) {
			LOG.error("Unable to save to Internal DAO", e);
			model.addAttribute(VAL, "0");
			model.addAttribute(MESSAGE, "Unable to save to Internal DAO:" + e.getMessage());
		} finally {
			closeIDaoConnection(model, conn);
		}

		return jsonView.Render(model, response);
	}

	@RequestMapping(value = "/pages/selectAll", method = RequestMethod.POST)
	public ModelAndView selectAll(@RequestParam("dbi") String dbi, @RequestParam("page") String pageId, Model model, HttpServletResponse response,
			HttpServletRequest request) {
		LOG.debug("Selecting all tables for page: {} {} ", new Object[] { dbi, pageId });

		if (StringUtils.isBlank(dbi) || StringUtils.isBlank(pageId)) {
			LOG.warn("Invalid Parameters");
			model.addAttribute(VAL, "0");
			model.addAttribute(MESSAGE, "Invalid Parameters");
			return jsonView.Render(model, response);
		}

		SortedSchema currentSchema = (SortedSchema) request.getSession().getAttribute("CurrentSchema");

		if (currentSchema == null || !currentSchema.getDbi().equals(dbi)) {
			LOG.warn("Schema {} not currently loaded.", dbi);
			model.addAttribute(VAL, "0");
			model.addAttribute(MESSAGE, "Schema " + dbi + " not currently loaded.");
			return jsonView.Render(model, response);
		}

		SchemaPage page = currentSchema.getPage(pageId);
		Connection conn = null;
		try {
			conn = iDAO.getConnection();

			iDAO.addViewsForAllTables(page, conn);

			model.addAttribute(VAL, page.getId());
			model.addAttribute(MESSAGE, "Success!");

			currentSchema.getPages().put(page.getId(), page);
			request.getSession().setAttribute("CurrentSchema", currentSchema);
			request.getSession().setAttribute("CurrentPage", null);
		} catch (Exception e) {
			LOG.error("Unable to save to Internal DAO", e);
			model.addAttribute(VAL, "0");
			model.addAttribute(MESSAGE, "Unable to save to Internal DAO:" + e.getMessage());
		} finally {
			closeIDaoConnection(model, conn);
		}

		return jsonView.Render(model, response);
	}

	@RequestMapping(value = "/pages/deselectAll", method = RequestMethod.POST)
	public ModelAndView deselectAll(@RequestParam("dbi") String dbi, @RequestParam("page") String pageId, Model model, HttpServletResponse response,
			HttpServletRequest request) {
		LOG.debug("deselecting all tables for page: {} {}", new Object[] { dbi, pageId });

		if (StringUtils.isBlank(dbi) || StringUtils.isBlank(pageId)) {
			LOG.warn("Invalid Parameters");
			model.addAttribute(VAL, "0");
			model.addAttribute(MESSAGE, "Invalid Parameters");
			return jsonView.Render(model, response);
		}

		SortedSchema currentSchema = (SortedSchema) request.getSession().getAttribute("CurrentSchema");

		if (currentSchema == null || !currentSchema.getDbi().equals(dbi)) {
			LOG.warn("Schema {} not currently loaded.", dbi);
			model.addAttribute(VAL, "0");
			model.addAttribute(MESSAGE, "Schema " + dbi + " not currently loaded.");
			return jsonView.Render(model, response);
		}
		SchemaPage page = currentSchema.getPage(pageId);
		Connection conn = null;
		try {
			conn = iDAO.getConnection();

			iDAO.removeViewsForAllTables(page, conn);

			model.addAttribute(VAL, page.getId());
			model.addAttribute(MESSAGE, "Success!");

			currentSchema.getPages().put(page.getId(), page);
			request.getSession().setAttribute("CurrentSchema", currentSchema);
			request.getSession().setAttribute("CurrentPage", null);
		} catch (Exception e) {
			LOG.error("Unable to save to Internal DAO", e);
			model.addAttribute(VAL, "0");
			model.addAttribute(MESSAGE, "Unable to save to Internal DAO:" + e.getMessage());
		} finally {
			closeIDaoConnection(model, conn);
		}

		return jsonView.Render(model, response);
	}

	@RequestMapping(value = "/pages/remove", method = RequestMethod.POST)
	public ModelAndView removePage(@RequestParam("dbi") String dbi, @RequestParam("page") String pageId,
			Model model, HttpServletResponse response, HttpServletRequest request) {

		LOG.debug("Removing page: {} ", pageId);

		if (StringUtils.isBlank(dbi) || StringUtils.isBlank(pageId)) {
			LOG.warn("Invalid Parameters");
			model.addAttribute(VAL, "0");
			model.addAttribute(MESSAGE, "Invalid Parameters");
			return jsonView.Render(model, response);
		}

		SortedSchema currentSchema = (SortedSchema) request.getSession().getAttribute("CurrentSchema");

		if (currentSchema == null || !currentSchema.getDbi().equals(dbi)) {
			LOG.warn("Schema {} not currently loaded.", dbi);
			model.addAttribute(VAL, "0");
			model.addAttribute(MESSAGE, "Schema " + dbi + " not currently loaded.");
			return jsonView.Render(model, response);
		}

		if (currentSchema.getNumPages() <= 1) {
			LOG.warn("Cannot remove last page");
			model.addAttribute(VAL, "0");
			model.addAttribute(MESSAGE, "Cannot remove last page");
			return jsonView.Render(model, response);
		}

		SchemaPage page = currentSchema.getPage(pageId);

		Connection conn = null;
		try {
			conn = iDAO.getConnection();

			iDAO.deleteSchemaPage(page, conn);
			LOG.debug("Successfully removed page with id {}", pageId);
			model.addAttribute(VAL, pageId);

			currentSchema.removePage(page.getId());
			request.getSession().setAttribute("CurrentSchema", currentSchema);
			request.getSession().setAttribute("CurrentPage", null);
		} catch (Exception e) {
			LOG.error("Unable to remove page from Internal DAO", e);
			model.addAttribute(VAL, "0");
			model.addAttribute(MESSAGE, "Unable to remove page from Internal DAO:" + e.getMessage());
		} finally {
			closeIDaoConnection(model, conn);
		}
		return jsonView.Render(model, response);
	}

	private void closeIDaoConnection(Model model, Connection conn) {
		try {
			conn.close();
		} catch (Exception ex) {
			// It's probably already closed or never opened but we'll
			// trace it anyway
			LOG.error("Unable to close connection to Internal DAO", ex);
			model.addAttribute(VAL, "0");
			model.addAttribute(MESSAGE, "Unable to close connection to Internal DAO:" + ex.getMessage());
		}
	}

}
