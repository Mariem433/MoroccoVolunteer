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
@WebServlet(name = "Applications", urlPatterns = {"/applications"})
public class Applications extends HttpServlet {

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
            
            out.println("<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Montserrat:400,400i,700,700i,600,600i\">");
            out.println("<link rel=\"stylesheet\" href=\""+request.getContextPath()+"/assets/bootstrap/css/bootstrap.min.css\">");
            out.println("<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.css\">");
            out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
            out.println("<title>Applications</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<nav class=\"navbar navbar-light navbar-expand-lg fixed-top bg-white clean-navbar\">\n" +
"            <div class=\"container\"><a class=\"navbar-brand logo\" href=\"#\">VolunteerMorocco</a><button data-toggle=\"collapse\" class=\"navbar-toggler\" data-target=\"#navcol-1\"><span class=\"sr-only\">Toggle navigation</span><span class=\"navbar-toggler-icon\"></span></button>\n" +
"                <div class=\"collapse navbar-collapse\" id=\"navcol-1\">\n" +
"                    <ul class=\"navbar-nav ml-auto\">\n" +
"                        <li class=\"nav-item\"><a class=\"nav-link\" href=\"organization.jsp\">Profile</a></li>\n" +
"                        <li class=\"nav-item\"><a class=\"nav-link\" href=\"e\">Manage events</a></li>\n" +
"                        <li class=\"nav-item\"><a class=\"nav-link\" href=\"applications\">applications</a></li>\n" +
"                        <li class=\"nav-item\"><a class=\"nav-link\" href=\"s\">volunteers</a></li>\n" +
"                        <li class=\"nav-item\"><a class=\"nav-link\" href=\"logout\">logout</a></li>\n" +
"                    </ul>\n" +
"                </div>\n" +
"            </div>\n" +
"        </nav>\n" +
"            \n" +
"        <h1>MoroccoVolunteer</h1>");
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
                        HttpSession session = request.getSession();
                        out.println("<section class=\"clean-block clean-product dark\">\n" +
"            <div class=\"container\">\n" +
"                <div class=\"block-heading\">\n" +
"                    <h2 class=\"text-info\">Applications</h2>\n" +
"                </div>\n" +
"                <div class=\"block-content\">\n" +
"                    <div class=\"product-info\">\n" +
"                        <div>\n" +
"                            <ul class=\"nav nav-tabs\" role=\"tablist\" id=\"myTab\">\n" +
"                                <li class=\"nav-item\" role=\"presentation\"><a class=\"nav-link active\" role=\"tab\" data-toggle=\"tab\" id=\"description-tab\" href=\"#description\">Pending</a></li>\n" +
"                                <li class=\"nav-item\" role=\"presentation\"><a class=\"nav-link\" role=\"tab\" data-toggle=\"tab\" id=\"specifications-tabs\" href=\"#specifications\">Accepted</a></li>\n" +
"                                <li class=\"nav-item\" role=\"presentation\"><a class=\"nav-link\" role=\"tab\" data-toggle=\"tab\" id=\"reviews-tab\" href=\"#reviews\">Rejected</a></li>\n" +
"                            </ul>");
                        
                        Statement stmt1 = conn.createStatement();
                        ResultSet rst1 = stmt1.executeQuery("SELECT * FROM vwPendingAppFutureEvents WHERE organizationId = "+(int)session.getAttribute("id")+";");
                        out.println("<div class=\"tab-content\" id=\"myTabContent\">\n" +
"                                    <div class=\"tab-pane fade show active description\" role=\"tabpanel\" id=\"description\">\n"
                                        + "<table id=\"example\" class=\"table table-striped table-bordered\" cellspacing=\"0\" width=\"100%\">\n" +
"                                        <thead>\n" +
"                                            <tr>\n" +
"                                                <th>Event</th>\n" +
"                                                <th>Position</th>\n" +
"                                                <th>First Name</th>\n" +
"                                                <th>Last Name</th>\n" +
"                                                <th>Decision</th>\n" +
"                                                <th>Confirm</th>\n" +
"                                            </tr>\n" +
"                                        </thead>");
                        out.println("<tbody>");
                        out.println("<tr>");
                        while (rst1.next()) {
                            out.println("<td>"+rst1.getString(2)+"</td>");
                            out.println("<td>"+rst1.getString(3)+"</td>");
                            out.println("<td>"+rst1.getString(4)+"</td>");
                            out.println("<td>"+rst1.getString(5)+"</td>");
                            out.println("<td>");
                            out.println("<form action=\"processdection\">");
                            out.println("<div class=\"dropdown-menu\" aria-labelledby=\"dropdownMenu2\">\n" +
                                    "    <button class=\"dropdown-item\" type=\"button\">Accept</button>\n" +
                                    "    <button class=\"dropdown-item\" type=\"button\">Reject</button>\n" +
                                    "  </div>");
                            out.println("<button type=\"submit\" class=\"btn btn-primary\">Submit</button>");
                            out.println("</form>");
                            out.println("</td>");
                        }
                        out.println("</tr>");
                        out.println("</tbody>\n" +
"                                    </table>\n" +
"                                </div>");
                        out.println("</div>");
                        out.println("<div class=\"tab-pane fade specifications\" role=\"tabpanel\" id=\"specifications\">\n" +
"                                    <div class=\"table-responsive table-bordered\">");
                        out.println("table id=\"example\" class=\"table table-striped table-bordered\" cellspacing=\"0\" width=\"100%\">");
                        out.println("<thead>\n" +
"                                            <tr>\n" +
"                                                <th>Event</th>\n" +
"                                                <th>Position</th>\n" +
"                                                <th>First Name</th>\n" +
"                                                <th>Last Name</th>\n" +
"                                            </tr>\n" +
"                                        </thead>");
                        Statement stmt2 = conn.createStatement();
                        ResultSet rst2 = stmt2.executeQuery("SELECT * FROM vwPendingAppFutureEvents WHERE organizationId = "+(int)session.getAttribute("id")+";");
                        out.println("<tbody>\n" +
"                                            <tr>");
                        while(rst2.next()){
                            out.println("<td>"+rst1.getString(2)+"</td>");
                            out.println("<td>"+rst1.getString(3)+"</td>");
                            out.println("<td>"+rst1.getString(4)+"</td>");
                            out.println("<td>"+rst1.getString(5)+"</td>");
                        }
                        out.println("</tr>");
                        out.println("</tbody>\n" +
"                                    </table>\n" +
"                                </div>");
                        out.println("<div class=\"tab-pane fade\" role=\"tabpanel\" id=\"reviews\">");
                        
                        Statement stmt3 = conn.createStatement();
                        ResultSet rst3 = stmt3.executeQuery("SELECT * FROM vwRejectedAppFutureEvents WHERE organizationId = "+(int)session.getAttribute("id")+";");
                        out.println("</div>");
                        out.println("table id=\"example\" class=\"table table-striped table-bordered\" cellspacing=\"0\" width=\"100%\">");
                        out.println("<thead>\n" +
"                                            <tr>\n" +
"                                                <th>Event</th>\n" +
"                                                <th>Position</th>\n" +
"                                                <th>First Name</th>\n" +
"                                                <th>Last Name</th>\n" +
"                                            </tr>\n" +
"                                        </thead>");
                        out.println("<tbody>\n" +
"                                            <tr>");
                        while(rst3.next()){
                            out.println("<td>"+rst1.getString(2)+"</td>");
                            out.println("<td>"+rst1.getString(3)+"</td>");
                            out.println("<td>"+rst1.getString(4)+"</td>");
                            out.println("<td>"+rst1.getString(5)+"</td>");
                        }
                        out.println("</tr>");
                        out.println("</tbody>\n" +
"                                        </table>\n" +
"                                </div>");
                        conn.close();
                        out.println("</div>\n" +
"                        </div>\n" +
"                    </div>\n" +
"                </div>\n" +
"            </div>\n" +
"        </section>\n" +
"    </main>");
                        
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
            out.println("<script src=\"https://code.jquery.com/jquery-3.4.1.slim.min.js\" integrity=\"sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n\" crossorigin=\"anonymous\"></script>\n" +
"        <script src=\"https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js\" integrity=\"sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo\" crossorigin=\"anonymous\"></script>\n" +
"        <script src=\"https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.js\"></script>\n" +
"        <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js\" integrity=\"sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6\" crossorigin=\"anonymous\"></script>");
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
