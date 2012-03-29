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
--%><%@page contentType="text/html" pageEncoding="UTF-8"%><!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DB-SVG | Menu</title>
        <link rel="stylesheet" type="text/css" media="screen" href="css/style.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/jq-ui.css" />
        <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon" />
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui.min.js"></script>
        <script type="text/javascript" src="js/common.js"></script>
        <script type="text/javascript" src="js/menu.js"></script>
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
            <ul class="frontmenulist"></ul>
            <jsp:include page="loading.jsp"></jsp:include>

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
