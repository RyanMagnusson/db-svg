/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dbsvg.servicelocators;

import com.dbsvg.models.*;
import com.dbsvg.services.IDBViewerCache;
import com.dbsvg.services.ITablePageSorter;

/**
 * A basic implementation meant to be overridden with mocks in tests
 * 
 * @author derrick.bowen
 */
public class TestServiceLocator extends ServiceLocator {

    public TestServiceLocator(){
        throw new RuntimeException("Override in Test Case.");
    }
    
    @Override
    public IDBViewerCache getProgramCache() {
        throw new RuntimeException("Override in Test Case.");
    }

    @Override
    public ITablePageSorter getTableSorter() {
        throw new RuntimeException("Override in Test Case.");
    }

    @Override
    public InternalDataDAO getIDAO() {
        throw new RuntimeException("Override in Test Case.");
    }

    @Override
    public IMainDAO getMainDAO() {
        throw new RuntimeException("Override in Test Case.");
    }
}
