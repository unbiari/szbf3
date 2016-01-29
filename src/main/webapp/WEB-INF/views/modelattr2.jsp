<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "form" uri = "http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	Hello Model Attribute2 Test...
	
	<form action="/csr/modelattr2.do" method="post">
		<input type="text" name="title" />
		<input type="text" name="content" />
		<button type="submit">Submit</button>
	</form>
	
</body>
</html>