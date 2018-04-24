<%@ include file="/WEB-INF/view/jspf/taglib.jspf" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form:form method="get" modelAttribute="loginUser" action="${contextPath}/user/checkLogin">
    <form:errors path="email"/><br>
    email:<form:input path="email" name="email" type="text"/><br>
    password:<form:input path="password" name="password" type="text"/>
    <input type="submit" value="submit">
</form:form>
</body>
</html>
