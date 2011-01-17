/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DBViewer.ServiceLocator;

import DBViewer.models.*;
import DBViewer.models.SQLiteInternalDataDAO;
import DBViewer.services.DBViewerMinimalCache;
import DBViewer.services.IDBViewerCache;
import DBViewer.services.ITablePageSorter;
import DBViewer.services.TableViewSpringSorter;
import java.util.Set;

/**
 *
 * @author derrick.bowen
 */
public class TestServiceLocator extends ServiceLocator {

    private static TestServiceLocator instance = null;
    private String iDAOpath = null;
    
    private TestServiceLocator(){
        
        
    }
    
    public static TestServiceLocator getInstance() {
        if (instance == null) {
            instance = new TestServiceLocator();
        }
        return instance;
    }
    
        
    public static TestServiceLocator getInstance(String iDAOpath) {
        if (instance == null) {
            instance = new TestServiceLocator();
        }
        instance.setIDAOPath(iDAOpath);
        return instance;
    }
    
    @Override
    public IDBViewerCache getProgramCache() {
        if (dbvCache == null) {
            dbvCache = new DBViewerMinimalCache();
        }
        return dbvCache;
    }

    @Override
    public ITablePageSorter getTableSorter() {
        if (tablePageSorter == null) {
            tablePageSorter = new TableViewSpringSorter(getIDAO());
        }
        return tablePageSorter;
    }

    @Override
    public InternalDataDAO getIDAO() {
        if (iDAOpath == null) {
            throw new UnsupportedOperationException("Path to IDAO not set.");
        }
        if (iDAO == null) {
            iDAO = new SQLiteInternalDataDAO(iDAOpath);            
        }
        return iDAO;
    }

    public void setIDAOPath(String iDAOPath) {        
        this.iDAOpath = iDAOPath;
    }

    @Override
    public IMainDAO getMainDAO() {
        if (mainDAO == null) {
            mainDAO = new JdbcMainDAO();
        }
        return mainDAO;
    }
}
