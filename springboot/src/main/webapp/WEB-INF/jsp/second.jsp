<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
Hello Second Jsp.
<br/>
${message}
<br/>
<ul>
  列表元素如下：
  <c:forEach items="${list}" var="item">
    <li>${item}</li>
  </c:forEach>
</ul>
</body>
<head>
  <title>Second Jsp</title>
</head>
</html>
