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
@WebServlet(name = "EventPage", urlPatterns = {"/eventpage"})
public class EventPage extends HttpServlet {

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
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css\" integrity=\"sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh\" crossorigin=\"anonymous\">");
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
                        Statement stmt1 = conn.createStatement();
                        Statement stmt2 = conn.createStatement();
                        String eventId = request.getParameter("eventId");
                        ResultSet rst1 = stmt1.executeQuery("SELECT "
                                + "organizationName, name, E.description, eventlocation, eventdate, field"
                                + " From Event AS E INNER JOIN Organization AS O ON O.organizationId = E.organizationId "
                                + "WHERE eventId ="+eventId);
                        ResultSet rst2 = stmt2.executeQuery("SELECT * FROM Position "
                                + "WHERE eventId = "+eventId);
                        out.println("<div class=\"container\">");
                        out.println("<div class=\"block-content\">");
                        while (rst1.next()) {
                            out.println("<div class=\"product-info\">");
                            out.println("<div class=\"row\">");
                            out.println("<div class=\"col-md-6\">");
                            out.println("<div class=\"info\">");
                            out.println("<h3>"+rst1.getString(2)+"</h3>");
                            out.println("<div class=\"info\">");
                            out.println("<h3>"+rst1.getDate(5)+"</h3>");
                            
                            out.println("<p>"+rst1.getString(4)+"</p>");
                            out.println("<p> By:"+rst1.getString(1)+"</p>");
                            out.println("</div>");
                            out.println("<div class=\"summary\">"+rst1.getString(3)+"</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                        }
                        out.println("<div class=\"product-info\">");
                        out.println("<ul class=\"nav nav-tabs\" role=\"tablist\" id=\"myTab\">");
                        out.println("<li class=\"nav-item\" role=\"presentation\">"
                                    + "<a class=\"nav-link active\" role=\"tab\" data-toggle=\"tab\" id=\"description-tab\" href=\"#description\">"
                                    + "Positions</a></li>");
                        out.println("</ul>");
                        out.println("<div>");
                        out.println("<div class=\"tab-content\" id=\"myTabContent\">");
                        out.println("<div class=\"tab-pane fade show active description\" role=\"tabpanel\" id=\"description\">");
                        out.println("<div class=\"row\">");
                        while (rst2.next()) {
                            out.println("<div class=\"col-sm-6 col-lg-4\" style=\"margin-top: 35px;\">");
                            out.println("<div class=\"card clean-card text-center\">");
                            out.println("<div class=\"card-body info\">");
                            out.println("<h4 class=\"card-title\">"+rst2.getString(2)+"</h4>");
                            out.println("<div class=\"row\">\n" +
"                        <div class=\"col\">\n" +
"                           <p>Description</p>\n" +
"                        </div>\n" +
"                        <div class=\"col\">\n" +
"                           <p class=\"text-primary\">"+rst2.getString(3)+"</p>\n" +
"                        </div>\n" +
"                     </div>");
                            out.println("<div class=\"row\">\n" +
"                        <div class=\"col\">\n" +
"                           <p>Spots wanted</p>\n" +
"                        </div>\n" +
"                        <div class=\"col\">\n" +
"                           <p class=\"text-primary\">"+rst2.getString(5)+"</p>\n" +
"                        </div>\n" +
"                     </div>");
                            out.println("<div class=\"row\">\n" +
"                        <div class=\"col\">\n" +
"                           <p>Participating</p>\n" +
"                        </div>\n" +
"                        <div class=\"col\">\n" +
"                           <p class=\"text-primary\">"+rst2.getString(6)+"</p>\n" +
"                        </div>\n" +
"                     </div>");
                            out.println("<div class=\"row\">\n" +
"                        <div class=\"col\">\n" +
"                           <p>Status</p>\n" +
"                        </div>\n" +
"                        <div class=\"col\">\n" +
"                           <p class=\"text-primary\">"+rst2.getString(7)+"</p>\n" +
"                        </div>\n" +
"                     </div>");
                            
                            out.println("</div>\n");
                            if(role != null && role.equals("Volunteer")){
                                out.print("<form action='./positionapply'>");
                            out.print("<input type='hidden' name='positionId' value='" +rst2.getInt(1)+"'>");
                            out.println(" <button class=\"btn btn-primary overlay\" type=\"submit\"><i class=\"fas fa-plus\"></i>&nbsp;Apply</button>\n" +
                                "</form>\n");
                            }
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
