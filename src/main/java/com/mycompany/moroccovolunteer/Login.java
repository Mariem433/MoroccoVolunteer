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
 * @author johnnyofhyrule93
 */
@WebServlet(name = "login", urlPatterns = {"/login"})
public class Login extends HttpServlet {

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
        String qry;
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet login</title>");            
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
                        
                   // create the insert preparedstatement
                        
                        
                        String email = request.getParameter("email");
                        String pass = request.getParameter("password");
                        String role = request.getParameter("role");
                        if(role.equals("Volunteer")){
                            qry = "SELECT * FROM volunteerLogin WHERE"
                                    + " volunteerEmail = (?) AND password = (?);";
                        }
                        else{
                            qry = "SELECT * FROM OrganizationLogin WHERE"
                                    + " organizationEmail = (?) AND password = (?);";
                        }
                        PreparedStatement prepStmt = conn.prepareStatement(qry);
                        prepStmt.setString(1,email);
                        prepStmt.setString(2,pass);
                        ResultSet rst = prepStmt.executeQuery();
                        if(rst.next()){
                            if(role.equals("Volunteer")){
                               out.println("todo");
                            }
                            else{
                                HttpSession session = request.getSession();
                                int id = rst.getInt("organizationId");
                                session.setAttribute("id", id);
                                session.setAttribute("role", role);
                                response.sendRedirect("organization.jsp");
                            }
                        }
                        else{
                            out.println("Login failed successfully");
                        }
                        
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
