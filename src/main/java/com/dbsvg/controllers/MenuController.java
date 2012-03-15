package com.dbsvg.controllers;

import java.sql.Connection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dbsvg.models.ConnectionWrapper;
import com.dbsvg.models.InternalDataDAO;
import com.dbsvg.services.IDBViewerCache;

/**
 * Handles requests for the application menu.
 */
@Controller
public class MenuController {

	@Autowired
	InternalDataDAO iDAO;

	@Autowired
	IDBViewerCache programCache;

	private static final Logger LOG = LoggerFactory
			.getLogger(MenuController.class);

	@RequestMapping(value = { "/menu", "/" })
	public String showMenu() throws Exception {

		Connection conn = null;
		try {
			conn = iDAO.getConnection();
			iDAO.setUpInternalDB(conn);

			Map<String, ConnectionWrapper> connections = iDAO
					.readAllConnectionWrappers(conn);
			programCache.setAllConnections(connections);
			LOG.info("Loaded {} saved connection(s).", connections.size());

		} catch (Exception e) {
			LOG.error("Unable to read from Internal DAO", e);
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				// It's probably already closed or never opened but we'll
				// trace it anyway

				LOG.error("Unable to close connection to Internal DAO", ex);
				throw ex;
			}
		}

		return "menu";
	}

}
