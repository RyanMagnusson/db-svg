/*
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
 *
 */
package DBViewer.controllers;

import DBViewer.ServiceLocator.ProdServiceLocator;
import DBViewer.ServiceLocator.ServiceLocator;
import DBViewer.models.ConnectionWrapper;
import DBViewer.models.InternalDataDAO;
import DBViewer.objects.view.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.sql.*;
import java.util.logging.*;
import javax.naming.NamingException;

/**
 *
 * @author derrick.bowen
 */
public abstract class AbstractViewController extends HttpServlet {

    protected String dbi = "";
    protected String pageid = "";
    protected ServiceLocator sLocator;
    protected SortedSchema currentSchema = new SortedSchema();
    protected SchemaPage sPage; 
    
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        ProdServiceLocator pLocator = ProdServiceLocator.getInstance();
        try {
            Context env = (Context) new InitialContext().lookup("java:comp/env");
            String iDAOpath = (String) env.lookup("AppDBLocation");
            
            if (pLocator.isDAOPathSet()) {
                pLocator.setIDAOPath(iDAOpath);
                
                InternalDataDAO iDAO = pLocator.getIDAO();
                try {
                    conn = iDAO.getConnection();
                } catch (SQLException e) {
                    System.out.println("Attempting Second DB path.");
                    iDAOpath = (String) env.lookup("AppDBLocation2");
                    pLocator.setIDAOPath(iDAOpath);
                    iDAO = pLocator.getIDAO();
                    conn = iDAO.getConnection();
                }
            }
            sLocator = pLocator;

        } catch (NamingException ex) {
            Logger.getLogger(AbstractViewController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (Exception e) {
            Logger.getLogger(AbstractViewController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            pLocator.setIDAOPath(null);
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (Exception ex) {
                // It's probably already closed or never opened but well trace it anyway
                Logger.getLogger(AbstractViewController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        
        dbi = request.getParameter("dbi");
        if (dbi != null && !dbi.equals("")) {
//            request.getSession().setAttribute("CurrentDBI", dbi);
            // collect available parameters and initialize/retrieve objects
            pageid = request.getParameter("page");

            if (pageid == null) {
                pageid = (String) request.getSession().getAttribute("pageid");
            } 
            if (request.getSession().getAttribute("CurrentSchema") == null || 
                    request.getSession().getAttribute("CurrentSchema").getClass() != currentSchema.getClass() ||
                    !((SortedSchema)request.getSession().getAttribute("CurrentSchema")).getDbi().equals(dbi) ) {
                // if no schema loaded, save the new one to the session
                currentSchema = new SortedSchema();
                currentSchema.setDbi(dbi);
                request.getSession().setAttribute("CurrentSchema", currentSchema);
            } else {
                // if a schema is found, load it from the session.
                currentSchema = (SortedSchema) request.getSession().getAttribute("CurrentSchema");
            }

            ConnectionWrapper cw = sLocator.getProgramCache().getConnection(dbi);
            request.getSession().setAttribute("CurrentConn", cw);
            // prepare the requested page of the schema for display.  
            
            if (currentSchema != null) {
                sPage = SchemaController.prepareSchema(currentSchema, dbi, pageid);
            } else {
                sPage = new SchemaPage();
            }
            request.getSession().setAttribute("CurrentPage", sPage);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Abstract View Controller for DBSVG project";
    }// </editor-fold>
}
