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
		<title>基本设置</title>
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
				<h2 class="theme-color">基本设置</h2>
			
				<form action="<%=contextPath %>/NormalUserServlet?info=set" method="post" class="form-horizontal">
					<table class="table">
						<tr>
							<td><label class="control-label">昵称</label></td>
							<td><input type="text" class="form-control" name="userNickname" /></td>
						</tr>
						<tr>
							<td><label class="control-label">性别</label></td>
							<td>
								<label class="radio-inline">
									<input type="radio" name="sex" value="1" />男
								</label>
								<label class="radio-inline">
									<input type="radio" name="sex" value="2" />女
								</label>
								<label class="radio-inline">
									<input type="radio" name="sex" value="0" checked />保密
								</label>
							</td>
						</tr>
						<tr>
							<td colspan="2"><button class="btn btn-primary">保存</button></td>
							<!--<td></td>-->
						</tr>
					</table>
					<input type="hidden" name="userId" value="${sessionScope.user.userId }" />
				</form>
			</div>
		</div>
	</body>
	<script src="<%=contextPath %>/js/jquery-3.1.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=contextPath %>/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
</html>