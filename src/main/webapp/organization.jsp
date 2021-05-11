<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page language = "java" contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Organization menu page</title>
        <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat:400,400i,700,700i,600,600i">
        <link rel="stylesheet" href="assets/fonts/simple-line-icons.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.css">
        
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <%
        
        response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
        if(session.getAttribute("role") == null){
            response.sendRedirect("index.html");
        }
        else{
            if(session.getAttribute("role").equals("Volunteer")){
                response.sendRedirect("volunteer.jsp");
            }
        }
        
    %>
    <body>
        <nav class="navbar navbar-light navbar-expand-lg fixed-top bg-white clean-navbar">
            <div class="container"><a class="navbar-brand logo" href="#">MoroccoVolunteers</a><button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
                <div class="collapse navbar-collapse" id="navcol-1">
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item"><a class="nav-link" href="organization.jsp">Profile</a></li>
                        <li class="nav-item"><a class="nav-link" href="orgevents">Events</a></li>
                        <li class="nav-item"><a class="nav-link" href="applications">applications</a></li>
                        <li class="nav-item"><a class="nav-link" href="volunteers">volunteers</a></li>
                        <li class="nav-item"><a class="nav-link" href="logout">logout</a></li>
                    </ul>
                </div>
            </div>
        </nav>
            
        <h1>MoroccoVolunteer</h1>
        <%try {
                
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
                String qry = "SELECT * FROM vwOrgWithAvgRate WHERE organizationId = (?);";
                // create the insert preparedstatement               
                PreparedStatement prepStmt = conn.prepareStatement(qry);
                int id = (int)session.getAttribute("id");
                prepStmt.setInt(1, id);
                ResultSet rst1 = prepStmt.executeQuery();
                rst1.next();
                String name = rst1.getString(2);
                String description = rst1.getString(3);
                Double average_rate = rst1.getDouble(7);
                Date establishment_date = rst1.getDate(4);
                String phone_num = rst1.getString(5);
                String address = rst1.getString(6);
                
        %>
        
        <main class="page product-page">
            <section class="clean-block clean-product dark">
                <div class="container" style="height: 100vh">
                    <div class="block-heading">
                        <h2 class="text-info">Welcome <%=name%> </h2>
                    </div>
                    <div class="block-content">
                        <div class="product-info">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="info">
                                        <h3><%=name%></h3>
                                        <div class="rating">Rating:<%=average_rate%> </div>
                                        <%
                                        String qry2 = "SELECT fnEventCountsOrg (?)";
                                        PreparedStatement prepStmt2 = conn.prepareStatement(qry2);
                                        prepStmt2.setInt(1, id);
                                        ResultSet rst2 = prepStmt.executeQuery();
                                        rst2.next();
                                        %>
                                        <div class="price">
                                            <h3>Events Organized in the past : <%=rst2.getInt(1)%></h3>
                                        </div>
                                        <div class="summary">
                                            <p><%=description%></p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="product-info">
                            <div>
                                <ul class="nav nav-tabs" role="tablist" id="myTab">
                                    <li class="nav-item" role="presentation"><a class="nav-link active" role="tab" data-toggle="tab" id="description-tab" href="#description">Requests</a></li>
                                    <li class="nav-item" role="presentation"><a class="nav-link" role="tab" data-toggle="tab" id="specifications-tabs" href="#specifications">Details</a></li>
                                    <li class="nav-item" role="presentation"><a class="nav-link" role="tab" data-toggle="tab" id="reviews-tab" href="#reviews">Reviews</a></li>
                                </ul>
                                <div class="tab-content" id="myTabContent">
                                    <div class="tab-pane fade show active description" role="tabpanel" id="description">
                                        <table id="example" class="table table-striped table-bordered" cellspacing="0" width="100%">
                                        <thead>
                                            <tr>
                                                <th>Event</th>
                                                <th>Position</th>
                                                <th>First Name</th>
                                                <th>Last Name</th>
                                                <th>Status</th>
                                                <th>Cancel</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%  String qry3 = "SELECT * from vwRequestWithVolunteer WHERE organizationId = (?)";
                                                PreparedStatement prepStmt3 = conn.prepareStatement(qry3);
                                                prepStmt3.setInt(1, id);
                                                ResultSet rst3 = prepStmt3.executeQuery();
                                                while(rst3.next()){%>
                                            <tr>
                                                <td><%=rst3.getString(2)%></td>
                                                <td><%=rst3.getString(3)%></td>
                                                <td><%=rst3.getString(4)%></td>
                                                <td><%=rst3.getString(5)%></td>
                                                <td><%=rst3.getString(6)%></td>
                                                <% if(rst3.getString(5).equals("pending")){%>
                                                   <td><a class="nav-link" href="logout">cancel</a></li></td> 
                                                <%}else{%>
                                                <<td></td>
                                                <%}%>
                                            </tr>
                                            <%}%>
                                        </tbody>
                                    </table>
                                    </div>
                                    <div class="tab-pane fade specifications" role="tabpanel" id="specifications">
                                        <div class="table-responsive table-bordered">
                                            <table class="table table-bordered">
                                                <tbody>
                                                    <tr>
                                                        <td class="stat">Name</td>
                                                        <td><%=name%></td>
                                                    </tr>
                                                    <tr>
                                                        <td class="stat">Establishment Date</td>
                                                        <td><%=establishment_date%></td>
                                                    </tr>
                                                    <tr>
                                                        <td class="stat">Phone Number</td>
                                                        <td><%=phone_num%></td>
                                                    </tr>
                                                    <tr>
                                                        <td class="stat">Address</td>
                                                        <td><%=address%></td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                    <div class="tab-pane fade" role="tabpanel" id="reviews">
                                        <%  String qry4 = "SELECT * from vwratingswithvolunteerauthor WHERE organizationId = (?)";
                                                PreparedStatement prepStmt4 = conn.prepareStatement(qry4);
                                                prepStmt4.setInt(1, id);
                                                ResultSet rst4 = prepStmt4.executeQuery();
                                                while(rst4.next()){%>
                                                <div class="reviews">
                                                    <div class="review-item">
                                                        <div class="rating"><%=rst4.getInt(4)%>/10</div>
                                                        <span class="text-muted"><a href="#"><%=rst4.getString(2)+ " "+rst4.getString(3)%></a></span>
                                                        <%
                                                        if(rst4.getString(5) == null ||rst4.getString(5).length()==0 ){
                                                        %>
                                                        <p>No Comment</p>
                                                        <%}else{%>
                                                        <p><%=rst4.getString(5)%></p>
                                                        <%}%>
                                                    </div>
                                                </div>
                                        <%}%>
                                    </div>
                                </div>
                                    
                                </div>
                            </div>
                        </div>
                  </div>
            
            </section>
    </main>
        <%} 
        }
    }
           catch (SQLException ex) {
           out.println("SQLException: " + ex);
           }
            catch (Exception e) {
            //e.printStackTrace();
            out.println("Exception caught");
            out.println(e.toString());
            }%>
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    </body>
</html>
