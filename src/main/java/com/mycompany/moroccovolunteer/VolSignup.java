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
/**
 *
 * @author johnnyofhyrule93
 */
@WebServlet(name = "VolSignup", urlPatterns = {"/volsignup"})
public class VolSignup extends HttpServlet {

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
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet VolSignup</title>");            
            out.println("</head>");
            out.println("<body>");
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
                        String qry = "CALL proAddVolunteer(?,?,?,?,?,?,?,?);";
                   // create the insert preparedstatement
                        PreparedStatement prepStmt = conn.prepareStatement(qry);
                        
                        String firstname = request.getParameter("fname");
                        String lastname = request.getParameter("lname");
                        String birthdate = request.getParameter("birthdate");
                        String gender = request.getParameter("gender");
                        String adrs = request.getParameter("address");
                        String email = request.getParameter("email");
                        String pass = request.getParameter("password");
                        int id = 0;
                        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY,ResultSet.HOLD_CURSORS_OVER_COMMIT);
                        ResultSet rst = stmt.executeQuery("SELECT volunteerId FROM Volunteer");
                        if(rst.last()){
                            id = rst.getInt("volunteerId")+1;
                        }
                        prepStmt.setInt(1,id);
                        prepStmt.setString (2, firstname);
                        prepStmt.setString (3, lastname);
                        SimpleDateFormat formatdate = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date utilDate = formatdate.parse(birthdate);
                        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                        prepStmt.setDate(4, sqlDate);
                        prepStmt.setString(5, gender);
                        prepStmt.setString(6, adrs);
                        prepStmt.setString(7, email);
                        prepStmt.setString(8, pass);
                        
                        // execute the preparedstatement
                        prepStmt.execute();
                        
                        response.sendRedirect("login.html");
                    } // end of try
                }
            }
            catch (SQLException ex) {
                out.println("SQLException: " + ex);
            }
            catch (Exception e) {
                //e.printStackTrace();
                out.println("Exception caught");
                out.println(e.toString());
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