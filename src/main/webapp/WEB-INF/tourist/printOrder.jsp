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
    <h5><fmt:message key="ui.print.ticket.message1" /></h5>
    <h5><fmt:message key="ui.print.ticket.message2" /></h5>
    <h5><fmt:message key="ui.print.ticket.message3" /></h5>
    <br/>
    <h1 class="text-center"><fmt:message key="ui.oder.bye" /></h1>
</div>

</body>
</html>
