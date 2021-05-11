/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.moroccovolunteer;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.naming.*;
import javax.sql.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpSession;

/**
 *
 * @author USER
 */
@WebServlet(name = "AddEvents", urlPatterns = {"/addevents"})
public class AddEvents extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddEvents</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Add Event</h1>");
            try {
                Context ctx = new InitialContext();
                if (ctx == null) {
                throw new Exception("Error - No Context");
            }
            // /jdbc/postgres is the name of the resource in context.xml
            DataSource ds = (DataSource)
            ctx.lookup("java:/comp/env/jdbc/postgres");
            if (ds != null) {
                Connection conn = ds.getConnection();
                if (conn != null) {
                // the insert statement
                String qry = "INSERT INTO Event " + " (eventId, name, Description, eventLocation, eventDate,field, organizationId)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?);";
                // create the insert preparedstatement
                HttpSession session = request.getSession();
                PreparedStatement prepStmt = conn.prepareStatement(qry);
                String name = request.getParameter("name");
                String Desc = request.getParameter("Description");
                String eLocat = request.getParameter("eventLocation");
                String eDate = request.getParameter("eventDate");
                String field = request.getParameter("Field");
                int orgId = (int)session.getAttribute("id");
                prepStmt.setString (2, name);
                prepStmt.setString (3, Desc);
                prepStmt.setString (4, eLocat);
                prepStmt.setString (6, field);
                SimpleDateFormat formatdate = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilDate = formatdate.parse(eDate);
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                prepStmt.setDate (5, sqlDate);
                prepStmt.setInt(7, orgId);
                int eId = 0;
                     Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY,ResultSet.HOLD_CURSORS_OVER_COMMIT);
                     ResultSet x = stmt.executeQuery("SELECT eventId FROM Event");
                        if(x.last()){
                            eId = x.getInt("eventId")+1;
                        }
                prepStmt.setInt(1,eId);
                // execute the preparedstatement
                prepStmt.execute();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Event added suceessfully');");
                out.println("location='orgevents';");
                out.println("</script>");
            } 
        }
    }
           catch (SQLException ex) {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('SQL Exception" + ex +" ');");
                out.println("location='events';");
                out.println("</script>");
           }
            catch (Exception e) {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Exception caught" + e.toString()+" ');");
                out.println("location='events';");
                out.println("</script>");
            }

            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
