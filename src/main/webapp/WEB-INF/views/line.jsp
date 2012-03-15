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
--%><%@page import="com.dbsvg.objects.view.*" %><%@page session="true" %><%

LinkLine l = (LinkLine)request.getSession().getAttribute("CurrentLine");

// line main end points
double x1 = l.getX2();
double y1 = l.getY2();
double x2 = l.getX1();
double y2 = l.getY1();

//arrow head end points
double xa1 = l.getXa1();
double ya1 = l.getYa1();
double xa2 = l.getXa2();
double ya2 = l.getYa2();
double xa3 = l.getXa3();
double ya3 = l.getYa3();

%>
<g
    class="svgTableLine"
     id="line-<%=l.getStartingTable().getName()+"->"+l.getForeignkey().getReferencedTable() %>">
    <path
       d="M <%=x1 %>,<%=y1 %> L <%=x2 %>,<%=y2 %>"
       class="lineShaft" />
    <path
       d="M <%=xa1 %>,<%=ya1 %> L <%=xa2 %>,<%=ya2 %> L <%=xa3 %>,<%=ya3 %> L <%=xa1 %>,<%=ya1 %> z"
       class="arrowHead" />
  </g>
