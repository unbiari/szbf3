<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
	<title>Home</title>
</head>
<body>

<%
 //모든 클라이언트가 공유하는 영역.
 Integer count = (Integer)application.getAttribute("count");
 if(count==null){ //처음 방문이라면 
  count=1;
  application.setAttribute("count", count);
 }else{ //처음 방문이 아니라면 
  count++;
  application.setAttribute("count", count);
 }
%>

<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
<p>application 을 실행한 이후 <%=count%> 번째 고객입니다 </p>

</body>

</html>
