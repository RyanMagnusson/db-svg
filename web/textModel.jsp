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

    Document   : textModel.jsp
    Created on : Mar 28, 2009, 4:37:08 PM
    Author     : horizon

DEPRECATED - Now on setup page through setupinfo.
Shows a text representation of the database.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="DBViewer.models.*" %>
<%@page import="java.util.*" %>
<%@page import="java.sql.*" %>
<%@page import="DBViewer.objects.model.*" %>
<%@page import="DBViewer.objects.view.*" %>
<%@page import="DBViewer.controllers.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DB-SVG | Text Version</title>
        <link rel="stylesheet" type="text/css" media="screen" href="css/style.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/jq-ui.css" />
        <script type="text/javascript" src="js/jquery.js"></script>
    </head>
    <body>
    <div class="titleBox">
        <div class="titleHeader">
            <div class="titleLeft"></div>
            <div class="titleRight"></div>
            <div class="titleBody">
                <h1>DB-SVG Text Version</h1>
            </div>
        </div>
        <div class="menu">
        <a href="Menu">Back to Menu</a><a href="setup.jsp?dbi=<%= request.getParameter("dbi") %>">Setup</a><a href="model.jsp?dbi=<%= request.getParameter("dbi") %>">SVG Diagram</a>
        </div>
    </div>
    <div id="content" class="contentBox">
        <%

    String dbi = request.getParameter("dbi");
    String pageid = request.getParameter("page");
    if (pageid==null) {
        pageid = (String)request.getSession().getAttribute("pageid");
    }

    SortedSchema currentSchema = new SortedSchema();
    if (request.getSession().getAttribute("CurrentSchema")==null || request.getSession().getAttribute("CurrentSchema").getClass()!=currentSchema.getClass()) {
        // if no schema loaded, save the new one to the session
        request.getSession().setAttribute("CurrentSchema",currentSchema);
    } else {
        // if a schema is found, load it from the session.
        currentSchema = (SortedSchema)request.getSession().getAttribute("CurrentSchema");
    }
    SchemaController.prepareSchema(currentSchema, dbi, pageid);

          for (Table t : currentSchema.getTables().values()) {
             out.println("<h3 class=\"ui-widget-header ui-corner-all\">"+t.getName()+"</h3><ul>");
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

        %>
    </div>
    </body>
</html>
