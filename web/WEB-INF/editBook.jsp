<%@ page import="model.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Author" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Book Page</title>
</head>

<body>
<%
    Book book = (Book) request.getAttribute("book");
    List<Author> authors = (List<Author>) request.getAttribute("author");
%>

Please update book's data:
<form action="/book/edit" method="post" enctype="multipart/form-data">
    <input type="hidden" name="bookId" value="<%=book.getId()%>"><br>
    <input type="text" name="title" value="<%=book.getTitle()%>"><br>
    <input type="text" name="description" value="<%=book.getDescription()%>"><br>
    <input type="number" name="price" value="<%=book.getPrice()%>"><br>
    <select name="authorId">
        <%
            for (Author author : authors) {
                if (author.equals(book.getAuthor())) {
        %>
        <option selected value="<%=book.getId()%>"><%=book.getTitle()%> <%=book.getPrice()%>
        </option>

        <% } else { %>
        <option value="<%=book.getId()%>"><%=book.getTitle()%> <%=book.getPrice()%>
        </option>

        <% }
        } %>

    </select><br>
    <input type="file" name="bookPic" value="<%=book.getBookPic()%>"><br>

    <input type="submit" value="Update">

</form>

</body>
</html>
