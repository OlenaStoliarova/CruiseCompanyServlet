<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">
<%@ include file="/WEB-INF/fragments/head.jsp" %>
<body>
<%@ include file="/WEB-INF/fragments/langlinks.jsp" %>
<div class="row">
    <div class="col-1 offset-11">
        <a class="btn btn-link" href="${pageContext.request.contextPath}/app/logout"><fmt:message key="ui.main.logout" /></a>
    </div>
</div>

<h2><fmt:message key="ui.tourist.hello"/></h2>
<br/>
<a href="${pageContext.request.contextPath}/app/tourist/cruises"><fmt:message key="ui.cruises.view" /></a>
<br/>
<a href="${pageContext.request.contextPath}/app/tourist/my_orders"><fmt:message key="ui.user.orders.view" /></a>

</body>
</html>
