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
--%><%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%><%@page session="true" %>

<%@page import="com.dbsvg.models.*" %>
<%@page import="java.util.*" %>
<%@page import="java.sql.*" %>
<%@page import="com.dbsvg.objects.model.*" %>
<%@page import="com.dbsvg.objects.view.*" %>
<%@page import="com.dbsvg.controllers.*" %>
<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DB-SVG | Data Model Explorer</title>
        <link rel="stylesheet" type="text/css" media="screen" href="css/style.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/jquery.svg.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/jq-ui.css" />
        <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon" /> 
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui.min.js"></script>
        <script type="text/javascript" src="js/jquery.svg.js"></script>
        <script type="text/javascript" src="js/jquery.svganim.js"></script>
        <script type="text/javascript" src="js/common.js"></script>
        <script type="text/javascript" src="js/diagram.js"></script>
<script type="text/javascript">
var svg;
var onTable = false;
var currentTable;
var currentDragx;
var currentDragy;

<%
// retreive needed variables from request & session

    String dbi = request.getParameter("dbi");
    String pageid = request.getParameter("page");
    if (pageid==null) {
        pageid = (String)request.getSession().getAttribute("pageid");
    }
        
    SortedSchema currentSchema = (SortedSchema)request.getSession().getAttribute("CurrentSchema");
    SchemaPage sPage = (SchemaPage)request.getSession().getAttribute("CurrentPage");
    
    if (sPage != null){
    	pageid = sPage.getId().toString();
    }
    
    String schemaUrlData = "?dbi="+dbi;
    if (pageid !=null){
        schemaUrlData = "?dbi="+dbi+"&page="+pageid;
    }
%>
  var dbi = <%=dbi%>;
  var pageid = '<%=pageid%>';
  var transx = -10-30;
  var transy = -10-100;

$(function() {

svg = $('#svgwindow').svg({loadURL: 'diagram/download<%=schemaUrlData%>'});

	svg.mousemove(function(e) {
			if (onTable) {
				var ex = e.clientX + f_scrollLeft();
				var ey = e.clientY + f_scrollTop();

				currentDragx = (ex + transx);
				currentDragy = (ey + transy);
				$('#coord').html(
						'x:' + (currentDragx) + ' y:' + (currentDragy) + ' '
								+ f_scrollLeft() + ',' + f_scrollTop() + ' '
								+ window.pageYOffset + '.'
								+ document.documentElement.scrollTop + '.'
								+ document.body.scrollTop);
				currentTable.setAttribute('transform', 'translate('
						+ (currentDragx) + ',' + (currentDragy) + ')');
			}
		});
		$('#saver').click(function() {
			showLoading();
			$.post("diagram/save", {
				"dbi" : dbi,
				"page" : pageid
			}, function(data) {
				hideLoading();
				showSave(data.message);
			}, "json");
			return false;
		});

		$('#sorter').click(function() {
			showLoading();
			$.post("diagram/refresh", {
				"dbi" : dbi,
				"page" : pageid
			}, function(data) {
				location.reload(true);
			}, "json");
			return false;
		});
	});

	
	function tableDown(evt) {
		currentTable = evt.parentNode;
		onTable = true;
	}

	function tableUp(evt) {
		onTable = false;
		$.post("diagram/setTablePosition", {
			"tableid" : evt.id,
			"x" : currentDragx,
			"y" : currentDragy
		}, function(data) {
			// alert("Data Loaded: " + data);
			location.reload(true);
		}, "json");
	}

	function tableMove(evt) {
	}
</script>
    </head>
    <body>
    <div class="titleHeader" >
            <div class="titleLeft"></div>
            <div class="titleRight"></div>
            <div class="titleBody">
            <h1>DB-SVG Data Model Explorer</h1>
        </div>
    </div>
    <div id="content" class="modelContentBox"><% if (currentSchema.getPages().size()>1) { %>
        <div class="tablayer"><% for (SchemaPage p :currentSchema.getPages().values()) { %>
<a href="diagram?dbi=<%= dbi %>&page=<%= p.getId() %>"><span class="tab<%=(p.getId().toString().equals(pageid) ? " tab-active" : "") %>"><%= p.getTitle() %></span></a><% } %>
        </div><% } %>
        <div class="menu">
            <a href="menu">Back to Menu</a><a href="setup?dbi=<%= dbi %>">Setup</a><a id="sorter" href="#diagram/refresh">Re-sort</a><a id="saver" href="#diagram/save">Save</a><a href="diagram/download<%=schemaUrlData%>" target="_blank">Download</a><span class="coord" id="coord" style="display:none">x,y</span>
        </div>
        <div class="svgwrapper" style="width:<%= sPage.getWidth() %>px;height:<%= sPage.getHeight() %>px;">
            <div id="svgwindow" class="svgwindow"></div>
        </div>
    <jsp:include page="loading.jsp"></jsp:include>
		<div id="saveDialog" class="saveDialog ui-dialog" title="Save Completed">
		    <p>The Save action has completed with the following message:</p>
		    <div id="saveText"></div>
		</div>
    </div>
    </body>
</html>
