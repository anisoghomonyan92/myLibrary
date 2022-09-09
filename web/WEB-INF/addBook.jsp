<%@ page import="model.Author" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Book</title>
</head>
<body>
<%
    List<Author> authors = (List<Author>) request.getAttribute("author");
%>

Please input book's data:
<form action="/add" method="post"enctype="multipart/form-data">

    <input type="text" name="title" placeholder="please input title"><br>
    <input type="text" name="description" placeholder="please input description"><br>
    <input type="number" name="price" placeholder="please input price"><br>
    <select name="authorId">
        <%
            for (Author author : authors) {

        %>
        <option value="<%=author.getId()%>"><%=author.getName()%> <%=author.getSurname()%> <%=author.getEmail()%>
        </option>

        <% }%>

    </select><br>
    Book Profile Picture:
    <input type="file" name="profilePic">


    <input type="submit" value="Add">

</form>


</body>
</html>
