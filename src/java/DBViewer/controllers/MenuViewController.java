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
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DBViewer.models.*;
import java.io.PrintWriter;
import java.util.*;
import java.sql.*;

/**
 *
 * @author horizon
 */
public class MenuViewController extends AbstractViewController {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * creates a the main menu page for the application.  
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // collect available parameters and initialize/retrieve objects
        super.processRequest(request, response);
        
        sLocator = ProdServiceLocator.getInstance();
        Connection conn = null;
        InternalDataDAO iDAO = sLocator.getIDAO();

        String m = request.getParameter("m");

        // ----------------------------------->   ADD CONNECTION ACTION
        if (m != null && m.equals("addConnection")) {

            String title = request.getParameter("title");
            String url = request.getParameter("url");
            String driver = request.getParameter("driver");
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            boolean isValid = false;

            if (title != null && !title.equals("") && url != null && !url.equals("")
                    && driver != null && !driver.equals("") && username != null && !username.equals("")
                    && password != null && !password.equals("")) {
                isValid = true;
            }

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            try {
                if (!isValid) {
                    out.println("0");
                } else {
                    ConnectionWrapper cw = new ConnectionWrapper();
                    cw.setTitle(title);
                    cw.setUrl(url);
                    cw.setDriver(driver);
                    cw.setUsername(username);
                    cw.setPassword(password);
                    try {
                        conn = iDAO.getConnection();
                        iDAO.saveConnectionWrapperNewID(cw, conn);
                        sLocator.getProgramCache().putConnection(Integer.toString(cw.getId()), cw);
                        out.println(cw.getId());
                    } catch (Exception e) {
                        Logger.getLogger(MenuViewController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
                        e.printStackTrace();
                        out.println("0");
                    } finally {
                        try {
                            conn.close();
                        } catch (Exception ex) {
                            // It's probably already closed or never opened but we'll trace it anyway
                            Logger.getLogger(MenuViewController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                            ex.printStackTrace();
                        }
                    }
                }

            } finally {
                out.close();
            }
            // ----------------------------------->   SAVE EXISTING CONNECTION ACTION
        } else if (m != null && m.equals("saveConnection")) {

            String dbi = request.getParameter("dbi");
            String title = request.getParameter("title");
            String url = request.getParameter("url");
            String driver = request.getParameter("driver");
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            boolean isValid = false;

            if (title != null && !title.equals("") && url != null && !url.equals("")
                    && driver != null && !driver.equals("") && username != null && !username.equals("")
                    && password != null && !password.equals("") && dbi != null && !dbi.equals("")) {
                isValid = true;
            }

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            try {
                if (!isValid) {
                    out.println("0");
                } else {
                    ConnectionWrapper cw = new ConnectionWrapper();
                    cw = sLocator.getProgramCache().getConnection(dbi);

                    cw.setId(Integer.parseInt(dbi));
                    cw.setTitle(title);
                    cw.setUrl(url);
                    cw.setDriver(driver);
                    cw.setUsername(username);
                    cw.setPassword(password);

                    try {
                        conn = iDAO.getConnection();
                        iDAO.saveConnectionWrapper(cw, conn);
                        sLocator.getProgramCache().putConnection(Integer.toString(cw.getId()), cw);
                        out.println("1");
                    } catch (Exception e) {
                        Logger.getLogger(MenuViewController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
                        e.printStackTrace();
                        out.println("0");
                    } finally {
                        try {
                            conn.close();
                        } catch (Exception ex) {
                            // It's probably already closed or never opened but we'll trace it anyway
                            Logger.getLogger(MenuViewController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                            ex.printStackTrace();
                        }
                    }
                }

            } finally {
                out.close();
            }
            // ----------------------------------->   REMOVE CONNECTION ACTION
        } else if (m != null && m.equals("removeConnection")) {

            String id = request.getParameter("id");


            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            try {
                try {
                    conn = iDAO.getConnection();
                    iDAO.deleteConnectionWrapper(id, conn);
                    Map<String, ConnectionWrapper> prefs = new HashMap();
                    prefs = iDAO.readAllConnectionWrappers(conn);
                    sLocator.getProgramCache().setAllConnections(prefs);
                    out.println(id);
                } catch (Exception e) {
                    Logger.getLogger(MenuViewController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
                    e.printStackTrace();
                    out.println("0");
                } finally {
                    try {
                        conn.close();
                    } catch (Exception ex) {
                        // It's probably already closed or never opened but we'll trace it anyway
                        Logger.getLogger(MenuViewController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                        ex.printStackTrace();
                    }
                }

            } finally {
                out.close();
            }

            // ----------------------------------->   MAIN MENU ACTION
        } else {
            try {
                
                conn = iDAO.getConnection();
                iDAO.setUpInternalDB(conn);

                Map<String, ConnectionWrapper> prefs = new HashMap();
                prefs = iDAO.readAllConnectionWrappers(conn);
                sLocator.getProgramCache().setAllConnections(prefs);

                getServletConfig().getServletContext().getRequestDispatcher(
                        "/menu.jsp").forward(request, response);

            } catch (Exception e) {
                Logger.getLogger(MenuViewController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
                e.printStackTrace();
            } finally {
                try {
                    conn.close();
                } catch (Exception ex) {
                    // It's probably already closed or never opened but we'll trace it anyway
                    Logger.getLogger(MenuViewController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                    ex.printStackTrace();
                }
            }
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
        return "Short description";
    }// </editor-fold>
}
