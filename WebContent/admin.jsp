<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String contextPath = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<title>后台管理</title>
	<link rel="stylesheet" type="text/css" href="css/font-awesome.min.css"/>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" type="text/css" href="css/common.css"/>
	<link rel="stylesheet" type="text/css" href="css/login.css"/>
</head>
<body>
	<jsp:include page="adminheader.jsp"></jsp:include>
	<section class="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-offset-2 col-xs-8 col-sm-offset-3 col-sm-6 col-md-offset-4 col-md-4">
					<div class="main text-center">
						<!-- 标签标题开始 -->
						<ul class="nav" role="tablist">
							<li><h2 class="theme-color">后台登录</h2></li>
						</ul>
						<!-- 标签标题结束 -->
						<!-- 标签内容开始 -->
						<div>
							<!-- 登录 -->
							<div id="login">
								<form action="<%=contextPath %>/AdminServlet?info=login" method="post">
									<div class="form-group input-group">
										<span class="input-group-addon"><span class="fa fa-user fa-fw"></span></span>
										<input type="text" name="username" id="loginUsername" class="form-control" placeholder="用户名" required autofocus/>
									</div>
									<div class="form-group input-group">
										<span class="input-group-addon"><span class="fa fa-key fa-fw"></span></span>
										<input type="password" name="password" id="loginPassword" class="form-control" placeholder="密码" required/>
									</div>
									<div class="form-group">
										<button type="submit" class="btn btn-primary form-control">登录</button>
									</div>
								</form>
							</div>
							<!-- 登录结束 -->
						</div>
						<!-- 标签内容结束 -->
					</div><!-- /.main -->
				</div><!-- /.col-md-4 -->
			</div><!-- /.row -->
		</div><!-- /.container -->
	</section>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
	<script src="js/jquery-3.1.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
</html>