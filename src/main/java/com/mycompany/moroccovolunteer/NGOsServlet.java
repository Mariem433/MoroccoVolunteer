package com.mycompany.moroccovolunteer;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.naming.*;
import javax.sql.*;
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mac
 */
@WebServlet(name = "NGOsServlet", urlPatterns = {"/NGOs"})
public class NGOsServlet extends HttpServlet {

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
out.println("<title>VolunteersMorocco</title>");
out.println("</head>");
out.println("<body>");
out.println("<h1>NGOs</h1>");
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
 out.println("<table>");
Statement stmt = conn.createStatement();
ResultSet rst = stmt.executeQuery("SELECT organizationId, organizationName, description, headquarterAddress, establishmentDate, phoneNumber from Organization;");
 out.println("<tr><th>organizationId</th><th>organizationName</th><th>description</th><th>headquarterAddress</th><th>establishmentDate</th><th>phoneNumber</th></tr>");
 while (rst.next()) {
 out.print("<tr>");
out.print("<td>" + rst.getInt(1) + "</td>");
out.print("<td>" + rst.getString(2) + "</td>");
out.print("<td>" + rst.getString(3) + "</td>");
out.print("<td>" + rst.getString(4) + "</td>");
out.print("<td>" + rst.getDate(5) + "</td>");
out.print("<td>" + rst.getString(6) + "</td>");
out.print("</tr>\n");
 }
conn.close();
out.println("</table>");
 } else {
 out.println("Error: conn is null ");
 }
 }
 } catch (Exception e) {
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
