
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Author</title>
</head>
<body>
Please input author's data:
<form action="/authorAdd" method="post" enctype="multipart/form-data">

    <input type="text" name="name" placeholder="please input name"><br>
    <input type="text" name="surname" placeholder="please input surname"><br>
    <input type="email" name="email" placeholder="please input email"><br>
    <input type="number" name="age" placeholder="please input age"><br>
    Profile Picture:
    <input type="file" name="profilePic">

    <input type="submit" value="Add">

</form>

</body>
</html>
