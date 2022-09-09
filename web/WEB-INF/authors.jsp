<%@ page import="model.Author" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Authors page</title>
</head>
<body>
<%
    List<Author> authors = (List<Author>) request.getAttribute("author");
%>

<table border="1">
    <tr>
        <th>image</th>
        <th>id</th>
        <th>name</th>
        <th>surname</th>
        <th>email</th>
        <th>age</th>
        <th>profile picture</th>
        <th>action</th>

    </tr>

    <%
        for (Author author : authors) {

    %>
    <tr>
        <td><% if(author.getProfilePic() == null || author.getProfilePic().length()==0) { %>
            <img src="/image/defaultProfilePic.png/1000_F_231196474_IT7zKhOFyJCqEJr3b9huL4W0ODVWsZd0.jpg"width="100" />
            <% } else {%>
            <img src="/getImage?profilePic=<%=author.getProfilePic()%>"width="100" />

            <% }%>
        </td>
        <td><%=author.getId()%>
        </td>
        <td><%=author.getName()%>
        </td>
        <td><%=author.getSurname()%>
        </td>
        <td><%=author.getEmail()%>
        </td>
        <td><%=author.getAge()%>
        </td>
        <td><%=author.getProfilePic()%>
        </td>
        <td>
            <a href="/author/remove?authorId=<%=author.getId()%>">Remove</a> |
            <a href="/author/edit?authorId=<%=author.getId()%>">Edit</a>
        </td>
    </tr>


    <% } %>
</table>

</body>
</html>
