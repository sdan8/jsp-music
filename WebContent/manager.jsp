<%@page import="music.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String contextPath = request.getContextPath();
request.setAttribute("login_success", Constant.LOGIN_SUCCESS);
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<title>后台管理</title>
	<link rel="stylesheet" type="text/css" href="css/font-awesome.min.css"/>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" type="text/css" href="css/common.css"/>
	<style type="text/css">
		.main {
			padding-top: 20px;
		}
	</style>
</head>
<body>
	<%-- 检测是否登录帐号 --%>
	<c:if test="${sessionScope.admin_login_flag!=login_success}">
		<%
		
		response.sendRedirect("admin.jsp");
		%>
	</c:if>
	<jsp:include page="adminheader.jsp"></jsp:include>
	<section class="main">
		<!-- 侧边栏 -->
		<div class="sidebar col-md-2">
			<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
				<div class="panel panel-success">
					<!-- 折叠标题 -->
					<!-- 用户管理 -->
					<div class="panel-heading" role="tab" id="headingOne">
						<h4 class="panel-title">
							<a href="#collapseUser" role="button" data-toggle="collapse" data-parent="#accordion" aria-expanded="true" aria-controls="collapseUser">用户管理&nbsp;<span class="fa fa-angle-double-down pull-right"></span></a>
						</h4>
					</div>
					<!-- 折叠标题结束 -->
					<!-- 折叠内容 -->
					<div id="collapseUser" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
						<div class="list-group">
							<a href="admin/ban.jsp" target="iframe" class="list-group-item active">封禁用户</a>
							<a href="admin/newAdmin.jsp" target="iframe" class="list-group-item">添加管理员</a>
						</div>
					</div>
					<!-- 折叠内容结束 -->
				</div>
				<div class="panel panel-success">
					<!-- 折叠标题 -->
					<!-- 网站管理 -->
					<div class="panel-heading" role="tab" id="headingTwo">
						<h4 class="panel-title">
							<a href="#collapseSite" role="button" data-toggle="collapse" data-parent="#accordion" aria-expanded="true" aria-controls="collapseSite">网站管理&nbsp;<span class="fa fa-angle-double-down pull-right"></span></a>
						</h4>
					</div>
					<!-- 折叠标题结束 -->
					<!-- 折叠内容 -->
					<div id="collapseSite" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
						<div class="list-group">
							<a href="admin/recommendForward.jsp" target="iframe" class="list-group-item">推荐歌曲</a>
							<a href="admin/newSongForward.jsp" target="iframe" class="list-group-item">新歌上架</a>
							<a href="admin/singermgrForward.jsp" target="iframe" class="list-group-item">歌手管理</a>
							<a href="admin/albummgrForward.jsp" target="iframe" class="list-group-item">专辑管理</a>
							<a href="admin/songmgrForward.jsp" target="iframe" class="list-group-item">歌曲管理</a>
						</div>
					</div>
					<!-- 折叠内容结束 -->
				</div>
				<div class="panel panel-success">
					<!-- 折叠标题 -->
					<!-- 帐号管理 -->
					<div class="panel-heading" role="tab" id="headingThree">
						<h4 class="panel-title">
							<a href="#collapseAccount" role="button" data-toggle="collapse" data-parent="#accordion" aria-expanded="true" aria-controls="collapseAccount">帐号管理&nbsp;<span class="fa fa-angle-double-down pull-right"></span></a>
						</h4>
					</div>
					<!-- 折叠标题结束 -->
					<!-- 折叠内容 -->
					<div id="collapseAccount" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
						<div class="list-group">
							<a href="admin/psw.jsp" target="iframe" class="list-group-item">修改密码</a>
						</div>
					</div>
					<!-- 折叠内容结束 -->
				</div>
			</div>
		</div>
		<!-- 侧边栏结束 -->
		<!-- 右侧内容 -->
		<div class="content col-md-10">
			<div class="iframe">
				<div class="embed-responsive embed-responsive-16by9">
					<iframe class="embed-responsive-item" name="iframe" src="admin/ban.jsp">您的浏览器版本过低，请升级新版本浏览器！</iframe>
				</div>
			</div>
		</div>
		<!-- 右侧内容结束 -->
	</section>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
	<script src="js/jquery-3.1.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		var taga = $(".list-group a");
		taga.on('click', function (){
			taga.removeClass("active");
			$(this).addClass("active");
		});
	</script>
</html>