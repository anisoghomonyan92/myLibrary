<%@ page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Library</title>
</head>

<%
    User user = (User) session.getAttribute("user");
%>


<body>
<a href="/authors">Show All Authors</a>
<a href="/books">Show All Books</a>

<%
    if (user != null) {
%>
<a href="/authorAdd">Add Author</a>
<a href="/book">Add Book</a>
<a href="/logout">Logout</a>
<% } else { %>
<a href="/register">Register</a>
<a href="/login">Login</a>
<% } %>

</body>
</html>
