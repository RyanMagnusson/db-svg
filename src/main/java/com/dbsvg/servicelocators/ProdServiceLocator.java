/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dbsvg.servicelocators;

import com.dbsvg.models.*;
import com.dbsvg.services.DBViewerMinimalCache;
import com.dbsvg.services.IDBViewerCache;
import com.dbsvg.services.ITablePageSorter;
import com.dbsvg.services.TableViewSpringSorter;

/**
 *
 * @author derrick.bowen
 */
public class ProdServiceLocator extends ServiceLocator {

    private static ProdServiceLocator instance = null;
    private String iDAOpath = null;

    /////////// Singleton ///////////////
    private ProdServiceLocator() {
    }

    public static ProdServiceLocator getInstance() {
        if (instance == null) {
            instance = new ProdServiceLocator();
        }
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
        iDAO = new SQLiteInternalDataDAO(iDAOpath);
    }
    
    public Boolean isDAOPathSet() {
        return iDAOpath == null;
    }

    @Override
    public IMainDAO getMainDAO() {
        if (mainDAO == null) {
            mainDAO = new JdbcMainDAO();
        }
        return mainDAO;
    }
}
