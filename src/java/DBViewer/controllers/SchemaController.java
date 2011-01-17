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
package DBViewer.controllers;

import DBViewer.ServiceLocator.ProdServiceLocator;
import DBViewer.ServiceLocator.ServiceLocator;
import java.util.*;
import java.sql.*;
import DBViewer.objects.model.*;
import DBViewer.objects.view.*;
import DBViewer.models.*;
import DBViewer.services.ITablePageSorter;

/**
 * Controller in charge of reading, saving, and sorting a schema.
 * @author horizon
 */
public class SchemaController {

    private static IMainDAO dao;
    private static ITablePageSorter tvSorter;
    private static InternalDataDAO iDAO;
    private static ServiceLocator sLocator;
    // So that the Databases can be given a default page to start out with.
    // This page will be generated when no page is found, but the user can
    // rename it to whatever they want.
    public static String DB_SVG_DEFAULT_NAMESPACE = "DBSVG_DEFAULT";

    /**
     * Contains Logic for preparing a schema's tables.
     * checks to see if it has already been sorted, and things like that.
     *
     * wraps the table sorter controller
     *
     * @param session
     * @param dbi
     */
    public static SchemaPage prepareSchema(SortedSchema schema, String dbi, String pageid) {
        sLocator = ProdServiceLocator.getInstance();
        dao = sLocator.getMainDAO();
        iDAO = sLocator.getIDAO();
        SchemaPage page;

        //get the table sorting object
        tvSorter = sLocator.getTableSorter();
        boolean isNewTables = false;
        if (dbi != null) {
            ConnectionWrapper cw = sLocator.getProgramCache().getConnection(dbi);
            if (cw != null) {
                isNewTables = readTables(schema, cw);
            }
        }

        if ((pageid != null && !pageid.equals("") && !pageid.equals("null")) && schema.getPages().get(UUID.fromString(pageid)) != null) {
            try {
                UUID pid = UUID.fromString(pageid);
                page = schema.getPages().get(pid);
                prepareTableViews(page, isNewTables);
            } catch (Exception e) {
                page = new SchemaPage();
                System.out.println("Error with Page ID");
            }
        } else {
            page = schema.getFirstPage();
            prepareTableViews(page, isNewTables);
        }
        return page;
    }

    /**
     * Reads the table objects from the database based on the Connection Wrapper
     * @param session
     * @param dbi
     */
    private static boolean readTables(SortedSchema schema, ConnectionWrapper cw) {
        boolean newTables = false;
        Connection conn = null;
        if (schema.getNumTables() < 1) {
            try {
                conn = cw.getConnection();
                schema.setTables(dao.getTables(conn, cw.getTitle()));
                schema.setName(cw.getTitle());
                newTables = true;
                readSchemaPages(schema);
            } catch (Exception e) {
                System.out.println("Unable to Connect to Database");
                e.printStackTrace();
                Connection iconn = null;
                try {
                    //TODO: save tables and read from internal DB if no connection found
                    //if the table exists in the internal database already, just read it from there.
                    //iconn= iDAO.getConnection();
                    //schema.setTables(iDAO.makeAllTablesForSchema(cwmap.get(dbi).getTitle(), iconn));
                    Table t = new Table();
                    t.setName("Unable to Connect");
                    t.setSchemaName(cw.getTitle());
                    Column c = new ColumnObject();
                    c.setName(e.toString());
                    t.getColumns().put(c.getName(), c);
                    Map<String, Table> tables = new HashMap();
                    tables.put(t.getName(), t);
                    schema.setTables(tables);
                    newTables = true;
                    readSchemaPages(schema);

                } catch (Exception ie) {
                    System.out.println("Error Reading Internal Database");
                    ie.printStackTrace();
                } finally {
                    try {
                    if (iconn!=null)
                            iconn.close();
                    } catch (Exception fe) {
                        System.out.println("Error Closing Internal Database");
                        fe.printStackTrace();
                    }
                }
            } finally {
                try {
                    if (conn!=null)
                        conn.close();
                } catch (Exception e) {
                    System.out.println("Error Closing Database");
                    e.printStackTrace();
                }
            }
        }
        return newTables;
    }

    /**
     * Checks to see if the Tables need to be sorted, and runs the sort if so.
     * Also generates the lines between tables
     *
     * @param isNewTables
     */
    private static void prepareTableViews(SchemaPage page, boolean isNewTables) {
        List<LinkLine> lines = page.getLines();
        if (!page.areTableViewsClean() || isNewTables) {
            int numDirty = 0;
            for (TableView tv : page.getTableViews()) {
                if (tv.isDirty()) {
                    numDirty++;
                }
            }
            tvSorter.SortPage(page, numDirty == page.getTableViews().size());
            tvSorter.calcLines(page);
        } else {
            List<TableView> tablesToClean = new ArrayList();
            for (LinkLine li : lines) {
                tablesToClean.addAll(li.recalculateLine());
            }
            for (TableView tv : tablesToClean) {
                tv.setClean();
            }
        }
        page.calcDimensions();
    }

    /**
     * Reads in the pages of the Schema and assigns the tables to
     * their pages.
     *
     * @throws java.lang.Exception
     */
    private static void readSchemaPages(SortedSchema schema) throws Exception {
        if (schema.getTables().size() > 0) {
            Connection conn = iDAO.getConnection();
            Map<UUID, SchemaPage> pages = iDAO.readSchemaPages(schema, conn);
            if (pages.size() < 1) {
                SchemaPage sp = new SchemaPage();
                pages.put(sp.getId(), sp);
                sp.setTitle(DB_SVG_DEFAULT_NAMESPACE);
                sp.setOrderid(0);
                sp.setSchema(schema);
            }
            for (SchemaPage page : pages.values()) {
                int i = 0;
                for (Table t : schema.getTables().values()) {
                    iDAO.makeTableSchema(t, conn);
                    TableView tv = iDAO.makeViewWCoordinates(t, page, schema.getTables().size(), conn);
                    tv.setId(i);
                    page.getTableViews().add(tv);
                    i++;
                }
            }
            schema.setPages(pages);
            conn.close();
        }
    }

    /**
     * Saves the positions in the already populated table views
     * @throws java.lang.Exception
     */
    public static void saveTablePositions(SchemaPage page) throws Exception {
        sLocator = ProdServiceLocator.getInstance();
        iDAO = sLocator.getIDAO();
        Connection conn = iDAO.getConnection();
        iDAO.verifySchema(page.getSchema().getName(), conn);
        iDAO.saveSchemaPage(page, conn);
        for (TableView tv : page.getTableViews()) {
            iDAO.saveTable(tv.getTable(), conn);
            iDAO.saveTablePosition(tv, conn);
        }
        conn.close();
    }

    /**
     * public access method
     * @param resort
     */
    public static void resortTableViews(SchemaPage currentPage) {
        sLocator = ProdServiceLocator.getInstance();
        tvSorter = sLocator.getTableSorter();
        tvSorter.SortPage(currentPage, true);
        tvSorter.calcLines(currentPage);
        currentPage.calcDimensions();
    }

    public static void saveTableViews(SchemaPage currentPage) {
        sLocator = ProdServiceLocator.getInstance();
        iDAO = sLocator.getIDAO();
        try {
            Connection conn = iDAO.getConnection();
            iDAO.saveSchemaPage(currentPage, conn);
            for (TableView t : currentPage.getTableViews()) {
                iDAO.saveTablePosition(t, conn);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
