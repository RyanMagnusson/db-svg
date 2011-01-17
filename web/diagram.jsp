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
--%><%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@page import="DBViewer.models.*" %>
<%@page import="java.util.*" %>
<%@page import="java.sql.*" %>
<%@page import="DBViewer.objects.model.*" %>
<%@page import="DBViewer.objects.view.*" %>
<%@page import="DBViewer.controllers.*" %>
<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DB-SVG | Data Model Explorer</title>
        <link rel="stylesheet" type="text/css" media="screen" href="css/style.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/jquery.svg.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/jq-ui.css" />
        <link rel="shortcut icon" href="favicon.ico" type="image/x-icon" /> 
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" src="js/jquery.svg.js"></script>
        <script type="text/javascript" src="js/jquery.svganim.js"></script>
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
    
    String schemaUrlData = "?dbi="+dbi;
    if (pageid !=null){
        schemaUrlData = "?dbi="+dbi+"&pageid="+pageid;
    }
    
    SortedSchema currentSchema = (SortedSchema)request.getSession().getAttribute("CurrentSchema");
    SchemaPage sPage = (SchemaPage)request.getSession().getAttribute("CurrentPage");
    
%>

  var transx = (-1*<%= sPage.getTransx() %>)-30;
  var transy = (-1*<%= sPage.getTransy() %>)-100;

$(function() {

svg = $('#svgwindow').svg({loadURL: 'schema.svg.jsp<%=schemaUrlData%>'});

      svg.mousemove(function (e){
           if (onTable) {
              var ex = e.clientX+f_scrollLeft();
              var ey = e.clientY+f_scrollTop();

              currentDragx = (ex+transx);
              currentDragy = (ey+transy);
              $('#coord').html('x:'+(currentDragx)+' y:'+(currentDragy)+' '+f_scrollLeft()+','+f_scrollTop()+' '+window.pageYOffset+'.'+document.documentElement.scrollTop+'.'+document.body.scrollTop);
              currentTable.setAttribute('transform','translate('+(currentDragx)+','+(currentDragy)+')');
          } 
      });
      $('#saver').click(function(){
           $.post("Diagram", { "m": "save" },
           function(data){
             alert(data);
           });
           return false;
      });

      $('#sorter').click(function(){
           $.post("Diagram", { "m": "refresh" },
           function(data){
            // alert("Data Loaded: " + data);
            location.reload(true);
           });
           return false;
      });
});

    function tableDown(evt) {
        currentTable = evt.parentNode;
        onTable = true;
    }
    
    function tableUp(evt) {
        onTable = false;
        $.post("Diagram", { "m": "setTablePosition", "name": evt.id, "x": currentDragx, "y": currentDragy },
           function(data){
            // alert("Data Loaded: " + data);
            location.reload(true);
           });
    }

    function tableMove(evt) {
    }

    function isWebKit(){
        return RegExp(" AppleWebKit/").test(navigator.userAgent);
    }
    function f_scrollLeft() {
          return f_filterResults (
                window.pageXOffset ? window.pageXOffset : 0,
                document.documentElement ? document.documentElement.scrollLeft : 0,
                document.body ? document.body.scrollLeft : 0
          );
    }
    function f_scrollTop() {
          return f_filterResults (
                window.pageYOffset ? window.pageYOffset : 0,
                document.documentElement ? document.documentElement.scrollTop : 0,
                document.body ? document.body.scrollTop : 0
          );
    }
    function f_filterResults(n_win, n_docel, n_body) {
          var n_result = n_win ? n_win : 0;
          if (n_docel && (!n_result || (n_result > n_docel)))
                n_result = n_docel;
          return n_body && (!n_result || (n_result > n_body)) ? n_body : n_result;
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
<a href="#"><div class="tab"><%= p.getTitle() %></div></a><% } %>
        </div><% } %>
        <div class="menu">
            <a href="Menu">Back to Menu</a><a href="Setup?dbi=<%= dbi %>">Setup</a><a id="sorter" href="#Diagram?m=refresh">Re-sort</a><a id="saver" href="#Diagram?m=save">Save</a><a href="schema.svg.jsp<%=schemaUrlData%>" target="_blank">Download</a><span class="coord" id="coord" style="display:none">x,y</span>
        </div>
        <div class="svgwrapper" style="width:<%= sPage.getWidth() %>px;height:<%= sPage.getHeight() %>px;">
            <div id="svgwindow" class="svgwindow"></div>
        </div>
    </div>
    </body>
</html>
