<%@page import="org.apache.shiro.subject.Subject"%>
<%@page import="org.apache.shiro.SecurityUtils"%>
<html>
<%
	Subject subject = SecurityUtils.getSubject();
%>
<body>
<h2>Hello World!</h2>
<%=subject %><br/>
<%=subject.getPrincipal() %><br/>
<%=subject.getSession()%><br/>
<%=subject.isAuthenticated() %>
</body>
</html>
