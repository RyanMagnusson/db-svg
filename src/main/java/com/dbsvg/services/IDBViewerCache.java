/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dbsvg.services;

import java.util.*;

import com.dbsvg.models.*;
import com.dbsvg.objects.view.*;

/**
 * Interface for the project caching mechanism.
 * @author derrick.bowen
 */
public interface IDBViewerCache {
    public ConnectionWrapper getConnection(String dbi);
    public void removeConnection(String dbi);
    public void putConnection(String dbi, ConnectionWrapper cw);
    public Map<String, ConnectionWrapper> getAllConnections();
    public void setAllConnections(Map<String, ConnectionWrapper> cwmap);
    public SchemaPage getSchemaPage(UUID pageId);
    public void putSchemaPage (SchemaPage sPage);

}
