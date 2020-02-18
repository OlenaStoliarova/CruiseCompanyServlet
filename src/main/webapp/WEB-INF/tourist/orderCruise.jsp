<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">
<%@ include file="/WEB-INF/fragments/head.jsp" %>
<body>
<%@ include file="/WEB-INF/fragments/langlinks.jsp" %>
<%@ include file="/WEB-INF/fragments/topMenu.jsp" %>

<h2 class="text-center"><fmt:message key="ui.cruise.order" /></h2>
<br/>

<c:if test="${requestScope.no_cruise_found == true}">
    <h3 class="text-danger"><fmt:message key="ui.error.cruise.not.found" /></h3>
</c:if>
<c:if test="${requestScope.no_cruise_found == null}">
    <div class="container">
        <c:set var="cruise" value="${requestScope.cruise}"/>
        <h4 class="font-weight-bold"><c:out value="${cruise.ship.routeName}"/></h4>
        <c:forEach var="port" items="${cruise.ship.visitingPorts}" varStatus="i">
            <span class="font-italic">
                <c:if test="${i.count < fn:length(cruise.ship.visitingPorts)}">
                    <c:out value="${port.name} (${port.country}) - "/>
                </c:if>
                <c:if test="${i.count == fn:length(cruise.ship.visitingPorts)}">
                    <c:out value="${port.name} (${port.country})"/>
                </c:if>
            </span>
        </c:forEach>
        <div class="font-italic font-weight-bold"><c:out value="${cruise.startingDate} - ${cruise.finishingDate}"/></div>
        <div><c:out value="${cruise.ship.name}"/></div>
        <br/>

        <c:if test="${requestScope.booking_error}">
            <h6 class="text-danger"><fmt:message key="ui.cruise.order.error" /></h6>
        </c:if>

        <form class="form-inline" method="post" action="${pageContext.request.contextPath}/app/tourist/order/cruise" >
            <input type="hidden" id="cruiseId" name="cruiseId" value="${cruise.id}">

            <div class="form-group mr-3">
                <label for="quantity"><fmt:message key="ui.cruise.ticket.quantity" /></label>
                <input class="form-control" id="quantity" name="quantity" required
                       pattern="\d{1,4}" title="<fmt:message key='regex.number.upto4digit.tip'/>">

            </div>
            <input type="submit" class="btn btn-success mr-3" value="<fmt:message key='ui.button.order'/>" />
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/app/tourist/cruises"><fmt:message key="ui.button.cancel"/></a>
        </form>
    </div>
</c:if>

</body>
</html>
