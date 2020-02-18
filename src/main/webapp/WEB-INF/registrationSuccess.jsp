<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">
<%@ include file="/WEB-INF/fragments/head.jsp" %>
<body>
<%@ include file="/WEB-INF/fragments/langlinks.jsp" %>
<%@ include file="/WEB-INF/fragments/topMenu.jsp" %>

<div class="container">
    <h2><fmt:message key="ui.registration.success" /></h2>
    <a class="btn btn-info" href="${pageContext.request.contextPath}/app/login"><fmt:message key="ui.login.login"/></a>
</div>

</body>
</html>
