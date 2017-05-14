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
		<title><c:out value="${requestScope.songInfo.song.songTitle }"></c:out></title>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/font-awesome.min.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/bootstrap.min.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/common.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/song.css"/>
	</head>
	<body>
		<c:if test="${requestScope.flag==true }">
			<script type="text/javascript">
				alert('${requestScope.message}');
			</script>
		</c:if>
		<jsp:include page="header.jsp"></jsp:include>
		<div class="main">
			<div class="container">
				<div class="row">
					<div class="col-md-offset-3 col-md-6">
						<!-- 歌曲描述 -->
						<section class="description">
							<div class="media">
								<div class="media-left media-middle">
									<img class="media-object img-circle" src="<%=Constant.ALBUM_PATH %>/${requestScope.songInfo.album.albumPic}"/>
								</div>
								<div class="media-body">
									<h1 class="theme-color"><span class="fa fa-music"></span>&nbsp;<c:out value="${requestScope.songInfo.song.songTitle }"></c:out></h1>
									<div class="music-info">歌手：<span class="singer"><a href="<%=contextPath %>/SingerServlet?info=play&playId=${requestScope.songInfo.singer.singerId}"><c:out value="${requestScope.songInfo.singer.singerName }"></c:out></a></span></div>
								<div class="music-info">所属专辑：<span class="album"><a href="<%=contextPath %>/AlbumServlet?info=play&playId=${requestScope.songInfo.album.albumId}"><c:out value="${requestScope.songInfo.album.albumTitle }"></c:out></a></span></div>
								</div>
							</div>
						</section>
						<!-- 歌曲描述结束 -->
						<!-- 工具栏 -->
						<section class="toolbar">
							<div class="btn-group" role="group">
								<button type="button" id="play" class="btn btn-primary"><span class="fa fa-headphones"></span>&nbsp;试听</button>
								<c:if test="${sessionScope.login_flag==login_success}">
									<a href="SongServlet?info=follow&songId=${requestScope.songInfo.song.songId }&userId=${sessionScope.user.userId }" type="button" class="btn btn-default"><span class="fa fa-heart-o"></span>&nbsp;收藏</a>
								</c:if>
								<a href="SongServlet?info=download&songId=${requestScope.songInfo.song.songId }&path=<%=Constant.MUSIC_PATH%>${requestScope.songInfo.song.songFile }" target="_blank" type="button" class="btn btn-default"><span class="fa fa-download"></span>&nbsp;下载</a>
								<c:if test="${sessionScope.login_flag==login_success}">
									<button type="button" data-toggle="modal" data-target="#commentModal" class="btn btn-default"><span class="fa fa-commenting-o"></span>&nbsp;评论</button>
								</c:if>
							</div>
						</section>
						<!-- 工具栏结束 -->
						<!-- 评论 -->
						<section class="comments" id="comment">
							<h2 class="theme-color">评论</h2>
							<div class="comments-main">
								<ul class="media-list">
									<c:forEach items="${requestScope.songInfo.comments }" var="comment">
										<li class="media">
											<div class="media-left">
												<img class="media-object" src="<%=Constant.DEFAULT_AVATAR_SECPATH %>${comment.normalUser.userAvatar}"/>
											</div>
											<div class="media-body">
												<p><span class="nickname"><c:out value="${comment.normalUser.userNickname }"></c:out>：</span><c:out value="${comment.commentText }"></c:out></p>
											</div>
											
											<c:if test="${sessionScope.admin_login_flag==login_success}">
												<a href="#" class="pull-right">删除</a>
											</c:if>
										</li>
									</c:forEach>
								</ul>
							</div>
						</section>
						<!-- 评论结束 -->
					</div>
				</div>
			</div>
		</div>
		<!-- 评论模态框 -->
		<div class="modal fade" id="commentModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="myModalLabel">评论</h4>
					</div>
					<form action="<%=contextPath %>/CommentsServlet?info=add" method="post">
						<div class="modal-body">
							<div class="form-group">
								<label for="">评论内容</label>
								<textarea class="form-control" name="commentText" rows="4" maxlength="255" required></textarea>
							</div>
							<input type="hidden" value="${requestScope.songInfo.song.songId }" name="songId" />
							<input type="hidden" value="${sessionScope.user.userId }" name="userId" />
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
							<button type="submit" class="btn btn-primary">发表</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		
		<jsp:include page="footer.jsp"></jsp:include>
		<!-- 播放器开始 -->
		<div class="container player-container">
			<div id="player" class="aplayer"></div>
		</div>
		<!-- 播放器结束 -->
	</body>
	<script src="<%=contextPath %>/js/jquery-3.1.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=contextPath %>/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=contextPath %>/js/APlayer.min.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		var ap = new APlayer({
	        element: document.getElementById('player'),			// Optional, player element
	        narrow: false,										// Optional, narrow style
	        autoplay: false,									// Optional, autoplay song(s), not supported by mobile browers
	        showlrc: 0,											// Optional, show lrc, can be 0, 1, 2, see: docs ###With lrc
	        mutex: true,										// Optional, pause other players when this player playing
	        theme: '#1ABC9C',									// Optional, theme color, default: #b7daff
	        mode: 'random',										// Optional, play mode, can be 'random' 'single' 'circulation'(loop) 'order'(no loop), default: 'circulation'
	        preload: 'metadata',								// Optional, the way to load music, can be 'none' 'metadata' 'auto', default: 'auto'
	        listmaxheight: '513px',								// Optional, max height of play list
	        music: [											// Required, music info, see: docs ###With playlist
		        {
		            title: '${requestScope.songInfo.song.songTitle }',								// Required, music title
		            author: '${requestScope.songInfo.singer.singerName }',							// Required, music author
		            url: '<%=Constant.MUSIC_PATH%>${requestScope.songInfo.song.songFile }',						// Required, music url
		            pic: '<%=Constant.ALBUM_PATH %>/${requestScope.songInfo.album.albumPic}',					// Optional, music picture
		           // lrc: 'audio/lrc/lrcname.lrc'				// Optional, lrc, see: docs ###With lrc
		        }
	        ]
	    });
	    $('#play').on('click', function(){
	    	ap.play();
	    });
	</script>
</html>