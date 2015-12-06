<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/header.jsp" %>
<html>
	<head>
		<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
		<script type="text/javascript">
			jQuery(function() {
				var url = "${pageContext.request.contextPath}/test/receive/array.do";
				var param = {id: 1};
				for (var i=0; i<259; i++) {
					param['roles['+i+'].id'] = i;
					param['roles['+i+'].name'] = 'role'+i;
				}
				jQuery.post(url, param, function(data) {
					alert(data);
				});
			});
		</script>
	</head>
	<body>
		<h2>Hello World!</h2>
		<ul>
			<li><a href="spring_security_login">default Login page</a></li>
			<li><a href="login.jsp">self define login page</a></li>
			<li><a href="login_success.jsp">login success</a></li>
	<sec:authorize access="hasRole('admin')">
		<a href="admin.jsp">admin page</a>
	</sec:authorize>			
		</ul>
	<!-- 将获取到的用户名以属性名username存放在session中 -->
	<sec:authentication property="principal.username" scope="session" var="username"/>
	${username }
			<div>xxxxxxxxx</div>
		
		<hr/>
	<sec:authentication property="principal.username"/>
		<hr/>
	<sec:authentication property="name" />
		<hr/>
		hello<br/>
		${username }
	
	<!-- 需要拥有所有的权限 -->
	<sec:authorize ifAllGranted="ROLE_ADMIN">
		<a href="admin.jsp">admin</a>
	</sec:authorize>
	<!-- 只需拥有其中任意一个权限 -->
	<sec:authorize ifAnyGranted="ROLE_USER,ROLE_ADMIN">hello</sec:authorize>
	<!-- 不允许拥有指定的任意权限 -->
	<sec:authorize ifNotGranted="ROLE_ADMIN">
		<a href="user.jsp">user</a>
	</sec:authorize>
	<hr/>
	<!-- 拥有访问指定url的权限才显示其中包含的内容 -->
	<sec:authorize url="/admin.jsp">
		<a href="admin.jsp">admin</a>
	</sec:authorize>
	<sec:authorize url="/index.jsp">
		<a href="index.jsp">index</a>
	</sec:authorize>
	<hr/>
	<sec:authorize access="isFullyAuthenticated()" var="isFullyAuthenticated">
		只有通过登录界面进行登录的用户才能看到1。<br/>
	</sec:authorize>
	上述权限的鉴定结果是：${isFullyAuthenticated }<br/>
	<%if((Boolean)pageContext.getAttribute("isFullyAuthenticated")) {%>
		只有通过登录界面进行登录的用户才能看到2。
	<%}%>
	</body>
</html>
