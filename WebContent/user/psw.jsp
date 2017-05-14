<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%String contextPath = request.getContextPath();  %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title>修改密码</title>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/font-awesome.min.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/bootstrap.min.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/common.css"/>
		<style type="text/css">
			.form {
				width: 400px;
				margin: 0 auto;
			}
			.table>thead>tr>th, .table>tbody>tr>th, .table>tfoot>tr>th, .table>thead>tr>td, .table>tbody>tr>td, .table>tfoot>tr>td {
			    border-top: none;
			}
			h2 {
				margin-bottom: 25px;
			}
		</style>
	</head>
	<body>
		<c:if test="${requestScope.flag==true }">
			<script type="text/javascript">
				alert('${requestScope.message}');
			</script>
		</c:if>
		<div class="container">
			<div class="form">
				<h2 class="theme-color">修改密码</h2>
			
				<form action="<%=contextPath %>/NormalUserServlet?info=psw" method="post" class="form-horizontal">
					<table class="table">
						<tr>
							<td><label class="control-label">原密码</label></td>
							<td><input type="password" class="form-control" name="password" /></td>
						</tr>
						<tr>
							<td><label class="control-label">新密码</label></td>
							<td><input type="password" class="form-control" name="newPassword" /></td>
						</tr>
						<tr>
							<td colspan="2"><button class="btn btn-primary">修改</button></td>
						</tr>
					</table>
					<input type="hidden" value="${sessionScope.user.userId }" name="userId" />
					<input type="hidden" value="${sessionScope.user.userName }" name="userName" />
				</form>
			</div>
		</div>
	</body>
	<script src="<%=contextPath %>/js/jquery-3.1.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=contextPath %>/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
</html>