<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录页面</title>
</head>
<body>
	<center>
		<form action="" method="post">
			<table style="border-color: #cdf; border-collapse: collapse;" cellpadding="1" cellspacing="0" border="1">
				<tr>
					<td>用户名：</td>
					<td><input type="text" name="username"/></td>
				</tr>
				<tr>
					<td>密码：</td>
					<td><input type="password" name="password"/></td>
				</tr>
				<tr>
					<td style="border-right: none;"></td>
					<td style="border-left: none;"><input type="checkbox" name="rememberMe" value="true"/>记住我</td>
				</tr>
				<tr>
					<td align="center" colspan="2">
						<input type="submit" value="登录"/>
					</td>
				</tr>
			</table>
		</form>
	</center>
</body>
</html>