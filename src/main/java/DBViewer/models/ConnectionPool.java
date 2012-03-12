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
package DBViewer.models;

import java.sql.*;
import javax.sql.ConnectionEventListener;
import javax.sql.StatementEventListener;

/**
 *
 * @author Derrick Bowen <derrickbowen@gmail.com>
 * 
 * A basic class for getting a connection.  Intended to be expaned to a full pool
 * when the need arises.
 */
public class ConnectionPool {

    private static String url = "jdbc:mysql://localhost:3306/movelib";
    private static String driver = "com.mysql.jdbc.Driver";
    private static String username = "test";
    private static String password = "test";
    
    public ConnectionPool(){
    }
    
    public static Connection getConnection() {
       try {
        Class.forName(driver).newInstance();
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
       } catch (Exception e){
          e.printStackTrace();
       }
       return null;
    }

   public void close() throws SQLException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public void addConnectionEventListener(ConnectionEventListener arg0) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public void removeConnectionEventListener(ConnectionEventListener arg0) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public void addStatementEventListener(StatementEventListener arg0) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public void removeStatementEventListener(StatementEventListener arg0) {
      throw new UnsupportedOperationException("Not supported yet.");
   }
    
}
