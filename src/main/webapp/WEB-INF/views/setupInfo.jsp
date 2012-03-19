<%--
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
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%><%@page session="true" %>
<%@page import="com.dbsvg.models.*" %>
<%@page import="java.util.*" %>
<%@page import="java.sql.*" %>
<%@page import="com.dbsvg.objects.model.*" %>
<%@page import="com.dbsvg.objects.view.*" %>
<%@page import="com.dbsvg.controllers.*" %><%

    SortedSchema currentSchema = (SortedSchema)request.getSession().getAttribute("CurrentSchema");
    ConnectionWrapper cw = (ConnectionWrapper)request.getSession().getAttribute("CurrentConn");

%>
<h3 class="ui-widget-header ui-corner-all">Schema Information: <%= cw.getTitle() %></h3>
<table class="info">
    <tr>
        <td class="label">Pages: </td><td class="data"><%= currentSchema.getPages().size() %></td>
    </tr>
    <tr>
        <td class="label">Tables: </td><td class="data"><%= currentSchema.getTables().size() %></td>
    </tr>
    <tr>
        <td class="label">URL: </td><td class="data"><%= cw.getUrl() %></td>
    </tr>
    <tr>
        <td class="label">Driver: </td><td class="data"><%= cw.getDriver() %></td>
    </tr>
    <tr>
        <td class="label">Username: </td><td class="data"><%= cw.getUsername() %></td>
    </tr>
</table>
    <p>
        <a href="#" id="editConn" class="frontmenuedit" onclick="showEditConn();">Edit This Connection</a>
    </p>

    <br />
    <h3 class="ui-widget-header ui-corner-all">Text Version </h3>
<%

List<Table> tables = new ArrayList<Table>();
tables.addAll(currentSchema.getTables().values());
Collections.sort(tables);
      for (Table t : tables) {
             out.println("<h4 >"+t.getName()+"</h4><ul>");
             Map<String,Column> cols = t.getColumns();
             for (Column c : cols.values()) {
                String fk = "";
                if (t.getForeignKeys().containsKey(c.getName())){
                   ForeignKey key = t.getForeignKeys().get(c.getName());
                   fk = " FK->"+key.getReferencedTable()+"."+key.getReferencedColumn();
                }
                out.println("<li>"+c.getName()+(t.getPrimaryKeys().containsKey(c.getName())?" PK":"")+fk+"</li>");
             }
             for (Table rt : t.getReferencingTables().values()) {

             out.println("&nbsp;&nbsp;&nbsp;&nbsp;    referenced by: "+rt.getName()+"<br />");
             }
             out.println("</ul>");
          }
    %><br>