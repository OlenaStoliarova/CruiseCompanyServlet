<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
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

<h2 class="text-center"><fmt:message key="ui.cruise.all.title" /></h2>
<div class="container-fluid">
    <div class="row">
        <form class="col-6 form-inline" method="get">
            <label for="fromDate"><fmt:message key="ui.cruise.filter.by.starting.date"/></label>
            <input type="date" id="fromDate" name="fromDate" required
                   value="${param.fromDate}">
            <input type="submit" class="btn btn-light" value="<fmt:message key='ui.button.filter'/>"/>
            <a class="btn btn-light" href="${pageContext.request.contextPath}/app/tourist/cruises"><fmt:message
                    key="ui.button.clear.filters"/></a>
        </form>
    </div>

    <c:if test="${empty requestScope.all_cruises}">
        <div class="text-success"><fmt:message key="ui.cruise.all.empty.list"/></div>
    </c:if>

    <div class="table-responsive">
        <table class="table table-condensed table-bordered table-hover">
            <thead>
            <tr class="table-primary">
                <th><fmt:message key="ui.cruise.date"/></th>
                <th><fmt:message key="ui.cruise.route"/></th>
                <th><fmt:message key="ui.cruise.duration"/></th>
                <th><fmt:message key="ui.cruise.ship"/></th>
                <th><fmt:message key="ui.cruise.price"/></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="cruise" items="${requestScope.all_cruises}">
                <tr>
                    <td><c:out value="${cruise.startingDate}"/></td>
                    <td>
                        <p><c:out value="${cruise.ship.routeName}"/></p>
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
                    </td>
                    <td><c:out value="${cruise.ship.oneTripDurationDays}"/></td>
                    <td><c:out value="${cruise.ship.name} (${cruise.ship.capacity})"/></td>
                    <td><fmt:formatNumber value="${cruise.price}"/></td>

                    <td>
                        <form class="form-inline" action="${pageContext.request.contextPath}/app/tourist/order/cruise" method="get">
                            <input type="hidden" id="cruiseId" name="cruiseId" value="${cruise.id}"/>
                            <input type="submit" class="btn btn-link" value="<fmt:message key='ui.button.order'/>" />
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <c:set var="linkForPagination" value="${pageContext.request.contextPath}/app/tourist/cruises"/>
    <%@ include file="/WEB-INF/fragments/pagination.jsp" %>
</div>


</body>
</html>
