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

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbsvg.common.StringUtils;
import com.dbsvg.models.ConnectionWrapper;
import com.dbsvg.models.IMainDAO;
import com.dbsvg.models.InternalDataDAO;
import com.dbsvg.objects.model.Column;
import com.dbsvg.objects.model.ColumnObject;
import com.dbsvg.objects.model.Table;
import com.dbsvg.objects.view.LinkLine;
import com.dbsvg.objects.view.SchemaPage;
import com.dbsvg.objects.view.SortedSchema;
import com.dbsvg.objects.view.TableView;

/**
 * Service in charge of reading, saving, and sorting a schema.
 * 
 */
@Service
public class SchemaService {

	protected static final Logger LOG = LoggerFactory.getLogger(SchemaService.class);

	@Autowired
	IMainDAO dao;
	@Autowired
	TableViewSpringSorter tvSorter;
	@Autowired
	InternalDataDAO iDAO;
	@Autowired
	IDBViewerCache programCache;

	// So that the Databases can be given a default page to start out with.
	// This page will be generated when no page is found, but the user can
	// rename it to whatever they want.
	public static final String DB_SVG_DEFAULT_PAGE_NAME = "Default Page";

	/**
	 * Contains Logic for preparing a schema's tables. checks to see if it has
	 * already been sorted, and things like that.
	 * 
	 * wraps the table sorter controller
	 * 
	 * @param session
	 * @param dbi
	 */
	public SchemaPage prepareSchema(SortedSchema schema, String dbi, String pageid) {

		if (schema == null) {
			throw new IllegalArgumentException("Schema cannot be null");
		}

		LOG.debug("Preparing schema: {} with dbi {} and page: {}", new Object[] { schema.getId(), dbi, pageid });
		SchemaPage page;

		// get the table sorting object
		boolean isNewTables = false;
		if (dbi != null) {
			ConnectionWrapper cw = programCache.getConnection(dbi);
			if (cw != null) {
				LOG.debug("Attempting to read schema tables");
				isNewTables = readTables(schema, cw);
			}
		}

		if ((!StringUtils.isBlank(pageid) && !pageid.equals("null")) && schema.getPage(pageid) != null) {
			LOG.debug("Found Page with ID", pageid);
			page = schema.getPage(pageid);
			prepareTableViews(page, isNewTables);
		} else {
			LOG.info("Blank page ID received, trying to retrieve first page of {}", schema.getPages().size());
			page = schema.getFirstPage();
			prepareTableViews(page, isNewTables);
		}
		return page;
	}

	/**
	 * Reads the table objects from the database based on the Connection Wrapper
	 * 
	 * @param session
	 * @param dbi
	 */
	protected boolean readTables(SortedSchema schema, ConnectionWrapper cw) {
		boolean newTables = false;
		Connection conn = null;
		if (schema.getNumTables() < 1) {
			try {
				conn = cw.connectToDB();
				schema.setTables(dao.getTables(conn, schema.getId()));
				newTables = true;
				readSchemaPages(schema);
			} catch (Exception e) {
				LOG.error(
						"Unable to Connect to Database - creating dummy table",
						e);
				Connection iconn = null;
				try {
					// TODO: save tables and read from internal DB if no
					// connection found
					// if the table exists in the internal database already,
					// just read it from there.
					// iconn= iDAO.getConnection();
					// schema.setTables(iDAO.makeAllTablesForSchema(cwmap.get(dbi).getTitle(),
					// iconn));
					Table t = new Table();
					t.setName("Unable to Connect");
					t.setSchemaId(cw.getSchemaId());
					Column c = new ColumnObject();
					c.setName(e.toString());
					t.getColumns().put(c.getName(), c);
					Map<String, Table> tables = new HashMap<String, Table>();
					tables.put(t.getName(), t);
					schema.setTables(tables);
					newTables = true;
					readSchemaPages(schema);

				} catch (Exception ie) {
					LOG.error("Error Reading Internal Database", ie);
				} finally {
					try {
						if (iconn != null)
							iconn.close();
					} catch (Exception fe) {
						LOG.error("Error Closing Internal Database", fe);
					}
				}
			} finally {
				try {
					if (conn != null)
						conn.close();
				} catch (Exception e) {
					LOG.error("Error Closing Database", e);
				}
			}
		}
		return newTables;
	}

	/**
	 * Checks to see if the Tables need to be sorted, and runs the sort if so.
	 * Also generates the lines between tables
	 * 
	 * @param isNewTables
	 */
	protected void prepareTableViews(SchemaPage page, boolean isNewTables) {
		boolean needsReSort = needsResort(page.numDirtyTableViews());
		if (needsReSort || isNewTables) {
			tvSorter.sortPage(page, needsReSort);
		} else {
			List<LinkLine> lines = page.getLines();
			List<TableView> tablesToClean = new ArrayList<TableView>();
			for (LinkLine li : lines) {
				tablesToClean.addAll(li.recalculateLine());
			}
			for (TableView tv : tablesToClean) {
				tv.setSorted();
			}
		}
		tvSorter.calcLines(page);
		page.calcDimensionsAndTranslatePageToOrigin();
	}

	private boolean needsResort(int numDirty) {
		return numDirty > 0;
	}

	/**
	 * Reads in the pages of the Schema and assigns the tables to their pages.
	 * 
	 * @throws java.lang.Exception
	 */
	protected void readSchemaPages(SortedSchema schema) throws Exception {
		if (schema.getTables().size() > 0) {
			Connection conn = iDAO.getConnection();
			Map<UUID, SchemaPage> pages = iDAO.readSchemaPages(schema, conn);
			boolean makeViewsForAllTables = false;
			if (pages.size() < 1) {
				SchemaPage sp = new SchemaPage();
				pages.put(sp.getId(), sp);
				sp.setTitle(DB_SVG_DEFAULT_PAGE_NAME);
				sp.setOrderid(0);
				sp.setSchema(schema);
				makeViewsForAllTables = true;
			}
			for (SchemaPage page : pages.values()) {
				int i = 0;
				for (Table t : schema.getTables().values()) {
					t.setViewId(i);
					iDAO.makeTableSchema(t, conn);
					iDAO.makeViewWCoordinates(t, page, schema.getTables().size(), conn, makeViewsForAllTables);
					i++;
				}
			}
			schema.setPages(pages);
			conn.close();
		}
	}

	/**
	 * Saves the positions in the already populated table views
	 * 
	 * @throws java.lang.Exception
	 */
	public void saveTablePositions(SchemaPage page) throws Exception {
		Connection conn = iDAO.getConnection();
		iDAO.verifySchema(page.getSchema().getId(), conn);
		iDAO.saveSchemaPage(page, conn);
		for (TableView tv : page.getTableViews()) {
			iDAO.saveTable(tv.getTable(), conn);
			iDAO.saveTablePosition(tv, conn);
		}
		conn.close();
	}

	/**
	 * public access method
	 * 
	 * @param resort
	 */
	public void resortTableViews(SchemaPage currentPage) {
		tvSorter.sortPage(currentPage, true);
		tvSorter.calcLines(currentPage);
		currentPage.calcDimensionsAndTranslatePageToOrigin();
	}

	public void saveTableViews(SchemaPage currentPage) {
		try {
			Connection conn = iDAO.getConnection();
			iDAO.saveSchemaPage(currentPage, conn);
			for (TableView t : currentPage.getTableViews()) {
				iDAO.saveTablePosition(t, conn);
			}
			conn.close();
		} catch (Exception e) {
			LOG.error("Error Saving Table Views", e);
		}
	}
}
