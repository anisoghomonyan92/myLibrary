<%@ page import="model.Book" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Books page</title>
</head>
<body>

<%
    List<Book> books = (List<Book>) request.getAttribute("book");
%>

<table border="1">
    <tr> <th>image</th>
        <th>id</th>
        <th>title</th>
        <th>description</th>
        <th>price</th>
        <th>author_id</th>
        <th>book picture</th>
        <th>option</th>


    </tr>

    <%
        for (Book book : books) {
    %>
    <tr>
        <td><% if(book.getBookPic() == null || book.getBookPic().length()==0) { %>
            <img src="/image/defaultProfilePic.png/The-Ukrainian-Book-Institute-Purchases-380.9-Thousand-Books-for-Public-Libraries1.jpeg"width="100" />
            <% } else {%>
            <img src="/getImage?profilePic=<%=book.getBookPic()%>"width="100" />

            <% }%>
        </td>
        <td><%=book.getId()%>
        </td>
        <td><%=book.getTitle()%>
        </td>
        <td><%=book.getDescription()%>
        </td>
        <td><%=book.getPrice()%>
        </td>
        <td>
            <% if (book.getAuthor() != null) {%>
            <%=book.getAuthor().getName()%>
            <% } else {%>
            <snap style="color: red">there is no author</snap>
            <% } %>
        </td>
        <td><%=book.getBookPic()%>
        </td>

        <td>
            <a href="/book/remove?bookId=<%=book.getId()%>">Remove</a> |
            <a href="/book/edit?bookId=<%=book.getId()%>">Edit</a>

        </td>

    </tr>


    <% } %>
</table>

</body>
</html>
