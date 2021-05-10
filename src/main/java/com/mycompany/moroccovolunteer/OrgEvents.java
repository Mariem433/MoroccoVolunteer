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
import java.sql.SQLException;
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
@WebServlet(name = "OrgEvents", urlPatterns = {"/orgevents"})
public class OrgEvents extends HttpServlet {

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
            out.println("<title>Events</title>");            
            out.println("</head>");
            out.println("<body>");
            HttpSession session = request.getSession();
            int id = (int)session.getAttribute("id");
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
                out.println("<nav class=\"navbar navbar-light navbar-expand-lg fixed-top bg-white clean-navbar\">\n" +
"         <div class=\"container\">\n" +
"            <a class=\"navbar-brand logo\" href=\"#\">VolunteerMorocco</a><button data-toggle=\"collapse\" class=\"navbar-toggler\" data-target=\"#navcol-1\"><span class=\"sr-only\">Toggle navigation</span><span class=\"navbar-toggler-icon\"></span></button>\n" +
"            <div class=\"collapse navbar-collapse\" id=\"navcol-1\">\n" +
"               <ul class=\"navbar-nav ml-auto\">\n" +
"                  <li class=\"nav-item\"><a class=\"nav-link\" href=\"volunteer.jsp\">Profile</a></li>\n" +
"                  <li class=\"nav-item\"><a class=\"nav-link\" href=\"orgevent\">Events</a></li>\n" +
"                  <li class=\"nav-item\"><a class=\"nav-link\" href=\"applications\">applications</a></li>\n" +
"                  <li class=\"nav-item\"><a class=\"nav-link\" href=\"applications\">volunteers</a></li>\n" +
"                  <li class=\"nav-item\"><a class=\"nav-link\" href=\"logout\">logout</a></li>\n" +
"               </ul>\n" +
"            </div>\n" +
"         </div>\n" +
"      </nav>");
                out.println(" <main class=\"page product-page\">");
                out.println("<section class=\"clean-block clean-product dark\">");
                out.println("<div class=\"container\">");
                out.println("<div class=\"block-heading\">\n" +
"                  <h2 class=\"text-info\">Events</h2>\n" +
"               </div>");
                out.println("<div class=\"block-content\">");
                out.println("<div class=\"product-info\">");
                out.println("<div>");
                out.println("<ul class=\"nav nav-tabs\" role=\"tablist\" id=\"myTab\">\n" +
"                           <li class=\"nav-item\" role=\"presentation\"><a class=\"nav-link\" role=\"tab\" data-toggle=\"tab\" id=\"description-tab\" href=\"#description\">Past Events</a></li>\n" +
"                           <li class=\"nav-item\" role=\"presentation\"><a class=\"nav-link\" role=\"tab\" data-toggle=\"tab\" id=\"specifications-tabs\" href=\"#specifications\">Current and Upcoming Events</a></li>\n" +
"                           <li class=\"nav-item\" role=\"presentation\"><a class=\"nav-link active\" role=\"tab\" data-toggle=\"tab\" id=\"reviews-tab\" href=\"#reviews\">Add Event</a></li>\n" +
"                        </ul>");
                out.println("<div class=\"tab-content\" id=\"myTabContent\">");
                out.println("<div class=\"tab-pane fade description\" role=\"tabpanel\" id=\"description\">");
                out.println("<div class=\"row justify-content-center\" style=\"margin-top: 5px;margin-right: 0px;margin-left: 0px;\">");
                Statement stmt1 = conn.createStatement();
                ResultSet rst1 = stmt1.executeQuery("SELECT "
                                + "eventId, name, eventlocation, eventdate, field"
                                + " From Event  WHERE eventdate < CURRENT_DATE AND organizationId ="+id);
                while(rst1.next()){
                    out.println("<div class=\"col-sm-5 col-lg-6\" style=\"margin-top: 35px;\">");
                            out.println("<div class=\"card clean-card text-center\">");
                            out.println("<div class=\"card-body info\">");
                            out.println("<h4 class=\"card-title\">"+rst1.getString(2)+"</h4>");
                            
                            out.println("<div class=\"row\">\n" +
"                        <div class=\"col\">\n" +
"                           <p>Location</p>\n" +
"                        </div>\n" +
"                        <div class=\"col\">\n" +
"                           <p class=\"text-primary\">"+rst1.getString(3)+"</p>\n" +
"                        </div>\n" +
"                     </div>");
                            
                            out.println("<div class=\"row\">\n" +
"                        <div class=\"col\">\n" +
"                           <p>Date</p>\n" +
"                        </div>\n" +
"                        <div class=\"col\">\n" +
"                           <p class=\"text-primary\">"+rst1.getDate(4)+"</p>\n" +
"                        </div>\n" +
"                     </div>");
                            out.println("<div class=\"row\">\n" +
"                        <div class=\"col\">\n" +
"                           <p>Field</p>\n" +
"                        </div>\n" +
"                        <div class=\"col\">\n" +
"                           <p class=\"text-primary\">"+rst1.getString(5)+"</p>\n" +
"                        </div>\n" +
"                     </div>");
                            out.println("</div>\n");
                            out.print("<form action='./eventpage'>");
                            out.print("<input type='hidden' name='eventId' value='" +rst1.getInt(1)+"'>");
                 out.println(" <button class=\"btn btn-primary overlay\" type=\"submit\"><i class=\"fas fa-plus\"></i>&nbsp;More details</button>\n" +
                         "</form>\n"+
"               </div>\n" +
"            </div>\n");
                }
                
                out.println("</div>");
                out.println("</div>");
                out.println("<div class=\"tab-pane fade specifications\" role=\"tabpanel\" id=\"specifications\">");
                out.println("<div class=\"row justify-content-center\" style=\"margin-top: 10px;margin-right: 0px;margin-left: 0px;\">");
                Statement stmt2 = conn.createStatement();
                ResultSet rst2 = stmt2.executeQuery("SELECT "
                                + "eventId, name, eventlocation, eventdate, field"
                                + " From Event WHERE eventdate >= CURRENT_DATE AND organizationId = "+id+";");
                while(rst2.next()){
                out.println("<div class=\"col-sm-6 col-lg-4\" style=\"margin-top: 35px;\">");
                            out.println("<div class=\"card clean-card text-center\">");
                            out.println("<div class=\"card-body info\">");
                            out.println("<h4 class=\"card-title\">"+rst2.getString(2)+"</h4>");
                            
                            out.println("<div class=\"row\">\n" +
"                        <div class=\"col\">\n" +
"                           <p>Location</p>\n" +
"                        </div>\n" +
"                        <div class=\"col\">\n" +
"                           <p class=\"text-primary\">"+rst2.getString(3)+"</p>\n" +
"                        </div>\n" +
"                     </div>");
                            
                            out.println("<div class=\"row\">\n" +
"                        <div class=\"col\">\n" +
"                           <p>Date</p>\n" +
"                        </div>\n" +
"                        <div class=\"col\">\n" +
"                           <p class=\"text-primary\">"+rst2.getDate(4)+"</p>\n" +
"                        </div>\n" +
"                     </div>");
                            out.println("<div class=\"row\">\n" +
"                        <div class=\"col\">\n" +
"                           <p>Field</p>\n" +
"                        </div>\n" +
"                        <div class=\"col\">\n" +
"                           <p class=\"text-primary\">"+rst2.getString(5)+"</p>\n" +
"                        </div>\n" +
"                     </div>");
                            out.println("</div>\n");
                            out.print("<form action='./eventpage'>");
                            out.print("<input type='hidden' name='eventId' value='" +rst2.getInt(1)+"'>");
                            out.println(" <button class=\"btn btn-primary overlay\" type=\"submit\"><i class=\"fas fa-plus\"></i>&nbsp;More details</button>\n" +
                                "</form>\n");
                            out.print("<form action='delete-event'>");
                            out.print("<input type='hidden' name='eventId' value='" +rst2.getInt(1)+"'>");
                            out.println(" <button class=\"btn btn-primary overlay\" type=\"submit\"><i class=\"fas fa-plus\"></i>&nbsp;Delete</button>\n" +
                                "</form>\n");
                            out.println("</div>\n"+"</div>\n");
                }
                out.println("</div>");
                out.println("</div>");
                out.println("<div class=\"tab-pane fade show active\" role=\"tabpanel\" id=\"reviews\">\n");
                out.println("<form method=\"post\" action='addevents'>\n" +
"                                        <div class=\"form-group\">\n" +
"                                            <section class=\"clean-block clean-form dark h-100\" style=\"background: rgb(255,255,255);\">\n" +
"                                                <div class=\"container\" style=\"background: #ffffff;\">\n" +
"                                                    <div class=\"block-heading\" style=\"padding-top: 0px;\"></div>\n" +
"                                                    <form action=\"index.html\" method=\"post\" enctype=\"multipart/form-data\" role=\"form\">\n" +
"                                                        <div class=\"form-group\"><label>Name*</label><input class=\"form-control\" type=\"tel\" placeholder=\"Event Name\" name=\"name\" required=\"\" maxlength=\"160\"></div>\n" +
"                                                        <div class=\"form-group\"><label>Description</label><input class=\"form-control\" type=\"text\" placeholder=\"Description\" name=\"Description\" required=\"\" maxlength=\"160\"></div>\n" +
"                                                        <div class=\"form-group\"><label>Location</label><input class=\"form-control\" type=\"text\" placeholder=\"Location\" name=\"eventLocation\" required=\"\" maxlength=\"100\"></div>\n" +
"                                                        <div class=\"form-group\"><label>Date*</label><input class=\"form-control\" type=\"date\" name=\"eventDate\"></div>\n" +
"                                                        <div class=\"form-group\"><label>Field*</label><select class=\"form-control states order-alpha\" id=\"stateId\" name=\"Field\" required=\"\">\n" +
"                                                                <option value=\"Healthcare\">HealthCare</option>\n" +
"                                                                <option value=\"Education\">Education</option>\n" +
"                                                                <option value=\"Children\">Children</option>\n" +
"                                                                <option value=\"Environment\">Environment</option>\n" +
"                                                                <option value=\"Women Empowerement\">Women Empowerement</option>\n" +
"                                                                <option value=\"Society\">Society</option>\n" +
"                                                            </select></div>\n" +
"                                                        <hr style=\"margin-top: 30px;margin-bottom: 10px;\">\n" +
"                                                        <div class=\"form-group\"><button class=\"btn btn-primary btn-block\" type=\"submit\"><i class=\"fas fa-plus\"></i>&nbsp;Add Event</button></div>\n" +
"                                                    </form>\n" +
"                                                </div>\n" +
"                                            </section>\n" +
"                                        </div>\n" +
"                                    </form>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
                out.println("</section>");
                out.println("</main>");
                out.println("<script src=\"https://code.jquery.com/jquery-3.4.1.slim.min.js\" integrity=\"sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n\" crossorigin=\"anonymous\"></script>\n");
                out.println("<script src=\""+request.getContextPath()+"/assets/bootstrap/js/bootstrap.min.js\"></script>");
                out.println("<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js\" integrity=\"sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6\" crossorigin=\"anonymous\"></script>");
                out.println("<script src=\"https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js\"></script>");
                out.println("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.js\"></script>");
                out.println("<script src=\"https://cdn.datatables.net/1.10.15/js/dataTables.bootstrap.min.js\"></script>");
            } 
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
            out.println("<script src=\"https://code.jquery.com/jquery-3.4.1.slim.min.js\" integrity=\"sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n\" crossorigin=\"anonymous\"></script>\n");
            out.println("<script src=\""+request.getContextPath()+"/assets/bootstrap/js/bootstrap.min.js\"></script>");
            out.println("<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js\" integrity=\"sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6\" crossorigin=\"anonymous\"></script>");
            out.println("<script src=\"https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js\"></script>");
            out.println("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.js\"></script>");
            out.println("<script src=\"https://cdn.datatables.net/1.10.15/js/dataTables.bootstrap.min.js\"></script>");
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
