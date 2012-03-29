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
--%><%@page contentType="image/svg+xml" pageEncoding="UTF-8"%><%@page session="true" %>
<%@page import="com.dbsvg.objects.model.*" %>
<%@page import="com.dbsvg.objects.view.*" %>
<%@page import="com.dbsvg.controllers.*" %>
<%@page import="com.dbsvg.services.*" %>
<%@page import="java.util.*" %>
<%
String dbi = request.getParameter("dbi");

if (dbi != null) {
    SchemaPage sPage = (SchemaPage)request.getSession().getAttribute("CurrentPage");
    Collection<TableView> tableViews = sPage.getTableViews();
%>
<svg
   xmlns:dc="http://purl.org/dc/elements/1.1/"
   xmlns:cc="http://creativecommons.org/ns#"
   xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
   xmlns:svg="http://www.w3.org/2000/svg"
   xmlns="http://www.w3.org/2000/svg"
   xmlns:sodipodi="http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd"
   xmlns:inkscape="http://www.inkscape.org/namespaces/inkscape"
   xmlns:xlink="http://www.w3.org/1999/xlink"
   width="<%= sPage.getWidth() %>"
   height="<%= sPage.getHeight() %>"
   id="canvas"
   sodipodi:version="0.32"
   inkscape:version="0.46"
   version="1.0"
   sodipodi:docname="template.svg"
   onload="init();" >
<style type="text/css" >
<![CDATA[
<jsp:include page="../../css/diagramstyle.css" ></jsp:include>
]]>
</style>
<svg:g id="<%= sPage.getId() %>">
<%
	List<LinkLine> lines = sPage.getLines();
    for (LinkLine l : lines) {
        request.getSession().setAttribute("CurrentLine", l);

        %>
        <jsp:include page="line.jsp" ></jsp:include>
        <%
    }

    for (TableView t : tableViews) {
        request.getSession().setAttribute("CurrentTableView", t);
        
        %>
       <jsp:include page="table.jsp" >
           <jsp:param name="transx" value="<%= t.getX() %>" />
           <jsp:param name="transy" value="<%= t.getY() %>" />
       </jsp:include>
 <%
     }
} else { %>
          <g class="svgTable">
            <rect width="350" height="50" x="20" y="90" fill="#9a564c" class="tableBody"></rect>
            <text x="40" y="120" fill="white" font-weight="bold"> 
              No Schema Loaded</text>
          </g>
<% } %>
       </svg:g>
   </svg>

