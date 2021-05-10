/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.moroccovolunteer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author mac
 */
@WebServlet(name = "DeleteAvailability", urlPatterns = {"/deleteavailability"})
public class DeleteAvailability extends HttpServlet {

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
        HttpSession session = request.getSession();
        
        try ( PrintWriter out = response.getWriter()) {
        /* TODO output your page here. You may use following sample code. */
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");;
        out.println("<title>Delete Availability</title>");
        out.println("</head>");
        out.println("<body>");
            try {            
              Context ctx = new InitialContext();    
              if (ctx == null) {                  
                 throw new Exception("Error - No Context");
                               }                               
            // /jdbc/postgres is the name of the resource in context.xml   
            DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/postgres");  
            if (ds != null) {                   
                Connection conn = ds.getConnection();
            if (conn != null) {      
                String date = request.getParameter("date");
                
                int id = (int)session.getAttribute("id");
                
                 Statement stmt = conn.createStatement(); 
                String qry = "DELETE FROM Availability WHERE volunteerId="+id+" AND availabilityDate="+date+";";
                 // Result set get the result of the SQL query  
                 stmt.executeUpdate(qry);
                //ResultSet rs = stmt.executeQuery(qry); 
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Availability Deleted Succesfully');");
                out.println("location='availabilities';");
                out.println("</script>");
            } // end of try             
}         }        
catch (SQLException ex) { 
 out.println("SQLException: " + ex); }      
catch (Exception e) {              
e.printStackTrace();     
out.println("Exception caught: ");          
out.println(e.toString());             }  
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
