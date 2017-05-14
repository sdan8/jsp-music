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
		<title>所有专辑</title>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/font-awesome.min.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/bootstrap.min.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/common.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/albumlist.css"/>
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
				<h2 class="theme-color">所有专辑</h2>
				<div class="album-list">
					<div class="row">
						<c:forEach items="${requestScope.albumList }" var="album" varStatus="status">
							<div class="col-sm-4 col-md-3 col-lg-3">
								<a href="<%=contextPath %>/AlbumServlet?info=play&playId=${album.albumId}" class="thumbnail">
									<img src="<%=Constant.ALBUM_PATH %>${album.albumPic }"/>
									<div class="caption text-center">
										<h4><c:out value="${album.albumTitle }"></c:out></h4>
										<h5><c:out value="${album.albumPubDate }"></c:out></h5>
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