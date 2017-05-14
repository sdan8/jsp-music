<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String contextPath = request.getContextPath();%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>推荐歌曲</title>
		<script type="text/javascript"> 
			window.location.href="<%=contextPath %>/RecommendServlet?info=find";
		</script>
	</head>
	<body>
	
	</body>
</html>