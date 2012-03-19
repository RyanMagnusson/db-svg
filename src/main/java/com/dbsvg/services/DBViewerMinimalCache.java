/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 * 
 * @author derrick.bowen
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
