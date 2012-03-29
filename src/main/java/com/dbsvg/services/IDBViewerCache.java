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

import java.util.Map;
import java.util.UUID;

import com.dbsvg.models.ConnectionWrapper;
import com.dbsvg.objects.view.SchemaPage;

/**
 * Interface for the project caching mechanism.
 * 
 */
public interface IDBViewerCache {
	public ConnectionWrapper getConnection(String dbi);

	public void removeConnection(String dbi);

	public void putConnection(String dbi, ConnectionWrapper cw);

	public Map<String, ConnectionWrapper> getAllConnections();

	public void setAllConnections(Map<String, ConnectionWrapper> cwmap);

	public SchemaPage getSchemaPage(UUID pageId);

	public void putSchemaPage(SchemaPage sPage);

	public void setInitialized();

	public boolean isInitialized();

}
