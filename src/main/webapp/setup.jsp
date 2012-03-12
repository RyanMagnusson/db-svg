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

    Document   : Setup.jsp
    Created on : Mar 28, 2009, 4:37:08 PM
    Author     : horizon
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="DBViewer.models.*" %>
<%@page import="java.util.*" %>
<%@page import="java.sql.*" %>
<%@page import="DBViewer.objects.model.*" %>
<%@page import="DBViewer.objects.view.*" %><%

    SortedSchema currentSchema = (SortedSchema)request.getSession().getAttribute("CurrentSchema");
    ConnectionWrapper cw = (ConnectionWrapper)request.getSession().getAttribute("CurrentConn");

%><!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DB-SVG | Schema Setup</title>
        <link rel="stylesheet" type="text/css" media="screen" href="css/style.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/jq-ui.css" />
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" src="js/jquery-ui.min.js"></script>
        <script type="text/javascript">
    var dbi = <%= request.getParameter("dbi") %>;

    $(function() {
        var title = $("#title"),
        url = $("#url"),
        driver = $("#driver"),
        username = $("#username"),
        password = $("#password"),
        allFields = $([]).add(title).add(url).add(driver).add(username).add(password),
        reqs = $("#validateReqs");

        $("#editConnDialog").dialog({
            autoOpen: false,
            height: 400,
            width: 420,
            modal: false,
            buttons: {
                Ok: function() {
                    var bValid = true;
                    allFields.removeClass('ui-state-error');

                    bValid = bValid && checkLength(title,"Title");
                    bValid = bValid && checkLength(url,"URL");
                    bValid = bValid && checkLength(driver,"Driver");
                    bValid = bValid && checkLength(username,"Username");
                    bValid = bValid && checkLength(password,"Password");

                    if (bValid) {
                        $.post("Menu", { 
                            "m": "saveConnection",
                            "dbi":"<%= request.getParameter("dbi") %>",
                            "title": title.val(),
                            "url": url.val(),
                            "driver": driver.val(), "username": username.val(),
                            "password": password.val() },
                        function(data){
                            if (data == "0") {
                                alert("Unable to add connection.");
                            } else {
                                window.location.reload();
                            }
                        });
                        $(this).dialog('close');
                    }
                },
                Cancel: function() {
                    $(this).dialog('close');
                    resetFields();
                }
            }

        });

        $('#info').click(function(){
           $.post("Setup", { "m": "info", "dbi": dbi },
           function(data){
               $('#changer').html(data);
           });
           return false;
      });

      $('#pages').click(function(){
           $.post("Setup", { "m": "pages", "refresh": "true" },
           function(data){
               $('#changer').html(data);
           });
           return false;
      });
        // show validation requirements
        function updateReqs(t) {
            reqs.text(t).effect("highlight",{},1500);
        }
        // basic validation
        function checkLength(o,n) {
            if ( o.val().length < 1 ) {
                o.addClass('ui-state-error');
                updateReqs( n + " is required.");
                return false;
            } else {
                return true;
            }

        }
    });

    // displays add connection dialog
    function showEditConn() {
        $("#editConnDialog").dialog("open");
    }

        </script>
    </head>
    <body>
        <div class="titleHeader">
            <div class="titleLeft"></div>
            <div class="titleRight"></div>
            <div class="titleBody">
                <h1>DB-SVG Schema Setup</h1>
            </div>
        </div>
    <div id="content" class="setupContentBox">
    <div class="menu">
    <a href="Menu">Back to Menu</a><a href="Diagram?dbi=<%= request.getParameter("dbi") %>">Diagram</a><a id="info" href="#schemaInfo">Schema Info</a><a id="pages" href="#pagesConfig">Pages</a>
    </div>
      <div id="changer">
          <jsp:include page="setupInfo.jsp"></jsp:include>
      </div>
    </div>
       <div id="editConnDialog" class="editDialog ui-dialog" title="Edit Connection">
        <p>
            Edit the url and credentials for the database connection. The url and driver should match
            standard Java JDBC syntax.
        </p>
        <span id="validateReqs"></span>
        <form>
            <label for="title">Title</label>
            <input type="text" name="title" id="title" value="<%= currentSchema.getName() %>" class="text ui-widget-content"><br>
            <label for="url">URL</label>
            <input type="text" name="url" id="url" value="<%= cw.getUrl() %>" class="text ui-widget-content"><br>
            <label for="driver">Driver</label>
            <input type="text" name="driver" id="driver" value="<%= cw.getDriver() %>" class="text ui-widget-content"><br>
            <label for="username">Username</label>
            <input type="text" name="username" id="username" value="<%= cw.getUsername() %>" class="text ui-widget-content"><br>
            <label for="password">Password</label>
            <input type="password" name="password" id="password" value="<%= cw.getPassword() %>" class="text ui-widget-content"><br>
        </form>
    </div>
    </body>
</html>