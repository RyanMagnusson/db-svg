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
package DBViewer.testing;

import DBViewer.ServiceLocator.ServiceLocator;
import DBViewer.ServiceLocator.TestServiceLocator;
import DBViewer.models.*;
import DBViewer.objects.model.*;
import DBViewer.objects.view.*;
import DBViewer.controllers.*;
import DBViewer.services.ITablePageSorter;
import java.util.*;
import java.sql.*;
import org.w3c.dom.*;

/**
 *
 * @author Derrick Bowen <derrickbowen@gmail.com>
 */
public class Tester {

    private static String url = "jdbc:sqlite:/DB-SVG/DB-SVG.db";
    private static String driver = "org.sqlite.JDBC";
    private static String username = "";
    private static String password = "";

    public static void main(String[] args) {

        ServiceLocator sLocator = TestServiceLocator.getInstance("/DB-SVG/DB-SVG.db");

        System.out.println("");
        System.out.println("===== Testing DB-Object Mapping =====");
        IMainDAO dao = sLocator.getMainDAO();
        Map<String, Table> tables = new HashMap();
        try {
            Connection conn = Tester.getConnection();
            tables = dao.getTables(conn, "test");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("");
        System.out.println("=============== Tables ==============");
        for (Table t : tables.values()) {
            System.out.println("Table: " + t.getName() + " Schema:" + t.getSchemaName());
            Map<String, Column> cols = t.getColumns();
            for (Column c : cols.values()) {
                String fk = "";
                if (t.getForeignKeys().containsKey(c.getName())) {
                    ForeignKey key = t.getForeignKeys().get(c.getName());
                    fk = " FK->" + key.getReferencedTable() + "." + key.getReferencedColumn();
                }
                System.out.println("  " + c.getName() + (t.getPrimaryKeys().containsKey(c.getName()) ? " PK" : "") + fk);
            }
            for (Table rt : t.getReferencingTables().values()) {

                System.out.println("    referenced by: " + rt.getName());
            }
        }
        System.out.println("");

        InternalDataDAO iDAO = sLocator.getIDAO();
        ITablePageSorter tvSorter = sLocator.getTableSorter();
//      List<TableView> tableViews = tvSorter.SortAction(tables,null,false);
//      List<LinkLine> lines = tvSorter.calcLines(tableViews);
//      for (LinkLine l : lines) {
//          System.out.println(l.getStartingTable().getName()+"->"+l.getForeignkey().getReferencedTable() + ":   "+l.getX1() + ","+l.getY1() + " -> "+l.getX2() + ","+l.getY2() +"  a:"+l.getAngle());
//      }

        /*
        System.out.println("Saving DB XML");
        List<List<String>> DBparameters = new ArrayList<List<String>>();
        String[] ParameterColumns = {"title","url","driver","username","password"};
        
        List<String> col1 = new ArrayList();
        col1.add("movelib");
        col1.add("jdbc:mysql://localhost:3306/movelib");
        col1.add("com.mysql.jdbc.Driver");
        col1.add("test");
        col1.add("zarahemla");
        
        DBparameters.add(col1);
        
        String path="/home/horizon/NetBeansProjects/DB-SVG/web/dbs.xml";
        
        XMLReadWriter xwriter = new XMLReadWriter();
        try {
        xwriter.writeOut(path, DBparameters, ParameterColumns);
        } catch (Exception ex) {
        ex.printStackTrace();
        }
         */
    }

    public static Connection getConnection() {
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url, username, password);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
