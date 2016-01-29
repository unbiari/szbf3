<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link rel="stylesheet" type="text/css" href="<c:url value ='/easyui/themes/default/easyui.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value ='/easyui/themes/icon.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value ='/easyui/demo/demo.css'/>" />

<script type="text/javascript" src="<c:url value='/easyui/jquery.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/easyui/jquery.easyui.min.js'/>"></script>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
