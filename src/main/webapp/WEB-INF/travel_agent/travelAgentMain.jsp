<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
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

<br/>
<h2><fmt:message key="ui.travelagent.hello" /></h2>
<br/><br/>
<a href="${pageContext.request.contextPath}/app/travel_agent/orders"><fmt:message key="ui.agent.orders.view" /></a>
<br/><br/>
<a href="${pageContext.request.contextPath}/app/travel_agent/excursions"><fmt:message key="ui.excursions.view" /></a>


</body>
</html>
