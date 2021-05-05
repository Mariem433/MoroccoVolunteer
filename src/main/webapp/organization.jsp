

<%@page language = "java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Organization menu page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <%
        response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
        if(session.getAttribute("id") == null){
            response.sendRedirect("index.html");
        }
        if(session.getAttribute("role").equals("Volunteer")){
            response.sendRedirect("volunteer.jsp");
        }
    %>
    <body>
        <h1>MoroccoVolunteer</h1>
        <div>
            <h2>Welcome</h2>
            <p>Click <a href="ManageEvents.html">Events</a> to add, delete and update information about your events</p>
            <p>Click <a href="ManageApplications.html">Applications</a> to accept/refuse pending applications</p>
            <form action ="logout">
                <p><input type="submit" value-"logout"></p>
            </form>
        </div>
    </body>
</html>
