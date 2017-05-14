<%@page import="music.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%String contextPath = request.getContextPath();
int male = Constant.SEX_MALE;
int female = Constant.SEX_FEMALE;
int sexDefault = Constant.SEX_DEFAULT;
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title>歌手管理</title>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/font-awesome.min.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/bootstrap.min.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/common.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/bootstrap-jquery-pagination.css"/>
		<style type="text/css">
			.btn-group {
				margin-bottom: 10px;
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
				<h2 class="theme-color">歌手管理</h2>
				<div class="row">
					<div class="col-md-12">
						<form action="<%=contextPath %>/SingerServlet?info=find&pageNum=1" method="post" id="formSub">
							<div class="btn-group">
								<button type="button" class="btn btn-default" data-toggle="modal" data-target="#modalAdd"><span class="fa fa-plus"></span>&nbsp;添加歌手</button>
								<button type="button" class="btn btn-default" data-toggle="modal" data-target="#modalDel"><span class="fa fa-minus"></span>&nbsp;删除歌手</button>
								<button type="button" class="btn btn-default" data-toggle="modal" data-target="#modalUpdate"><span class="fa fa-gear"></span>&nbsp;修改歌手</button>
								<button type="submit" class="btn btn-default" id="btnSubmit"><span class="fa fa-refresh"></span>&nbsp;刷新</button>
							</div>
						</form>
						<table class="table table-striped">
							<tr>
								<th>歌手ID</th>
								<th>歌手名</th>
								<th>歌手性别</th>
							</tr>
							<tbody id="list-content">
								<c:forEach items="${result.dataList }" var="singer">
									<tr>
										<td><c:out value="${singer.singerId }"></c:out> </td>
										<td><c:out value="${singer.singerName }"></c:out> </td>
										<td>
											<c:if test="${singer.singerSex eq 0 }">
												<c:out value="保密"></c:out>
											</c:if>
											<c:if test="${singer.singerSex eq 1 }">
												<c:out value="男"></c:out>
											</c:if>
											<c:if test="${singer.singerSex eq 2 }">
												<c:out value="女"></c:out>
											</c:if>
										 </td>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="4">
										<ul class="pagination">
											<li><a href="#" onclick="firstPage();">首页</a></li>
											<li><a href="#" onclick="previousPage();">上一页</a></li>
											<li><a href="#" onclick="nextPage();">下一页</a></li>
											<li><a href="#" onclick="lastPage();">尾页</a></li>
										</ul>
									</td>
								</tr>
							</tfoot>
						</table>
					</div>
				</div>
			</div>
		</div>
		<!-- 模态框 -->
		<!-- 添加 -->
		<div class="modal fade" id="modalAdd" tabindex="-1">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="modalAddLabel">添加歌手</h4>
					</div>
					
					<form action="<%=contextPath %>/SingerServlet?info=add" method="post" enctype="multipart/form-data">
						<div class="modal-body">
							<div class="form-group">
								<input type="text" class="form-control" placeholder="歌手名" name="singerName" required />
							</div>
							<div class="form-group">
								<label>性别</label>
								<label class="radio-inline">
									<input type="radio" name="sex" id="inlineRadio1" value="<%=male%>"> 男
								</label>
								<label class="radio-inline">
									<input type="radio" name="sex" id="inlineRadio2" value="<%=female%>"> 女
								</label>
								<label class="radio-inline">
									<input type="radio" name="sex" id="inlineRadio3" value="<%=sexDefault%>" checked > 保密
								</label>
							</div>
							<div class="form-group">
								<label for="">歌手介绍</label>
								<textarea class="form-control" name="singerIntroduction" rows="4" maxlength="255" required></textarea>
							</div>
							<div class="form-group">
								<label>歌手图片</label>
								<input type="file" name="filename" required/>
								<p class="help-block">仅支持.jpg或.png格式</p>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	        				<button type="submit" class="btn btn-primary">添加</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		
		<!-- 删除 -->
		<div class="modal fade" id="modalDel" tabindex="-1">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="modalDelLabel">删除歌手</h4>
					</div>
					<form action="<%=contextPath %>/SingerServlet?info=del" method="post">
						<div class="modal-body">
							<div class="form-group">
								<input type="text" class="form-control" placeholder="歌手ID" name="singerId" />
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	        				<button type="submit" class="btn btn-primary">删除</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		
		<!-- 修改 -->
		<div class="modal fade" id="modalUpdate" tabindex="-1">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="modalUpdateLabel">修改歌手</h4>
					</div>
					<form action="<%=contextPath %>/SingerServlet?info=update" method="post" enctype="multipart/form-data">
						<div class="modal-body">
							<div class="form-group">
								<input type="text" class="form-control" placeholder="歌手ID" name="singerId" />
							</div>
							<div class="form-group">
								<input type="text" class="form-control" placeholder="歌手名" name="singerName" />
							</div>
							<div class="form-group">
								<label>性别</label>
								<label class="radio-inline">
									<input type="radio" name="sex" id="inlineRadio1" value="<%=male%>" /> 男
								</label>
								<label class="radio-inline">
									<input type="radio" name="sex" id="inlineRadio2" value="<%=female%>" /> 女
								</label>
								<label class="radio-inline">
									<input type="radio" name="sex" id="inlineRadio3" value="<%=sexDefault%>" checked /> 保密
								</label>
							</div>
							<div class="form-group">
								<label for="">歌手介绍</label>
								<textarea class="form-control" rows="4" maxlength="255" name="singerIntroduction"></textarea>
							</div>
							<div class="form-group">
								<label>歌手图片</label>
								<input type="file" name=filename required/>
								<p class="help-block">仅支持.jpg或.png格式</p>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	        				<button type="submit" class="btn btn-primary">修改</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!-- 模态框结束 -->
	</body>
	<script src="<%=contextPath %>/js/jquery-3.1.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=contextPath %>/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		
		var currentPage = ${result.currentPage};
		
		// 总页数
		var totalPage = ${result.totalPage};
		
		function submitForm(actionUrl){
			var formElement = document.getElementById("formSub");
			formElement.action = actionUrl;
			formElement.submit();
		}
		function nextPage(){
			if(currentPage == totalPage){
				alert("已经是最后一页数据");
				return false;
			}else{
				submitForm("<%=contextPath %>/SingerServlet?info=find&pageNum=" + (currentPage+1));
				return true;
			}
		}
		
		function previousPage(){
			if(currentPage == 1){
				alert("已经是第一页数据");
				return false;
			}else{
				submitForm("<%=contextPath %>/SingerServlet?info=find&pageNum=" + (currentPage-1));
				return true;
			}
		}
		// 第一页
		function firstPage(){
			if(currentPage == 1){
				alert("已经是第一页数据");
				return false;
			}else{
				submitForm("<%=contextPath %>/SingerServlet?info=find&pageNum=1");
				return true;
			}
		}
		// 尾页
		function lastPage(){
			if(currentPage == totalPage){
				alert("已经是最后一页数据");
				return false;
			}else{
				submitForm("<%=contextPath %>/SingerServlet?info=find&pageNum=${result.totalPage}");
				return true;
			}
		}
	</script>
</html>