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
@WebServlet(name = "Events", urlPatterns = {"/events"})
public class Events extends HttpServlet {
    
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
            HttpSession session = request.getSession();
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css\" integrity=\"sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh\" crossorigin=\"anonymous\">");
            out.println("<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Montserrat:400,400i,700,700i,600,600i\">");
            out.println("<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.css\">");
            out.println("<link rel=\"stylesheet\" href=\"assets/css/styles.min.css\">");
            out.println("<title>Events</title>");            
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
                        out.println("<div class=\"container\" style=\"text-align: center;\">\n" +
"            <h1>Events</h1>\n" +
"         </div>");
                        out.println("<div class=\"row justify-content-center\" style=\"margin-top: 10px;margin-right: 0px;margin-left: 0px;\">");
                        
                        Statement stmt = conn.createStatement();
                        ResultSet rst = stmt.executeQuery("SELECT "
                                + "eventId, organizationName, name, E.description, eventlocation, eventdate, field"
                                + " From Event AS E INNER JOIN Organization AS O ON O.organizationId = E.organizationId");
                        while (rst.next()) {
                            out.println("<div class=\"col-sm-6 col-lg-4\" style=\"margin-top: 35px;\">");
                            out.println("<div class=\"card clean-card text-center\">");
                            out.println("<div class=\"card-body info\">");
                            out.println("<h4 class=\"card-title\">"+rst.getString(3)+"</h4>");
                            out.println("<div class=\"row\">\n" +
"                        <div class=\"col\">\n" +
"                           <p>Organizer</p>\n" +
"                        </div>\n" +
"                        <div class=\"col\">\n" +
"                           <p class=\"text-primary\">"+rst.getString(2)+"</p>\n" +
"                        </div>\n" +
"                     </div>");
                            out.println("<div class=\"row\">\n" +
"                        <div class=\"col\">\n" +
"                           <p>Location</p>\n" +
"                        </div>\n" +
"                        <div class=\"col\">\n" +
"                           <p class=\"text-primary\">"+rst.getString(5)+"</p>\n" +
"                        </div>\n" +
"                     </div>");
                            out.println("<div class=\"row\">\n" +
"                        <div class=\"col\">\n" +
"                           <p>Description</p>\n" +
"                        </div>\n" +
"                        <div class=\"col\">\n" +
"                           <p class=\"text-primary\">"+rst.getString(4)+"</p>\n" +
"                        </div>\n" +
"                     </div>");
                            out.println("<div class=\"row\">\n" +
"                        <div class=\"col\">\n" +
"                           <p>Date</p>\n" +
"                        </div>\n" +
"                        <div class=\"col\">\n" +
"                           <p class=\"text-primary\">"+rst.getDate(6)+"</p>\n" +
"                        </div>\n" +
"                     </div>");
                            out.println("<div class=\"row\">\n" +
"                        <div class=\"col\">\n" +
"                           <p>Field</p>\n" +
"                        </div>\n" +
"                        <div class=\"col\">\n" +
"                           <p class=\"text-primary\">"+rst.getString(7)+"</p>\n" +
"                        </div>\n" +
"                     </div>");
                            out.println("</div>\n");
                            out.print("<form action='./eventpage'>");
                            out.print("<input type='hidden' name='eventId' value='" +rst.getInt(1)+"'>");
                 out.println(" <button class=\"btn btn-primary overlay\" type=\"submit\"><i class=\"fas fa-plus\"></i>&nbsp;More details</button>\n" +
                         "</form>\n"+
"               </div>\n" +
"            </div>\n");
                        }
                        conn.close();
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
