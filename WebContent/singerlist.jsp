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
		<title>所有歌手</title>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/font-awesome.min.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/bootstrap.min.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/common.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/singerlist.css"/>
	</head>
	<body>
		<c:if test="${requestScope.flag==true }">
			<script type="text/javascript">
				alert('${requestScope.message}');
			</script>
		</c:if>
		<jsp:include page="header.jsp"></jsp:include>
		<section class="main">
			<div class="container">
				<h2 class="theme-color">所有歌手</h2>
				<div class="singer-list">
					<div class="row">
						<c:forEach items="${requestScope.singerList }" var="singer" varStatus="status">
							<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
								<a href="<%=contextPath %>/SingerServlet?info=play&playId=${singer.singerId}" class="thumbnail">
									<img src="<%=Constant.SINGER_PATH%>${singer.singerThumbnail}"/>
									<div class="caption text-center">
										<h4><c:out value="${singer.singerName }"></c:out> &nbsp;
											<small>
												<c:if test="${singer.singerSex==1 }">
													<span class="fa fa-mars"></span>
												</c:if>
												<c:if test="${singer.singerSex==2 }">
													<span class="fa fa-venus"></span>
												</c:if>
											</small>
										</h4>
									</div>
								</a>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</section>
		<jsp:include page="footer.jsp"></jsp:include>
	</body>
	<script src="<%=contextPath %>/js/jquery-3.1.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=contextPath %>/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
</html>