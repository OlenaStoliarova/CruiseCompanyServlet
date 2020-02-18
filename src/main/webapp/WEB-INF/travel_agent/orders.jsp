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

<div class="container-fluid">
    <div class="table-responsive">
        <table class="table table-condensed table-bordered table-hover">
            <thead>
            <tr class="table-primary">
                <th><fmt:message key="ui.order.id"/></th>
                <th><fmt:message key="ui.order.date"/></th>
                <th><fmt:message key="ui.order.client"/></th>
                <th><fmt:message key="ui.order.details"/></th>
                <th><fmt:message key="ui.cruise.ticket.quantity"/></th>
                <th><fmt:message key="ui.price.total"/></th>
                <th><fmt:message key="ui.order.status"/></th>
                <th><fmt:message key="ui.order.excursions"/></th>
                <th><fmt:message key="ui.order.bonuses"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${requestScope.orders}">
                <tr>
                    <td><c:out value="${order.id}"/></td>
                    <td><c:out value="${order.creationDate}"/></td>
                    <td><c:out value="${order.user.email}"/></td>
                    <td>
                        <span><c:out value="${order.cruise.ship.name}"/></span>
                        <div class="font-italic"><c:out value="${order.cruise.startingDate}"/></div>
                    </td>
                    <td><c:out value="${order.quantity}"/></td>
                    <td><fmt:formatNumber value="${order.totalPrice}"/></td>
                    <td>
                        <%@ include file="/WEB-INF/fragments/orderStatusNice.jsp" %>
                    </td>
                    <td>
                        <c:if test="${order.status == 'NEW' || order.status == 'PAID'}">
                            <span><fmt:message key="ui.order.excursions.waiting"/></span>
                        </c:if>
                        <c:if test="${order.status == 'CANCELED'}">
                            <span><fmt:message key="ui.order.canceled"/></span>
                        </c:if>
                        <span style="white-space:pre"><c:out value="${order.addedExcursions}"/></span>
                    </td>
                    <td>
                        <c:if test="${order.status == 'NEW' || order.status == 'PAID'}">
                            <span><fmt:message key="ui.order.excursions.waiting"/></span>
                        </c:if>
                        <c:if test="${order.status == 'CANCELED'}">
                            <span><fmt:message key="ui.order.canceled"/></span>
                        </c:if>
                        <c:if test="${order.status == 'EXCURSIONS_ADDED'}">
                            <form class="form-inline" action="${pageContext.request.contextPath}/app/travel_agent/order/add_bonuses" method="get">
                                <input type="hidden" id="orderId" name="orderId" value="${order.id}"/>
                                <input type="submit" class="btn btn-success" value="<fmt:message key='ui.button.add'/>" />
                            </form>
                        </c:if>
                        <span><c:out value="${order.freeExtras}"/></span>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:if test="${empty requestScope.orders}">
            <div class="text-success"><fmt:message key="ui.orders.list.empty"/></div>
        </c:if>
    </div>
    <c:set var="linkForPagination" value="${pageContext.request.contextPath}/app/travel_agent/orders"/>
    <%@ include file="/WEB-INF/fragments/pagination.jsp" %>
</div>


</body>
</html>
