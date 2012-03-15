package com.dbsvg.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dbsvg.models.ConnectionWrapper;
import com.dbsvg.objects.view.SchemaPage;
import com.dbsvg.objects.view.SortedSchema;
import com.dbsvg.services.IDBViewerCache;
import com.dbsvg.services.SchemaService;
import com.dbsvg.services.StringUtils;

public abstract class AbstractDiagramInitializingController {

	protected static final Logger LOG = LoggerFactory
			.getLogger(AbstractDiagramInitializingController.class);

	@Autowired
	IDBViewerCache programCache;

	@Autowired
	SchemaService schemaService;

	protected SchemaPage initialize(String dbi, String pageId,
			HttpServletRequest request) {

		String sessionPageId = (String) request.getSession().getAttribute(
				"pageid");
		SortedSchema currentSchema = (SortedSchema) request.getSession()
				.getAttribute("CurrentSchema");
		ConnectionWrapper currentConn = (ConnectionWrapper) request
				.getSession().getAttribute("CurrentConn");
		SchemaPage currentPage = (SchemaPage) request.getSession()
				.getAttribute("CurrentPage");

		if (dbi != null && !dbi.equals("")) {

			if (StringUtils.isBlank(pageId)) {
				pageId = sessionPageId;
			}

			currentConn = programCache.getConnection(dbi);
			if (currentConn == null) {
				LOG.warn("Retreived null connection wrapper for dbi {}", dbi);
			}

			if (currentSchema == null || !currentSchema.getDbi().equals(dbi)) {
				// if no schema loaded, save the new one to the session
				currentSchema = new SortedSchema(currentConn);
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

}