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
		<title><c:out value="${requestScope.albumInfo.album.albumTitle }"></c:out></title>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/font-awesome.min.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/bootstrap.min.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/common.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/album.css"/>
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
				<div class="row">
					<div class="col-md-offset-2 col-md-8">
						<!-- 专辑描述 -->
						<section class="description">
							<div class="media">
								<div class="media-left media-middle">
									<img class="media-object img-circle" src="<%=Constant.ALBUM_PATH %>/${requestScope.albumInfo.album.albumPic}"/>
								</div>
								<div class="media-body">
									<h1 class="theme-color"><span class="fa fa-music"></span>&nbsp;<c:out value="${requestScope.albumInfo.album.albumTitle }"></c:out></h1>
									<div class="album-info">歌手：<span class="singer"><a href="<%=contextPath %>/SingerServlet?info=play&playId=${requestScope.albumInfo.singer.singerId}"><c:out value="${requestScope.albumInfo.singer.singerName }"></c:out></a></span></div>
									<div class="album-info">发行时间：<span class="date"><c:out value="${requestScope.albumInfo.album.albumPubDate }"></c:out></span></div>
									<div class="album-info">发行公司：<span class="company"><c:out value="${requestScope.albumInfo.album.albumPubCom }"></c:out></span></div>
								</div>
							</div>
						</section>
						<!-- 专辑描述结束 -->
						<!-- 工具栏 -->
						<section class="toolbar">
							<div class="btn-group" role="group">
								<c:if test="${sessionScope.login_flag==login_success}">
									<a href="AlbumServlet?info=follow&albumId=${requestScope.albumInfo.album.albumId }&userId=${sessionScope.user.userId }" type="button" class="btn btn-default"><span class="fa fa-heart-o"></span>&nbsp;喜欢</a>
								</c:if>
								
							</div>
						</section>
						<!-- 工具栏结束 -->
						<!-- 专辑介绍 -->
						<section class="album-desc">
							<h3><strong>歌手介绍：</strong></h3>
							<p><c:out value="${requestScope.albumInfo.singer.singerIntroduction }"></c:out></p>
						</section>
						<!-- 专辑介绍结束-->
						<!-- 歌曲列表 -->
						<section class="list">
							<h2 class="theme-color">包含歌曲列表</h2>
							<table class="table table-striped">
								<tr>
									<th class="list-num"></th>
									<th class="list-icon"></th>
									<th class="list-title">歌曲标题</th>
									<th class="list-singer">歌手</th>
								</tr>
								<c:forEach items="${requestScope.albumInfo.songs }" var="song" varStatus="status">
									<tr>
										<td><c:out value="${status.count }"></c:out></td>
										<td><a href="<%=contextPath %>/SongServlet?info=play&playId=${song.songId}"><span class="fa fa-play-circle-o"></span></a></td>
										<td><a href="<%=contextPath %>/SongServlet?info=play&playId=${song.songId}"><c:out value="${song.songTitle }"></c:out></a></td>
										<td><a href="<%=contextPath %>/SingerServlet?info=play&playId=${requestScope.albumInfo.singer.singerId}"><c:out value="${requestScope.albumInfo.singer.singerName }"></c:out></a></td>
									</tr>
								</c:forEach>
							</table>
						</section>
						<!-- 歌曲列表结束 -->
					</div>
				</div>
			</div>
		</section><!-- /.main -->
		<jsp:include page="footer.jsp"></jsp:include>
	</body>
	<script src="<%=contextPath %>/js/jquery-3.1.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=contextPath %>/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
</html>