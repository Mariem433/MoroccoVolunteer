/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.moroccovolunteer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
 * @author johnnyofhyrule93
 */
@WebServlet(name = "ApplicationPage", urlPatterns = {"/applicationpage"})
public class ApplicationPage extends HttpServlet {

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
        String role = (String)session.getAttribute("role");
        int id = (int)session.getAttribute("id");
        String Id =String.valueOf(id);
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<link rel=\"stylesheet\" href=\""+request.getContextPath()+"/assets/bootstrap/css/bootstrap.min.css\">");
            out.println("<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Montserrat:400,400i,700,700i,600,600i\">");
            out.println("<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.css\">");
            out.println("<link rel=\"stylesheet\" href=\"assets/css/styles.min.css\">");
            out.println("<title>Event page</title>");            
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
                        if(role.equals("Organization") && role!=null){
                              Statement stmt1 = conn.createStatement();
                              ResultSet rst1 = stmt1.executeQuery("SELECT "
                                      + " role, E.name, field, P.positionId, volunteerId "
                                      + " From Event AS E INNER JOIN Organization AS O ON E.organizationId = O.organizationId  "
                                      + " INNER JOIN POSITION AS P ON P.eventId= E.eventId NATURAL JOIN Application As A"
                                      + " WHERE applicationStatus = 'pending' AND O.organizationId = "+Id);
                              out.println("<div class=\"container\">");
                              out.println("<div class=\"block-content\">");
                              out.println("<div class=\"product-info\">");
                              out.println("<ul class=\"nav nav-tabs\" role=\"tablist\" id=\"myTab\">");
                              out.println("<li class=\"nav-item\" role=\"presentation\">"
                                          + "<a class=\"nav-link active\" role=\"tab\" data-toggle=\"tab\" id=\"description-tab\" href=\"#description\">"
                                          + "Applications</a></li>");
                              out.println("</ul>");
                              out.println("<div>");
                              out.println("<div class=\"tab-content\" id=\"myTabContent\">");
                              out.println("<div class=\"tab-pane fade show active description\" role=\"tabpanel\" id=\"description\">");
                              out.println("<div class=\"row\">");
                              while (rst1.next()) {
                                  out.println("<div class=\"col-sm-6 col-lg-4\" style=\"margin-top: 35px;\">");
                                  out.println("<div class=\"card clean-card text-center\">");
                                  out.println("<div class=\"card-body info\">");
                                  out.println("<h4 class=\"card-title\">"+rst1.getString(1)+"</h4>");
                                  out.println("<div class=\"row\">\n" +
      "                        <div class=\"col\">\n" +
      "                           <p>Event Name</p>\n" +
      "                        </div>\n" +
      "                        <div class=\"col\">\n" +
      "                           <p class=\"text-primary\">"+rst1.getString(2)+"</p>\n" +
      "                        </div>\n" +
      "                     </div>");
                                  out.println("<div class=\"row\">\n" +
      "                        <div class=\"col\">\n" +
      "                           <p>Field</p>\n" +
      "                        </div>\n" +
      "                        <div class=\"col\">\n" +
      "                           <p class=\"text-primary\">"+rst1.getString(3)+"</p>\n" +
      "                        </div>\n" +
      "                     </div>");
                                  out.println("</div>\n");
                                      out.print("<form action='./cancelapplication'>");
                                      out.print("<input type='hidden' name='positionId' value='" +rst1.getInt(4)+"'>");
                                      out.print("<input type='hidden' name='volunteerId' value='" +rst1.getInt(5)+"'>");
                                      out.println(" <button class=\"btn btn-primary overlay\" type=\"submit\"><i class=\"fas fa-plus\"></i>&nbsp;Cancel</button>\n" );
                                      out.println(" <button class=\"btn btn-primary overlay\" type=\"submit\" formaction= './acceptapplication'><i class=\"fas fa-plus\"></i>&nbsp;Accept</button>\n" +
                                    "</form>\n");
                                  out.println("</div>");  
                                  out.println("</div>");
                              }
                              out.println("</div>");
                              out.println("</div>");
                              out.println("</div>");
                              out.println("</div>");
                              out.println("</div>");
                              conn.close();
                              out.println("</div>");
                              out.println("</div>");
                        }else if(role.equals("Volunteer") && role!=null){
                            Statement stmt1 = conn.createStatement();
                            ResultSet rst1 = stmt1.executeQuery("SELECT "
                                      + " O.organizationName, role, E.name, field, P.positionId, volunteerId "
                                      + " From Event AS E INNER JOIN Organization AS O ON E.organizationId = O.organizationId  "
                                      + " INNER JOIN POSITION AS P ON P.eventId= E.eventId NATURAL JOIN Application As A"
                                      + " WHERE applicationStatus = 'pending' AND O.organizationId = "+Id);
                            out.println("<div class=\"container\">");
                            out.println("<div class=\"block-content\">");
                            out.println("<div class=\"product-info\">");
                            out.println("<ul class=\"nav nav-tabs\" role=\"tablist\" id=\"myTab\">");
                            out.println("<li class=\"nav-item\" role=\"presentation\">"
                                        + "<a class=\"nav-link active\" role=\"tab\" data-toggle=\"tab\" id=\"description-tab\" href=\"#description\">"
                                        + "Applications</a></li>");
                            out.println("</ul>");
                            out.println("<div>");
                            out.println("<div class=\"tab-content\" id=\"myTabContent\">");
                            out.println("<div class=\"tab-pane fade show active description\" role=\"tabpanel\" id=\"description\">");
                            out.println("<div class=\"row\">");
                            while (rst1.next()) {
                                out.println("<div class=\"col-sm-6 col-lg-4\" style=\"margin-top: 35px;\">");
                                out.println("<div class=\"card clean-card text-center\">");
                                out.println("<div class=\"card-body info\">");
                                out.println("<h4 class=\"card-title\">"+rst1.getString(2)+"</h4>");
                                out.println("<div class=\"row\">\n" +
    "                        <div class=\"col\">\n" +
    "                           <p>Organization Name</p>\n" +
    "                        </div>\n" +
    "                        <div class=\"col\">\n" +
    "                           <p class=\"text-primary\">"+rst1.getString(1)+"</p>\n" +
    "                        </div>\n" +
    "                     </div>");
                                out.println("<div class=\"row\">\n" +
    "                        <div class=\"col\">\n" +
    "                           <p>Event Name</p>\n" +
    "                        </div>\n" +
    "                        <div class=\"col\">\n" +
    "                           <p class=\"text-primary\">"+rst1.getString(3)+"</p>\n" +
    "                        </div>\n" +
    "                     </div>");
                                out.println("<div class=\"row\">\n" +
    "                        <div class=\"col\">\n" +
    "                           <p>Field</p>\n" +
    "                        </div>\n" +
    "                        <div class=\"col\">\n" +
    "                           <p class=\"text-primary\">"+rst1.getString(4)+"</p>\n" +
    "                        </div>\n" +
    "                     </div>");
                                out.println("</div>\n");
                                    out.print("<form action='./cancelapplication'>");
                                    out.print("<input type='hidden' name='positionId' value='" +rst1.getInt(5)+"'>");
                                    out.print("<input type='hidden' name='volunteerId' value='" +rst1.getInt(6)+"'>");
                                    out.println(" <button class=\"btn btn-primary overlay\" type=\"submit\"><i class=\"fas fa-plus\"></i>&nbsp;Cancel</button>\n" +
                                    "</form>\n");
                                out.println("</div>");  
                                out.println("</div>");
                            }
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            conn.close();
                            out.println("</div>");
                            out.println("</div>");


                        }
                    } 
                    else {
                        out.println("Error: conn is null ");
                    }
                }
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