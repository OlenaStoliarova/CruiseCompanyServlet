<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
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

<h2><fmt:message key="ui.admin.hello"/></h2>
<br/>
<a href="${pageContext.request.contextPath}/app/admin/users"><fmt:message key="ui.admin.userslist.view" /></a>
<br/>
<a href="${pageContext.request.contextPath}/app/admin/seaports"><fmt:message key="ui.ports.view" /></a>
<br/>
<a href="${pageContext.request.contextPath}/app/admin/ships"><fmt:message key="ui.ships.view" /></a>


</body>
</html>
