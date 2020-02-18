<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">
<head>
    <%@ include file="/WEB-INF/fragments/head.jsp" %>
</head>
<body>
<div class="container text-center">
    <h3>403</h3>
    <h1><fmt:message key="ui.error.403.title" /></h1>
    <h5><fmt:message key="ui.error.403.description" /></h5>
    <a href="${pageContext.request.contextPath}/app/main"><fmt:message key="ui.main.tomain" /></a>
</div>
</body>
</html>