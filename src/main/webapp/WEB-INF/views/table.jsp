<%--
 * DB-SVG Copyright 2012 Derrick Bowen
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
 *   @author Derrick Bowen derrickbowen@dbsvg.com
--%><%@page session="true" %>
<%@page import="com.dbsvg.objects.view.*" %>
<%@page import="com.dbsvg.objects.model.*" %>
<%@page import="java.util.*" %>
<%
TableView t = (TableView)request.getSession().getAttribute("CurrentTableView");

int height = (int)(t.getTable().getHeight());
int width = (int)(t.getTable().getWidth());
%>
  <g
     id="Table-<%= t.getId() %>"
     class="svgTable"
     transform="translate(<%= request.getParameter("transx") %>,<%= request.getParameter("transy") %>)">
 <a id="<%= t.getId() %>" class="tableanchor" href="#" onmousedown="tableDown(this);" onmouseup="tableUp(this);" onmousemove="tableMove(this);">
    <rect
       class="tableBody"
       id="tableBody"
       width="<%= width %>"
       height="<%= height %>"
       x="0"
       y="0"
       rx="11.711954"
       ry="7.3775272" />
    <rect
       class="tableName"
       id="tableName"
       width="<%= width %>"
       height="30"
       x="0"
       y="0"
       rx="11.711954"
       ry="7.3775272" />
    <text
       xml:space="preserve"
       x="10"
       y="3"
       class="tableHeadText"
       id="tableHeadText"><tspan
         sodipodi:role="line"
         id="tspan3163"
         x="10"
         y="18"><%= t.getTable().getName() %></tspan></text>
    <text
       xml:space="preserve"
       x="10"
       y="20"
       class="tableBodyText"
       id="tableBodyText">
           <%
           Map<String,Column> cols = t.getTable().getColumns();

           double y = 48.0;
             for (Column c : cols.values()) {
                String fk = "";
                if (t.getTable().getForeignKeys().containsKey(c.getName())){
                   ForeignKey key = t.getTable().getForeignKeys().get(c.getName());
                   fk = " FK->"+key.getReferencedTable()+"."+key.getReferencedColumn();
                }
                String colname = c.getName()+(t.getTable().getPrimaryKeys().containsKey(c.getName())?" PK":"")+fk;
                %>
         <tspan
         sodipodi:role="line"
         id="tspan3167"
         x="10"
         y="<%= y %>"><%= colname %></tspan>
          <% y+=15;
             } %></text>
     </a>
  </g>
