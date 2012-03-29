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

package com.dbsvg.models;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import com.dbsvg.objects.model.Table;
import com.dbsvg.objects.view.SchemaPage;
import com.dbsvg.objects.view.SortedSchema;
import com.dbsvg.objects.view.TableView;

/**
 * 
 */
public interface InternalDataDAO extends Serializable {
	public Connection getConnection() throws SQLException, ClassNotFoundException;

	public void setUpInternalDB(Connection conn) throws SQLException;

	public void saveConnectionWrapper(ConnectionWrapper c, Connection conn) throws SQLException;

	public void deleteConnectionWrapper(String id, Connection conn) throws SQLException;

	public void saveConnectionWrapperNewID(ConnectionWrapper c, Connection conn) throws SQLException;

	public void saveTable(Table t, Connection conn) throws SQLException;

	public void saveSchemaPage(SchemaPage page, Connection conn) throws SQLException;

	public void saveTablePosition(TableView tv, Connection conn) throws SQLException;

	public void verifySchema(String schema, Connection conn) throws SQLException;

	public ConnectionWrapper readConnectionWrapper(int id, Connection conn) throws SQLException;

	public Map<String, ConnectionWrapper> readAllConnectionWrappers(Connection conn) throws SQLException;

	public void makeTableSchema(Table t, Connection conn) throws SQLException;

	public void addViewsForAllTables(SchemaPage page, Connection conn) throws SQLException;

	public void removeViewsForAllTables(SchemaPage page, Connection conn) throws SQLException;

	/**
	 * Creates a TableView with the given attributes if applicable and adds it
	 * to the given page, otherwise adds nothing to the page.
	 * 
	 * @param t
	 * @param page
	 * @param numTables
	 * @param conn
	 * @param makeViewsForAllTables
	 */
	public void makeViewWCoordinates(Table t, SchemaPage page, int numTables, Connection conn, boolean makeViewsForAllTables)
			throws SQLException;

	public Map<UUID, SchemaPage> readSchemaPages(SortedSchema schema, Connection conn) throws SQLException;

	public void deleteTablePosition(TableView tv, Connection conn) throws SQLException;

	public void deleteSchemaPage(SchemaPage page, Connection conn) throws SQLException;

}
