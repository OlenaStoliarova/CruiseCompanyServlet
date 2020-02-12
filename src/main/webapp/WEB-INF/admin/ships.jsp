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

<h2 class="text-center"><fmt:message key="ui.ships.all.title" /></h2>
<div class="container-fluid">
    <div class="row">
        <div class="col-12">
            <div class="table-responsive">
                <table class="table table-sm table-bordered table-hover">
                    <thead>
                    <tr class="table-primary">
                        <th>id</th>
                        <th><fmt:message key="ui.ship.name" /></th>
                        <th><fmt:message key="ui.ship.capacity" /></th>
                        <th><fmt:message key="ui.ship.route" /></th>
                        <th><fmt:message key="ui.ship.extras" /></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="ship" items="${requestScope.all_ships}">
                        <tr>
                            <td><c:out value="${ship.id}"/></td>
                            <td class="font-weight-bold"><c:out value="${ship.name}"/></td>
                            <td><c:out value="${ship.capacity}"/></td>
                            <td>
                                <p>
                                    <span><c:out value="${ship.routeName}"/></span>
                                    <span>(<c:out value="${ship.oneTripDurationDays}"/> <fmt:message key="ui.route.days" />)</span>
                                </p>
                                <c:forEach var="port" items="${ship.visitingPorts}" varStatus="i">
                                    <span class="font-italic">
                                        <c:if test="${i.count < fn:length(ship.visitingPorts)}">
                                            <c:out value="${port.name} (${port.country}) - "/>
                                        </c:if>
                                        <c:if test="${i.count == fn:length(ship.visitingPorts)}">
                                            <c:out value="${port.name} (${port.country})"/>
                                        </c:if>
                                    </span>
                                </c:forEach>
                            </td>
                            <td>
                                <c:forEach var="extra" items="${ship.extras}">
                                    <p><c:out value="${extra.name}"/></p>
                                </c:forEach>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

</body>
</html>
