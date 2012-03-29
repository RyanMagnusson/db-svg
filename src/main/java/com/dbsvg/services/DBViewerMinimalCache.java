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
package com.dbsvg.services;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.dbsvg.models.ConnectionWrapper;
import com.dbsvg.objects.view.SchemaPage;

/**
 * A simple memory cache for now. Thought about using the request session, but
 * just doing it in memory was simpler. Used interface so it can easily be
 * exchanged for a distributed cache in the future.
 * 
 * This version only keeps the last viewed objects in memory
 */
public class DBViewerMinimalCache implements IDBViewerCache {

	private Map<String, ConnectionWrapper> dbc;
	private Map<UUID, SchemaPage> schPages;
	private boolean initialized = false;

	public DBViewerMinimalCache() {
		this.dbc = new HashMap<String, ConnectionWrapper>();
		this.schPages = new HashMap<UUID, SchemaPage>();
	}

	@Override
	public ConnectionWrapper getConnection(String dbi) {
		if (dbc.containsKey(dbi)) {
			return dbc.get(dbi);
		} else {
			return null;
		}
	}

	@Override
	public void removeConnection(String dbi) {
		ConnectionWrapper cw = this.getConnection(dbi);
		if (cw != null)
			dbc.remove(cw);
	}

	@Override
	public void setAllConnections(Map<String, ConnectionWrapper> cwmap) {
		dbc = cwmap;
	}

	@Override
	public void putConnection(String dbi, ConnectionWrapper cw) {
		dbc.put(dbi, cw);
	}

	@Override
	public Map<String, ConnectionWrapper> getAllConnections() {
		return dbc;
	}

	@Override
	public SchemaPage getSchemaPage(UUID pageId) {
		return schPages.get(pageId);
	}

	@Override
	public void putSchemaPage(SchemaPage sPage) {
		schPages.put(sPage.getId(), sPage);
	}

	@Override
	public void setInitialized() {
		initialized = true;
	}

	@Override
	public boolean isInitialized() {
		return initialized;
	}

}
