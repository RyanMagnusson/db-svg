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
package com.dbsvg.models;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dbsvg.objects.model.Table;
import com.dbsvg.objects.view.SchemaPage;
import com.dbsvg.objects.view.SortedSchema;
import com.dbsvg.objects.view.TableView;

/**
 * A very basic DAO for storing information about table positions in an SQLite
 * database
 * 
 */
@SuppressWarnings("serial")
@Service
public class SQLiteInternalDataDAO implements Serializable, InternalDataDAO {

	protected static final Logger LOG = LoggerFactory.getLogger(SQLiteInternalDataDAO.class);

	private String url = "jdbc:sqlite:";
	private String driver = "org.sqlite.JDBC";

	@Value("${config.db.location:config.db}")
	String path = "unset.db";

	/**
	 * Generates a connection from the internal DB based on the initialized
	 * path.
	 * 
	 * @return
	 * @throws java.sql.SQLException
	 * @throws ClassNotFoundException
	 */
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		LOG.info("INFO: Connecting to Internal DAO with path: {}", path);
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url + path);
			return conn;
		} catch (SQLException e) {
			LOG.error("Internal DAO path incorrect.", e);
			throw e;
		}
	}

	/**
	 * Makes sure that all the correct tables have been created in the internal
	 * db
	 * 
	 * @param conn
	 */
	public void setUpInternalDB(Connection conn) {
		try {
			Statement st = conn.createStatement();
			st.executeUpdate(" CREATE TABLE IF NOT EXISTS schema (title PRIMARY KEY); ");
			st.executeUpdate(" CREATE TABLE IF NOT EXISTS table_schema (id PRIMARY KEY, name, schema,"
					+ " UNIQUE (name, schema), "
					+ " FOREIGN KEY (schema) REFERENCES schema(title));");
			st.executeUpdate(" CREATE TABLE IF NOT EXISTS schema_page (id PRIMARY KEY, orderid INTEGER, title, schema, "
					+ " FOREIGN KEY (schema) REFERENCES schema(title)); ");
			st.executeUpdate(" CREATE TABLE IF NOT EXISTS table_page_position (pageid, tableid, x_pos INTEGER, y_pos INTEGER,"
					+ " UNIQUE (pageid, tableid), "
					+ " FOREIGN KEY (pageid) REFERENCES schema_page(id), "
					+ " FOREIGN KEY (tableid) REFERENCES table_schema(id) "
					+ "); ");
			st.executeUpdate(" CREATE TABLE IF NOT EXISTS connection (id INTEGER PRIMARY KEY, title, url, driver, username, password); ");
		} catch (Exception e) {
			LOG.error("Ignoring Table Creation Error:", e);
		}
	}

	/**
	 * The method to save a connection when there is already an ID.
	 * 
	 * @param c
	 * @param conn
	 */
	public void saveConnectionWrapper(ConnectionWrapper c, Connection conn) {
		String insertConnectionSQL = "INSERT OR REPLACE INTO connection (id, title, url, driver, username, password) VALUES (?,?,?,?,?,?);";
		try {
			PreparedStatement ps = conn.prepareStatement(insertConnectionSQL);
			ps.setInt(1, c.getId());
			ps.setString(2, c.getTitle());
			ps.setString(3, c.getUrl());
			ps.setString(4, c.getDriver());
			ps.setString(5, c.getUsername());
			ps.setString(6, c.getPassword());
			ps.executeUpdate();

			ps.close();
		} catch (Exception e) {
			LOG.error("Ignoring Connection Insert or Replace Error:", e);
		}
	}

	/**
	 * I chose to delete becuase it's easy enough to put it in again, and maybe
	 * they deleted it because the information was sensitive.
	 * 
	 * @param c
	 * @param conn
	 */
	public void deleteConnectionWrapper(String id, Connection conn) {
		String insertConnectionSQL = "DELETE FROM connection WHERE id = ?;";
		try {
			PreparedStatement ps = conn.prepareStatement(insertConnectionSQL);
			ps.setString(1, id);
			ps.executeUpdate();

			ps.close();
		} catch (Exception e) {
			LOG.error("Ignoring Connection Delete Error:", e);
		}
	}

	/**
	 * The method to save a connection when there is NOT already an ID. saves to
	 * the db and adds the id to the object.
	 * 
	 * @param c
	 * @param conn
	 */
	public void saveConnectionWrapperNewID(ConnectionWrapper c, Connection conn) {
		String insertConnectionSQL = "INSERT OR REPLACE INTO connection (title, url, driver, username, password) VALUES (?,?,?,?,?);";
		try {
			PreparedStatement ps = conn.prepareStatement(insertConnectionSQL);
			ps.setString(1, c.getTitle());
			ps.setString(2, c.getUrl());
			ps.setString(3, c.getDriver());
			ps.setString(4, c.getUsername());
			ps.setString(5, c.getPassword());
			ps.executeUpdate();

			ps.close();

			String lastrowidSQL = "select last_insert_rowid() as id";
			PreparedStatement ps2 = conn.prepareStatement(lastrowidSQL);

			ResultSet rs = ps2.executeQuery();

			if (rs.next()) {
				c.setId(rs.getInt("id"));
			}
			ps2.close();
			rs.close();
			LOG.info("Saved Connection with id {}", c.getId());

		} catch (Exception e) {
			LOG.error("Ignoring Save Connection New ID Error:", e);
		}
	}

	/**
	 * Inserts a table into the internal DB. Need to save table positions
	 * separately.
	 * 
	 * @param t
	 * @param conn
	 */
	public void saveTable(Table t, Connection conn) {
		String insertTableSQL = "INSERT OR REPLACE INTO table_schema (id,name,schema) VALUES (?,?,?);";
		try {
			PreparedStatement ps = conn.prepareStatement(insertTableSQL);
			ps.setString(1, t.getId().toString());
			ps.setString(2, t.getName());
			ps.setString(3, t.getSchemaId());
			ps.executeUpdate();
			ps.close();

			LOG.info("Saved Table {}", t);
		} catch (Exception e) {
			LOG.error("Ignoring Table Save Error:", e);
		}
	}

	/**
	 * Inserts or updates a TableView
	 * 
	 * @param tv
	 * @param conn
	 */
	public void saveTablePosition(TableView tv, Connection conn) {
		String insertTableViewSQL = "INSERT OR REPLACE INTO table_page_position (pageid,tableid,x_pos,y_pos) VALUES (?,?,?,?);";
		try {
			PreparedStatement ps = conn.prepareStatement(insertTableViewSQL);
			ps.setString(1, tv.getPage().getId().toString());
			ps.setString(2, tv.getTable().getId().toString());
			ps.setDouble(3, tv.getX());
			ps.setDouble(4, tv.getY());
			ps.executeUpdate();
			ps.close();

			LOG.info("Saved TableView {}", tv);
		} catch (Exception e) {
			LOG.error("Ignoring Table Position Save Error:", e);
		}
	}

	/**
	 * Deletes a TableView
	 * 
	 * @param tv
	 * @param conn
	 */
	public void deleteTablePosition(TableView tv, Connection conn) {
		String insertTableViewSQL = "DELETE FROM table_page_position WHERE pageid=? AND tableid=?;";
		try {
			PreparedStatement ps = conn.prepareStatement(insertTableViewSQL);
			ps.setString(1, tv.getPage().getId().toString());
			ps.setString(2, tv.getTable().getId().toString());
			ps.executeUpdate();
			ps.close();

			LOG.info("DELETED TableView {}", tv);
		} catch (Exception e) {
			LOG.error("Ignoring Table Position Delete Error:", e);
		}
	}

	/**
	 * Save a SchemaPage to the internal DB
	 * 
	 * @param page
	 * @param conn
	 */
	public void saveSchemaPage(SchemaPage page, Connection conn) {
		String insertPageSQL = "INSERT OR REPLACE INTO schema_page (id, orderid, title, schema) VALUES (?,?,?,?);";
		try {
			PreparedStatement ps = conn.prepareStatement(insertPageSQL);
			ps.setString(1, page.getId().toString());
			ps.setInt(2, page.getOrderid());
			ps.setString(3, page.getTitle());
			ps.setString(4, page.getSchema().getId());
			ps.executeUpdate();

		} catch (Exception e) {
			LOG.error("Ignoring SchemaPage Save Error:", e);
		}

	}

	/**
	 * Save a SchemaPage to the internal DB
	 * 
	 * @param page
	 * @param conn
	 */
	public void deleteSchemaPage(SchemaPage page, Connection conn) {
		String insertPageSQL = "DELETE FROM schema_page WHERE id=?;";
		try {
			PreparedStatement ps = conn.prepareStatement(insertPageSQL);
			ps.setString(1, page.getId().toString());
			ps.executeUpdate();

		} catch (Exception e) {
			LOG.error("Ignoring SchemaPage Delete Error:", e);
		}

	}

	/**
	 * Makes sure that the schema is in the internal db.
	 * 
	 * @param schema
	 * @param conn
	 */
	public void verifySchema(String schema, Connection conn) {

		String insertSQL = "INSERT OR REPLACE INTO schema VALUES (?);";
		try {
			PreparedStatement ps = conn.prepareStatement(insertSQL);

			ps.setString(1, schema);

			ps.executeUpdate();

		} catch (Exception e) {
			LOG.error("Ignoring Schema Verification Error:", e);
		}
	}

	/**
	 * Read a connection from the database by id
	 * 
	 * @param id
	 * @param conn
	 * @return
	 */
	public ConnectionWrapper readConnectionWrapper(int id, Connection conn) {
		String selectAllSQL = "SELECT * FROM connection WHERE id = ?;";
		ConnectionWrapper cw = new ConnectionWrapper();
		try {
			PreparedStatement ps = conn.prepareStatement(selectAllSQL);

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {// , title, url, driver, username, password)
				cw.setTitle(rs.getString("title"));
				cw.setUrl(rs.getString("url"));
				cw.setDriver(rs.getString("driver"));
				cw.setUsername(rs.getString("username"));
				cw.setPassword(rs.getString("password"));
			}
			rs.close();
		} catch (Exception e) {
			LOG.error("Ignoring Connection Read Error:", e);
		}
		return cw;
	}

	/**
	 * Returns a map of all of the Connections
	 * 
	 * @param conn
	 * @return
	 */
	public Map<String, ConnectionWrapper> readAllConnectionWrappers(
			Connection conn) {
		Map<String, ConnectionWrapper> connections = new HashMap<String, ConnectionWrapper>();
		String selectAllSQL = "SELECT * FROM connection ORDER BY id DESC;";
		try {
			PreparedStatement ps = conn.prepareStatement(selectAllSQL);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {// , title, url, driver, username, password)
				ConnectionWrapper cw = new ConnectionWrapper();
				cw.setId(rs.getInt("id"));
				connections.put(cw.getSchemaId(), cw);
				cw.setTitle(rs.getString("title"));
				cw.setUrl(rs.getString("url"));
				cw.setDriver(rs.getString("driver"));
				cw.setUsername(rs.getString("username"));
				cw.setPassword(rs.getString("password"));
			}
			rs.close();
		} catch (Exception e) {
			LOG.error("Ignoring Read All Connections Error:", e);
		}
		return connections;
	}

	/**
	 * 
	 * @param t
	 * @param schemaName
	 * @param conn
	 */
	public void makeTableSchema(Table t, Connection conn) {

		String selectTSQL = "SELECT * FROM table_schema WHERE name = ? AND schema = ? LIMIT 1;";
		try {
			PreparedStatement ps = conn.prepareStatement(selectTSQL);

			ps.setString(1, t.getName());
			ps.setString(2, t.getSchemaId());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				t.setId(UUID.fromString(rs.getString("id")));
			}
			rs.close();
		} catch (Exception e) {
			LOG.error("Ignoring Make Table Schema SELECT Error:", e);
		}
	}

	/**
	 * makes the tables from the internal database for a schema
	 * 
	 * @param t
	 * @param schemaName
	 * @param conn
	 */
	public Map<String, Table> makeAllTablesForSchema(String SchemaName,
			Connection conn) {

		Map<String, Table> tables = new HashMap<String, Table>();

		String selectTSQL = "SELECT * FROM table_schema WHERE schema = ?;";
		try {
			PreparedStatement ps = conn.prepareStatement(selectTSQL);

			ps.setString(1, SchemaName);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Table t = new Table();
				t.setName(rs.getString("name"));
				t.setId(UUID.fromString(rs.getString("id")));
				t.setSchemaId(SchemaName);
				tables.put(t.getName(), t);
			}
			rs.close();
		} catch (Exception e) {
			LOG.error("Ignoring Make all Tables for Schema Error:", e);
		}
		return tables;
	}

	/**
	 * Checks to see if there are coordinates for the current table in the
	 * internal DB. If not, it returns false.
	 * 
	 * @param page
	 * @param conn
	 * 
	 * @return coordsFound
	 */
	public TableView makeViewWCoordinates(Table t, SchemaPage page, int numTables, Connection conn, boolean makeViewsForAllTables) {
		TableView tv = page.makeViewForTable(t);

		String selectTVSQL = "SELECT * FROM table_page_position WHERE pageid = ? AND tableid = ? LIMIT 1;";
		try {
			PreparedStatement ps = conn.prepareStatement(selectTVSQL);

			ps.setString(1, page.getId().toString());
			ps.setString(2, t.getId().toString());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				tv.setX(rs.getDouble("x_pos"));
				tv.setY(rs.getDouble("y_pos"));
				LOG.debug("Populated Coordinates for: " + tv.getTable().getName() + "{" + tv.getX() + "," + tv.getY() + "}");
				tv.calcLinksAndRadius();
				tv.setSorted();
				LOG.info("Read TableView from db {}", tv);
			} else if (makeViewsForAllTables) {
				tv.randomInitialize(numTables);
				LOG.info("No TableView found for table. Created blank view for this page {}", tv);
			}
			rs.close();
		} catch (Exception e) {
			LOG.error("Ignoring Make TableView with Coordinates Error:", e);
		}
		return tv;
	}

	/**
	 * Returns a collection of Schema Pages from the internal DB.
	 * 
	 * @param schemaName
	 * @param conn
	 * @return
	 */
	public Map<UUID, SchemaPage> readSchemaPages(SortedSchema schema,
			Connection conn) {
		String insertSQL = "SELECT * FROM schema_page sp WHERE schema = ? ORDER BY sp.orderid ASC;";
		Map<UUID, SchemaPage> pages = new HashMap<UUID, SchemaPage>();
		try {
			PreparedStatement ps = conn.prepareStatement(insertSQL);

			ps.setString(1, schema.getId());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				SchemaPage p = new SchemaPage(rs.getString("id"));
				pages.put(p.getId(), p);
				p.setOrderid(rs.getInt("orderid"));
				p.setTitle(rs.getString("title"));
				p.setSchema(schema);
			}
			rs.close();
		} catch (Exception e) {
			LOG.error("Ignoring Read Schema Pages Error:", e);
		}
		return pages;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
