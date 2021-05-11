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
 * @author mac
 */
@WebServlet(name = "VolunteerInfo", urlPatterns = {"/volunteerinfo"})
public class VolunteerInfo extends HttpServlet {
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
            HttpSession session = request.getSession();
            int id = (int)session.getAttribute("id");
            int volunteerid = Integer.parseInt(request.getParameter("volunteerId").replace("/", ""));
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Montserrat:400,400i,700,700i,600,600i\">");
            out.println("<link rel=\"stylesheet\" href=\""+request.getContextPath()+"/assets/bootstrap/css/bootstrap.min.css\">");
            out.println("<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.css\">");
            out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
            out.println("<title>Volunteer Information</title>");            
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
                out.println(" <main class=\"page product-page\">");
                out.println("<section class=\"clean-block clean-product dark\">");
                out.println("<div class=\"container\" style=\"height: 100vh\">");
                out.println("<div class=\"block-heading\">\n" +
"                  <h2 class=\"text-info\">Volunteer Information</h2>\n" +
"               </div>");
                out.println("<div class=\"block-content\">");
                out.println("<div class=\"product-info\">");
                out.println("<div>");
                out.println("<ul class=\"nav nav-tabs\" role=\"tablist\" id=\"myTab\">\n" +
"                           <li class=\"nav-item\" role=\"presentation\"><a class=\"nav-link\" role=\"tab\" data-toggle=\"tab\" id=\"description-tab\" href=\"#description\">Profile</a></li>\n" +
"                           <li class=\"nav-item\" role=\"presentation\"><a class=\"nav-link\" role=\"tab\" data-toggle=\"tab\" id=\"specifications-tabs\" href=\"#specifications\">Availabilities</a></li>\n" +
"                           <li class=\"nav-item\" role=\"presentation\"><a class=\"nav-link active\" role=\"tab\" data-toggle=\"tab\" id=\"reviews-tab\" href=\"#reviews\">Reviews</a></li>\n" +
"                        </ul>");
                out.println("<div class=\"tab-content\" id=\"myTabContent\">");
                out.println("<div class=\"tab-pane fade description\" role=\"tabpanel\" id=\"description\">");
                out.println("<table id=\"example\" class=\"table table-striped table-bordered\" cellspacing=\"0\" width=\"100%\">");
                out.println("<thead>\n" +
"                                    <tr>\n" +
"                                       <th>First Name</th>\n" +
"                                       <th>Last Name</th>\n" +
"                                       <th>Birthdate</th>\n" +
"                                       <th>Gender</th>\n" +
"                                       <th>Address</th>\n" +
"                                    </tr>\n" +
"                                 </thead>");
                out.println("<tbody>");
                Statement stmt1 = conn.createStatement();
                ResultSet rst1 = stmt1.executeQuery("SELECT * FROM Volunteer WHERE volunteerId ="+volunteerid+";" );
                if(rst1.next()){
                    out.println("<tr>\n" +
"                                       <td>"+rst1.getString(2)+"</td>\n" +
 "                                      <td>"+rst1.getString(3)+"</td>\n" +
"                                       <td>"+rst1.getDate(4)+"</td>\n" +
                                   "    <td>"+rst1.getString(5)+"</td>\n" +
                                      " <td>"+rst1.getString(6)+"</td>");
                }
                else{
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Volunteer does not exist');");
                out.println("location='volunteers';");
                out.println("</script>");
                }
                out.println("</tbody>");
                out.println("</table>");
                out.println("</div>");
                out.println("<div class=\"tab-pane fade specifications\" role=\"tabpanel\" id=\"specifications\">");
                out.println("<table id=\"example\" class=\"table table-striped table-bordered\" cellspacing=\"0\" width=\"100%\">");
                out.println("<thead>\n" +
"                                    <tr>\n" +
"                                       <th>Date</th>\n" +
"                                       <th>Status</th>\n" +
"                                    </tr>\n" +
"                                 </thead>");
                 out.println("<tbody>");
                Statement stmt2 = conn.createStatement();
                ResultSet rst2 = stmt2.executeQuery("SELECT availabilityDate,availabilityStatus FROM Availability"
                        + " WHERE volunteerId =  "+volunteerid+  ";" );
                while(rst2.next()){
                out.println("<tr>\n" +
"                                       <td>"+rst2.getDate(1)+"</td>\n" +
"                                       <td>"+rst2.getString(2)+"</td>\n" +
"                                    </tr>");
                }
                out.println("</tbody>");
                out.println("</table>");
                out.println("</div>");
                out.println("<div class=\"tab-pane fade show active\" role=\"tabpanel\" id=\"reviews-tab\">");
                out.println("<table id=\"example\" class=\"table table-striped table-bordered\" cellspacing=\"0\" width=\"100%\">");
                out.println("<thead>\n" +
"                                    <tr>\n" +
"                                       <th>Organization</th>\n" +
"                                       <th>Rating</th>\n" +
                                       "<th>Review</th>\n" +
"                                    </tr>\n" +
"                                 </thead>");
                 out.println("<tbody>");
                Statement stmt3 = conn.createStatement();
                ResultSet rst3 = stmt3.executeQuery("SELECT organizationId,volunteerRating,comment FROM VolunteerEndorsement"
                        + " WHERE volunteerId= " +volunteerid+ ";");
                while(rst3.next()){
                out.println("<tr>\n" +
"                                      <td>"+rst3.getInt(1)+"</td>\n" +
"                                      <td>"+rst3.getInt(2)+"</td>\n" +
"                                      <td>"+rst3.getString(3)+"</td>\n" +
"                                    </tr>");
                }
                out.println("</tbody>");
                out.println("</table>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
                //out.println("</div>");
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