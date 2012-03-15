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
 
    Document   : setupPages
    Created on : May 16, 2009, 11:08:04 PM
    Author     : horizon
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%><%@page session="true" %>
<%@page import="com.dbsvg.models.*" %>
<%@page import="java.util.*" %>
<%@page import="java.sql.*" %>
<%@page import="com.dbsvg.objects.model.*" %>
<%@page import="com.dbsvg.objects.view.*" %>
<%@page import="com.dbsvg.controllers.*" %><%

    SortedSchema currentSchema = (SortedSchema)request.getSession().getAttribute("CurrentSchema");
    
    List<SchemaPage> pages = new ArrayList<SchemaPage>();
    pages.addAll(currentSchema.getPages().values());

    List<Table> tables = new ArrayList<Table>();
    tables.addAll(currentSchema.getTables().values());
    Collections.sort(tables);

%>
<h3 class="ui-widget-header ui-corner-all">Pages Setup</h3>
<div class="pagesButtons">
    <button type="button" name="AddPage" id="addPage">Add Page</button>
    <button type="button" name="Save" id="save">Save Changes</button>
</div>
<table class="pagesTable">
    <thead>
        <tr>
            <th></th><% for (SchemaPage p : pages) { %>
            <th><%= p.getTitle() %></th><% } %>
        </tr>
    </thead>
    <tbody><% for (Table t : tables) { %>
    <tr>
        <td><%= t.getName() %></td><% for (SchemaPage p : pages) { %>
        <td class="pagebox"><input type="checkbox" <%= (p.contains(t)? "CHECKED":"")%> ></input></td>
    <% } } %>
    </tr>
    </tbody>
</table>