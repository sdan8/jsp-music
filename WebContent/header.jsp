<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% String contextPath = request.getContextPath(); %>

<header class="theme-bg-color">
	<nav class="navbar navbar-default">
		<div class="container theme-bg-color">
			<!--切换为折叠样式以适应小屏幕-->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse" aria-expanded="false">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a href="#" class="navbar-brand"><img src="img/logo_2.png" alt="music"/></a>
			</div><!-- /.navbar-header -->
			<div class="collapse navbar-collapse" id="navbar-collapse">
				<ul class="nav navbar-nav">
					<li><a href="index.jsp"><span class="fa fa-home"></span>&nbsp;首页</a></li>
					<li><a href="<%=contextPath %>/SingerServlet?info=list"><span class="fa fa-microphone"></span>&nbsp;歌手</a></li>
					<li><a href="<%=contextPath %>/AlbumServlet?info=list"><span class="fa fa-th-list"></span>&nbsp;专辑</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<c:if test="${user==null}">
						<li><a href="<%=contextPath%>/login.jsp">登录</a></li>
					</c:if>
					<c:if test="${user!=null}">
						<li><a href="<%=contextPath %>/NormalUserServlet?info=manager"><span class="fa fa-user"></span>&nbsp;${user.userNickname }</a></li>
						<li><a href="<%=contextPath %>/NormalUserServlet?info=logout">退出</a></li>
					</c:if>
				</ul>
			</div><!-- /.navbar-collapse -->
		</div><!-- /.container -->
	</nav>
</header>