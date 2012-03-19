package com.dbsvg.controllers;

import java.sql.Connection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dbsvg.common.StringUtils;
import com.dbsvg.models.ConnectionWrapper;
import com.dbsvg.models.InternalDataDAO;
import com.dbsvg.objects.view.SchemaPage;
import com.dbsvg.objects.view.SortedSchema;
import com.dbsvg.services.IDBViewerCache;
import com.dbsvg.services.SchemaService;

public abstract class AbstractDiagramInitializingController {

	protected static final Logger LOG = LoggerFactory.getLogger(AbstractDiagramInitializingController.class);

	@Autowired
	IDBViewerCache programCache;

	@Autowired
	SchemaService schemaService;

	@Autowired
	InternalDataDAO iDAO;

	protected SchemaPage initialize(String dbi, String pageId, HttpServletRequest request) throws Exception {
		initIDAO();

		String sessionPageId = (String) request.getSession().getAttribute("pageid");
		SortedSchema currentSchema = (SortedSchema) request.getSession().getAttribute("CurrentSchema");
		ConnectionWrapper currentConn = (ConnectionWrapper) request.getSession().getAttribute("CurrentConn");
		SchemaPage currentPage = (SchemaPage) request.getSession().getAttribute("CurrentPage");

		if (dbi != null && !dbi.equals("")) {

			if (StringUtils.isBlank(pageId)) {
				pageId = sessionPageId;
			}

			currentConn = programCache.getConnection(dbi);
			if (currentConn == null) {
				LOG.warn("Retreived null connection wrapper for dbi {}", dbi);
				throw new IllegalArgumentException("DBI must correspond to an existing Database Connection");
			}

			if (currentSchema == null || !currentSchema.getDbi().equals(dbi)) {
				// if no schema loaded, save the new one to the session
				currentSchema = new SortedSchema(currentConn.getSchemaId());
				currentSchema.setDbi(dbi);
			}

			// prepare the requested page of the schema for display.
			currentPage = schemaService.prepareSchema(currentSchema, dbi,
					pageId);

			request.getSession().setAttribute("CurrentSchema", currentSchema);
			request.getSession().setAttribute("CurrentConn", currentConn);
			request.getSession().setAttribute("CurrentPage", currentPage);

		}
		return currentPage;
	}

	protected void initIDAO() throws Exception {
		Connection conn = null;
		if (!programCache.isInitialized())
			try {
				conn = iDAO.getConnection();
				iDAO.setUpInternalDB(conn);

				Map<String, ConnectionWrapper> connections = iDAO.readAllConnectionWrappers(conn);
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
	}

}