package com.dbsvg.controllers;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.dbsvg.models.ConnectionWrapper;
import com.dbsvg.models.InternalDataDAO;
import com.dbsvg.services.IDBViewerCache;

/**
 * Handles requests for the application menu.
 */
@Controller
public class ConnectionController {

	protected static final String VAL = "val";

	@Autowired
	JsonView jsonView;

	@Autowired
	InternalDataDAO iDAO;

	@Autowired
	IDBViewerCache programCache;

	private static final Logger LOG = LoggerFactory.getLogger(ConnectionController.class);

	@RequestMapping(value = "/connections/list", method = RequestMethod.GET)
	public ModelAndView listConnections(Model model,
			HttpServletResponse response) throws Exception {

		Connection conn = null;
		try {
			conn = iDAO.getConnection();

			Map<String, ConnectionWrapper> connections = iDAO.readAllConnectionWrappers(conn);
			List<Map<String, Object>> displayList = new ArrayList<Map<String, Object>>();
			for (ConnectionWrapper cw : connections.values()) {
				Map<String, Object> display = new HashMap<String, Object>();
				display.put("id", cw.getId());
				display.put("title", cw.getTitle());
				displayList.add(display);
			}

			model.addAttribute("connections", displayList);
			LOG.info("Loaded {} saved connection(s).", connections.size());

		} catch (Exception e) {
			LOG.error("Unable to read from Internal DAO", e);
			model.addAttribute(VAL, "0");
		} finally {
			closeIDaoConnection(model, conn);
		}

		return jsonView.Render(model, response);
	}

	@RequestMapping(value = "/connections/add", method = RequestMethod.POST)
	public ModelAndView addConnection(@RequestParam("title") String title,
			@RequestParam("url") String url,
			@RequestParam("driver") String driver,
			@RequestParam("username") String username,
			@RequestParam("password") String password, Model model,
			HttpServletResponse response) {

		LOG.debug("Adding connection: {} {} {} {} [password]", new Object[] {
				title, url, driver, username });

		if (connectionParametersAreInvalid(title, url, driver, username,
				password)) {
			LOG.warn("Invalid Connection Parameters");
			model.addAttribute(VAL, "0");
			return jsonView.Render(model, response);
		}

		ConnectionWrapper cw = new ConnectionWrapper();
		cw.setTitle(title);
		cw.setUrl(url);
		cw.setDriver(driver);
		cw.setUsername(username);
		cw.setPassword(password);

		Connection conn = null;

		try {
			conn = iDAO.getConnection();
			iDAO.saveConnectionWrapperNewID(cw, conn);
			programCache.putConnection(Integer.toString(cw.getId()), cw);
			LOG.debug("Successfully saved connection with id {}", cw.getId());
			model.addAttribute(VAL, cw.getId());
		} catch (Exception e) {
			LOG.error("Unable to save connection to Internal DAO", e);
			model.addAttribute(VAL, "0");
		} finally {
			closeIDaoConnection(model, conn);
		}
		return jsonView.Render(model, response);
	}

	@RequestMapping(value = "/connections/save", method = RequestMethod.POST)
	public ModelAndView saveConnection(String dbi, String title, String url,
			String driver, String username, String password, Model model,
			HttpServletResponse response, HttpServletRequest request) {
		LOG.debug("Adding connection: {} {} {} {} [password]", new Object[] {
				title, url, driver, username });

		if (connectionParametersAreInvalid(title, url, driver, username,
				password) || StringUtils.isBlank(dbi)) {
			LOG.warn("Invalid Connection Parameters");
			model.addAttribute(VAL, "0");
			return jsonView.Render(model, response);
		}

		ConnectionWrapper cw = new ConnectionWrapper();
		cw.setId(Integer.parseInt(dbi));
		cw.setTitle(title);
		cw.setUrl(url);
		cw.setDriver(driver);
		cw.setUsername(username);
		cw.setPassword(password);

		Connection conn = null;

		try {
			conn = iDAO.getConnection();
			iDAO.saveConnectionWrapper(cw, conn);
			programCache.putConnection(Integer.toString(cw.getId()), cw);
			LOG.debug("Successfully saved connection with id {}", cw.getId());
			request.getSession().setAttribute("CurrentConn", cw);

			model.addAttribute(VAL, cw.getId());
		} catch (Exception e) {
			LOG.error("Unable to save connection to Internal DAO", e);
			model.addAttribute(VAL, "0");
		} finally {
			closeIDaoConnection(model, conn);
		}
		return jsonView.Render(model, response);
	}

	private boolean connectionParametersAreInvalid(String title, String url,
			String driver, String username, String password) {
		return StringUtils.isBlank(title) || StringUtils.isBlank(url)
				|| StringUtils.isBlank(driver) || StringUtils.isBlank(username)
				|| StringUtils.isBlank(password);
	}

	@RequestMapping(value = "/connections/remove", method = RequestMethod.POST)
	public ModelAndView removeConnection(@RequestParam("id") String id,
			Model model, HttpServletResponse response) {

		LOG.debug("Removing connection: {} ", id);

		if (id == null || id.equals("")) {
			LOG.warn("Invalid Connection Removal ID");
			model.addAttribute(VAL, "0");
			return jsonView.Render(model, response);
		}

		Connection conn = null;
		try {
			conn = iDAO.getConnection();

			iDAO.deleteConnectionWrapper(id, conn);
			Map<String, ConnectionWrapper> connections = iDAO
					.readAllConnectionWrappers(conn);
			programCache.setAllConnections(connections);
			model.addAttribute(VAL, id);
			LOG.debug("Successfully removed connection with id {}", id);

		} catch (Exception e) {
			LOG.error("Unable to remove connection from Internal DAO", e);
			model.addAttribute(VAL, "0");
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
		}
	}

}
