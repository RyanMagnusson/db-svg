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

    Document   : menu
    Created on : Feb 8, 2010, 10:27:33 PM
    Author     : Derrick Bowen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="DBViewer.models.*" %>
<%@page import="java.util.*" %>
<%@page import="DBViewer.objects.model.*" %>
<%@page import="DBViewer.objects.view.*" %>
<%@page import="DBViewer.controllers.*" %>
<%@page import="DBViewer.ServiceLocator.*" %>
<%  
ServiceLocator sLocator = ProdServiceLocator.getInstance();
%>
<!DOCTYPE HTML>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DB-SVG | Menu</title>
        <link rel="stylesheet" type="text/css" media="screen" href="css/style.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/jq-ui.css" />
        <link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" src="js/jquery-ui.min.js"></script>
        <script type="text/javascript">
            var connToDelete;
            $(function() {
                var title = $("#title"),
                url = $("#url"),
                driver = $("#driver"),
                username = $("#username"),
                password = $("#password"),
                allFields = $([]).add(title).add(url).add(driver).add(username).add(password),
                reqs = $("#validateReqs");

                $("#loadDialog").dialog({
                    autoOpen: false,
                    resizable: false,
                    height:170,
                    modal: true
                });
                $("#removeDialog").dialog({
                    autoOpen: false,
                    resizable: false,
                    height:230,
                    modal: true,
                    buttons: {
                        Ok: function() {
                            $.post("Menu", { "m": "removeConnection", "id": connToDelete },
                                function(data){
                                    if (data == "0") {
                                        alert("Unable to delete connection.");
                                    } else {
                                        $("#conn"+connToDelete).remove();
                                    }
                                });
                            $(this).dialog('close');
                        },
                        Cancel: function() {
                            $(this).dialog('close');
                        }
                    }
                });
                $("#addDialog").dialog({
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
                                $.post("Menu", { "m": "addConnection", "title": title.val(), "url": url.val(),
                                    "driver": driver.val(), "username": username.val(), "password": password.val() },
                                function(data){
                                    if (data == "0") {
                                        alert("Unable to add connection.");
                                    } else {
                                        $(".frontmenuadd").remove();
                                        $(".frontmenulist").append('<li id="conn' + data + '"><a href="Diagram?dbi=' + data + '" onclick="showLoading();" >' + title.val() + '</a><span class="frontmenusetup"><a href="Setup?dbi=' + data + '" onclick="showLoading();" ><img src="images/document-properties.png\" alt=\"Setup\" border=0></a></span><span class="frontmenuremove"><a href="#Remove' + data + '" onclick=\"showRemove(' + data + ',\'' + title.val() + '\');" ><img src="images/edit-delete.png" alt="Remove" border=0></a></span></li>');
                                        $(".frontmenulist").append('<li class="frontmenuadd"><a href="#" onclick="showAdd();" >+ Add Connection</a></li>');
                                        resetFields();
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
                function resetFields(){
                    allFields.val('').removeClass('ui-state-error');
                    title.val('My Database');
                    url.val('jdbc:mysql://localhost:3306/mydb');
                    driver.val('com.mysql.jdbc.Driver');
                    reqs.text(' ');
                }
            });
            // displays loading dialog
            function showLoading() {
                $("#loadDialog").dialog("open");
            }
            // displays add connection dialog
            function showAdd() {
                $("#addDialog").dialog("open");
            }
            // displays add connection dialog
            function showRemove(idToNix,titleToNix) {
                connToDelete = idToNix;
                $("#removeText").text(titleToNix);
                $("#removeDialog").dialog("open");
            }
        </script>
    </head>
    <body>
        <div class="titleHeader">
            <div class="titleLeft"></div>
            <div class="titleRight"></div>
            <div class="titleBody">
                <h1>DB-SVG Menu</h1>
            </div>
        </div>
        <div id="content" class="contentBoxMenu">
            <h2>Exploreable Databases:</h2>
            <ul class="frontmenulist">
                <% 
                          Map<String, ConnectionWrapper> prefs = sLocator.getProgramCache().getAllConnections();
                          List<ConnectionWrapper> connections = new ArrayList();
                          connections.addAll(prefs.values());
                          Collections.sort(connections);

                          for (ConnectionWrapper cw : connections) {
                              out.println("<li id=\"conn" + cw.getId() + "\"><a href=\"Diagram?dbi=" + cw.getId() + "\" onclick=\"showLoading();\" >" + cw.getTitle() + "</a><span class=\"frontmenusetup\"><a href=\"Setup?dbi=" + cw.getId() + "\" onclick=\"showLoading();\" ><img src=\"images/document-properties.png\" alt=\"Setup\" border=0></a></span><span class=\"frontmenuremove\"><a href=\"#Remove" + cw.getId() + "\" onclick=\"showRemove(" + cw.getId() + ",'" + cw.getTitle() + "');\" ><img src=\"images/edit-delete.png\" alt=\"Remove\" border=0></a></span></li>");
                          }
                %>
                <li class="frontmenuadd"><a href="#" onclick="showAdd();" >+ Add Connection</a></li>
            </ul>
            <div id="loadDialog" class="loadDialog ui-dialog" title="Loading, Please Wait...">
                <p><center><img src="images/ajax-loader.gif" alt="The database is being read into memory."/></center></p>
            </div>

            <div id="addDialog" class="addDialog ui-dialog" title="Add New Connection">
                <p>
                    Fill out the url and credentials for the database connection. The url and driver should match
                    standard Java JDBC syntax (as shown).
                </p>
                <span id="validateReqs"></span>
                <form>
                    <label for="title">Title</label>
                    <input type="text" name="title" id="title" value="My Database" class="text ui-widget-content"><br>
                    <label for="url">URL</label>
                    <input type="text" name="url" id="url" value="jdbc:mysql://localhost:3306/mydb" class="text ui-widget-content"><br>
                    <label for="driver">Driver</label>
                    <input type="text" name="driver" id="driver" value="com.mysql.jdbc.Driver" class="text ui-widget-content"><br>
                    <label for="username">Username</label>
                    <input type="text" name="username" id="username" value="" class="text ui-widget-content"><br>
                    <label for="password">Password</label>
                    <input type="password" name="password" id="password" value="" class="text ui-widget-content"><br>
                </form>
            </div>
            <div id="removeDialog" class="removeDialog ui-dialog" title="Remove?">
                <p>Do you really want to remove this connection?</p>
                <div id="removeText"></div>
            </div>

        </div>
    </body>
</html>
