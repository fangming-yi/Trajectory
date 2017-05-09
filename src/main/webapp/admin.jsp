<%--
  Created by IntelliJ IDEA.
  User: fangming.yi
  Date: 2017/5/9
  Time: 17:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <title>管理员界面</title>
</head>
<body>
<form action="/admin/uploadFiles" method="post" enctype="multipart/form-data">
    TrajectoryFile:<input type="file" required="true" name="multipartFiles">
    <input type="submit" value="上传文件">
</form>
</body>
</html>
