<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page language = "java" contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
        <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat:400,400i,700,700i,600,600i">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Droid+Serif">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
        <title>Leaderboard</title>
    </head>
    <body>
        <nav class="navbar navbar-light navbar-expand-lg fixed-top bg-white clean-navbar">
            <div class="container"><a class="navbar-brand logo" href="#">MoroccoVolunteers</a><button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
                <div class="collapse navbar-collapse" id="navcol-1">
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item"><a class="nav-link active" href="index.html">Home</a></li>
                        <li class="nav-item"><a class="nav-link active" href="Leaderboard.jsp">Leaderboard</a></li>
                        <li class="nav-item"><a class="nav-link" href="signup.html">Sign up</a></li>
                        <li class="nav-item"><a class="nav-link" href="login.html">Login</a></li>
                    </ul>
                </div>
            </div>
            
        </nav>
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
                String qry = "SELECT * FROM vwBstorganizations;";
                // create the insert preparedstatement               
                PreparedStatement prepStmt = conn.prepareStatement(qry);
                ResultSet rst1 = prepStmt.executeQuery();
                
        %>
        <main class="page">
            
            <section class="clean-block about-us">
                <div class="container" style="height: 100vh">
                    <div class="block-heading">
                        <h2 class="text-info">Leaderboard</h2>
                        <p>Here, we honor our most active members. Although, by being here and serving a purpose, we know that we are all winners after all.</p>
                    </div>
                    <%while(rst1.next()){
                    String o_name = rst1.getString(1);
                    double o_rating = rst1.getDouble(2); %>
                    <div class="row justify-content-center">
                        <div class="col-sm-6 col-lg-4">
                            <div class="card text-center clean-card"><img class="card-img-top w-100 d-block" src="assets/img/avatars/avatar1.jpg">
                                <div class="card-body info">
                                    <h4 class="card-title">Best Organization</h4>
                                    <h4 class="card-title"><%=o_name%></h4>
                                    <h4 class="card-title">Rating:<%=o_rating%></h4>
                                    <p class="card-text"></p>
                                    <p class="card-text">This trophy is given to the organization that treats their volunteers the best and marks their experience<br></p>
                                </div>
                            </div>
                        </div>
                        <%}%>
                        <%
                            Statement stmt = conn.createStatement();
                            ResultSet rst2 = stmt.executeQuery("SELECT * FROM vwBstvolunteers;");
                            while(rst2.next()){
                                String v_name = rst2.getString(1) + " " + rst2.getString(2);
                                double v_rating = rst2.getDouble(3);
                            
                        %>
                        <div class="col-sm-6 col-lg-4">
                            <div class="card text-center clean-card"><img class="card-img-top w-100 d-block" src="assets/img/avatars/avatar2.jpg">
                                <div class="card-body info">
                                    <h4 class="card-title">Best Volunteer</h4>
                                    <h4 class="card-title"><%=v_name%></h4>
                                    <h4 class="card-title">Rating: <%=v_rating%></h4>
                                    <p class="card-text">This trophy is given to those who were most passionate in their volunteering.<br></p>
                                </div>
                            </div>
                        </div>
                        <%}%>
                        <%
                            Statement stmt2 = conn.createStatement();
                            ResultSet rst3 = stmt.executeQuery("SELECT * FROM vwMostPopularField;");
                            while(rst3.next()){
                                String field = rst3.getString(1);
                                int count = rst3.getInt(2);
                            
                        %>
                        <div class="col-sm-6 col-lg-4">
                            <div class="card text-center clean-card"><img class="card-img-top w-100 d-block" src="assets/img/avatars/avatar3.jpg">
                                <div class="card-body info">
                                    <h4 class="card-title">Most popular field</h4>
                                    <h4 class="card-title"><%=field%></h4>
                                    <h4 class="card-title">Number of past events: <%=count%></h4>
                                    <p class="card-text">This trophy is given to the field that our organizations focus on the most.</p>
                                </div>
                            </div>
                        </div>
                        <%}%>
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
    </body>
    <script src="assets/js/theme.js"></script>
</html>
