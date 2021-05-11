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
@WebServlet(name = "FilterVolunteers", urlPatterns = {"/filtervolunteers"})
public class FilterVolunteers extends HttpServlet {

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
            out.println("<title>Volunteers</title>");            
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
                    HttpSession session = request.getSession();
                    out.println("<nav class=\"navbar navbar-light navbar-expand-lg fixed-top bg-white clean-navbar\">\n" +
                        "         <div class=\"container\">\n" +
                        "            <a class=\"navbar-brand logo\" href=\"#\">MoroccoVolunteers</a><button data-toggle=\"collapse\" class=\"navbar-toggler\" data-target=\"#navcol-1\"><span class=\"sr-only\">Toggle navigation</span><span class=\"navbar-toggler-icon\"></span></button>\n" +
                        "            <div class=\"collapse navbar-collapse\" id=\"navcol-1\">\n" +
                        "               <ul class=\"navbar-nav ml-auto\">\n" +
                        "                  <li class=\"nav-item\"><a class=\"nav-link\" href=\"organization.jsp\">Profile</a></li>\n" +
                        "                  <li class=\"nav-item\"><a class=\"nav-link\" href=\"\">Events</a></li>\n" +
                        "                  <li class=\"nav-item\"><a class=\"nav-link\" href=\"volunteers\">Volunteers</a></li>\n" +
                        "                  <li class=\"nav-item\"><a class=\"nav-link\" href=\"applications\">Applications</a></li>\n" +
                        "                  <li class=\"nav-item\"><a class=\"nav-link\" href=\"logout\">Logout</a></li>\n" +
                        "               </ul>\n" +
                        "            </div>\n" +
                        "         </div>\n" +
                        "      </nav>");
                    out.println("<main class=\"page catalog-page\">");
                    out.println("<section class=\"clean-block clean-catalog dark\">");
                    out.println("<div class=\"container\"  style=\"height: 100vh\">");
                    out.println("<div class=\"block-heading\">\n" +
                "                    <h2 class=\"text-info\">Volunteers</h2>\n" +
                "                </div>");
                    out.println("<div class=\"content\">");
                    out.println("<div class=\"row\">");
                    out.println("<div class=\"col-md-3\" style=\"margin-top: 0px;\">");
                    out.println("<div class=\"d-none d-md-block\">");
                    out.println("<div class=\"filters\">");
                    out.println("<div class=\"filter-item\">");
                    out.println("<h3>Filter by availability</h3>\n" +
"                                        <div class=\"text-center\">\n" +
"                                            <form action = 'filtervolunteers'><select class=\"form-control\" style=\"height: 32px;padding-top: 8px;padding-bottom: 3px;font-size: 12px;width: 102%;margin-right: 2px;margin-bottom: 0px;margin-left: -9px;\" name = 'filtervalue'>\n"+
"                                                    <option value=\"2\" selected=\"\">All</option>\n" +
"                                                    <option value=\"1\">Available for at least 1 event</option>\n" +
"                                                    <option value=\"0\">Not available</option>\n" +
"                                                </select><button class=\"btn btn-primary\" type=\"submit\" style=\"margin-top: 10px;padding-top: 3px;color: var(--blue);background: var(--white);margin-right: 11px;margin-bottom: 0px;\">Filter</button>\n" +
"                                            </form>\n" +
"                                        </div>");
                    out.println("</div>");
                    out.println("</div>");
                    out.println("</div>");
                    out.println("</div>");
                    out.println("<div class=\"col-md-9\">");
                    out.println("<div class=\"volunteers\">");
                    out.println("<div class=\"row padMar\">\n" +
"                                    <div class=\"col padMar\">\n" +
"                                        <form action=\"searchvolunteer\">\n" +
"                                            <div class=\"input-group\">\n" +
"                                                <div class=\"input-group-prepend\"></div><input class=\"form-control autocomplete\" type=\"text\" placeholder=\"Search by volunteer's name\" name=\"searchfield\">\n" +
"                                                <div class=\"form-group\"><button class=\"btn btn-primary btn-block\" type=\"submit\" style=\"background: var(--white);color: var(--blue);width: 100px;\">search</button></div>\n" +
"                                            </div>\n" +
"                                        </form>\n" +
"                                    </div>\n" +
"                                </div>");
                    out.println("<div class=\"row no-gutters\">");
                    Statement stmt = conn.createStatement();
                    int filtervalue = Integer.parseInt(request.getParameter("filtervalue"));
                    int id = (int) session.getAttribute("id");
                    String query = "";
                    switch (filtervalue) {
                        case 0:
                            query = "SELECT V.volunteerID, firstname, lastname, \n" +
                                    "CAST(COALESCE(AVG(volunteerRating),0) AS decimal(8,2))\n" +
                                    "FROM Volunteer AS V LEFT JOIN VolunteerEndorsement AS VE\n" +
                                    "ON V.volunteerId= VE.volunteerId\n" +
                                    "Group By V.volunteerID,firstname, lastname\n" +
                                    "EXCEPT\n" +
                                    "SELECT V.volunteerID, firstname, lastname,\n" +
                                    "CAST(COALESCE(AVG(volunteerRating),0) AS decimal(8,2))\n" +
                                    "FROM Volunteer NATURAL JOIN Availability AS V LEFT JOIN VolunteerEndorsement AS VE ON V.volunteerId= VE.volunteerId \n" +
                                    "WHERE availabilitydate IN(SELECT eventDate FROM Event\n" +
                                    "                              WHERE organizationId ="+id+") \n" +
                                    "AND availabilityStatus = 'free'\n" +
                                    "Group By V.volunteerID,firstname, lastname;";
                            break;
                        case 1:
                            query = "SELECT V.volunteerID, firstname, lastname,\n" +
                                    "CAST(COALESCE(AVG(volunteerRating),0) AS decimal(8,2))\n" +
                                    "FROM Volunteer NATURAL JOIN Availability AS V LEFT JOIN VolunteerEndorsement AS VE ON V.volunteerId= VE.volunteerId \n" +
                                    "WHERE availabilitydate IN(SELECT eventDate FROM Event\n" +
                                    "                              WHERE organizationId ="+id+") \n" +
                                    "AND availabilityStatus = 'free'\n" +
                                    "Group By V.volunteerID,firstname, lastname;";
                            break;
                        default:
                            response.sendRedirect("volunteers");
                            break;
                    }
                    ResultSet rst = stmt.executeQuery(query);
                    
                    while (rst.next()) {
                        out.print("<div class=\"col-12 col-md-6 col-lg-4\">");
                        out.println("<div class=\"clean-product-item\">");
                        out.println("<div class=\"product-name\"><a href=\"volunteerinfo?volunteerId="+rst.getInt(1)+"\">"+rst.getString(2)+" "+rst.getString(3)+"</a></div>");
                        out.println("<div class=\"about\">\n" +
"                                                <div class=\"price\">\n" +
"                                                    <h5>Rating:" +rst.getDouble(4)+"/10</h5>\n" +
"                                                </div>\n" +
"                                            </div>");
                        out.println("</div>");
                        out.println("</div>");
                    }
                    conn.close();
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
                    else {
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
