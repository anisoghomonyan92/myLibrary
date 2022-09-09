<%@ page import="model.Book" %>
<%@ page import="model.Author" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Author Page</title>
</head>

<body>
<%
    Author author = (Author) request.getAttribute("author");
    List<Book> book = (List<Book>) request.getAttribute("book");


%>
Please update author's data:
<form action="/author/edit" method="post" enctype="multipart/form-data">
    <input type="hidden" name="authorId" value="<%=author.getId()%>"><br>
    <input type="text" name="name" value="<%=author.getName()%>"><br>
    <input type="text" name="surname" value="<%=author.getSurname()%>"><br>
    <input type="email" name="email" value="<%=author.getEmail()%>"><br>
    <input type="number" name="age" value="<%=author.getAge()%>"><br>
    <input type="file" name="profilePic" value="<%=author.getProfilePic()%>"><br>

    <input type="submit" value="Update">

</form>


</body>
</html>
