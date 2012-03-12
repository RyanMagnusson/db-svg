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

/**
 * 
 * @author horizon
 */
@SuppressWarnings("serial")
public class ConnectionWrapper implements Comparable<ConnectionWrapper>, Serializable {

	private int id;
	private String title = "";
	private String url = "";
	private String driver = "";
	private String username = "";
	private String password = "";

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Connection getConnection() throws Exception {
		Class.forName(driver).newInstance();
		Connection conn = DriverManager.getConnection(url, username, password);
		return conn;
	}

	/**
	 * Sort ConnectionWrappers by their Title
	 * 
	 * @param o
	 * @return
	 */
	public int compareTo(ConnectionWrapper o) {
		if (this == null && o == null)
			return 0;
		if (this == null)
			return 1;
		if (o == null)
			return -1;
		if (this.getTitle() == null && o.getTitle() == null)
			return 0;
		if (this.getTitle() == null)
			return 1;
		if (o.getTitle() == null)
			return -1;
		return this.getTitle().compareToIgnoreCase(o.getTitle());
	}
}
