/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DBViewer.models;

import java.sql.*;
import java.util.*;
import DBViewer.objects.view.*;
import DBViewer.objects.model.*;
import java.io.Serializable;


/**
 *
 * @author derrick.bowen
 */
public interface InternalDataDAO extends Serializable {
    public Connection getConnection() throws Exception;
    public void setUpInternalDB(Connection conn);
    public void saveConnectionWrapper(ConnectionWrapper c, Connection conn);
    public void deleteConnectionWrapper(String id, Connection conn);
    public void saveConnectionWrapperNewID(ConnectionWrapper c, Connection conn);
    public void saveTable(Table t, Connection conn);
    public void saveSchemaPage(SchemaPage page, Connection conn);
    public void saveTablePosition(TableView tv, Connection conn);
    public void verifySchema(String schema, Connection conn);
    public ConnectionWrapper readConnectionWrapper(int id, Connection conn);
    public Map<String, ConnectionWrapper> readAllConnectionWrappers(Connection conn);
    public void makeTableSchema(Table t, Connection conn);
    public Map<String, Table> makeAllTablesForSchema(String SchemaName, Connection conn);
    public TableView makeViewWCoordinates(Table t, SchemaPage page, int numTables, Connection conn);
    public Map<UUID, SchemaPage> readSchemaPages(SortedSchema schema, Connection conn);
    
    
}
