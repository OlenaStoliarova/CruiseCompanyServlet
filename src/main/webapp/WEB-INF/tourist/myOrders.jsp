<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
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
    <h2><fmt:message key="ui.user.orders.all.title" /></h2>
    <div class="table-responsive">
    <c:if test="${!empty requestScope.orders}">
        <table class="table table-condensed table-bordered table-hover">
            <thead>
            <tr class="table-primary">
                <th><fmt:message key="ui.order.id"/></th>
                <th><fmt:message key="ui.order.date"/></th>
                <th><fmt:message key="ui.order.details"/></th>
                <th><fmt:message key="ui.cruise.ticket.quantity"/></th>
                <th><fmt:message key="ui.order.excursions"/></th>
                <th><fmt:message key="ui.order.bonuses"/></th>
                <th><fmt:message key="ui.price.total"/></th>
                <th><fmt:message key="ui.order.status"/></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${requestScope.orders}">
                <tr>
                    <td><c:out value="${order.id}"/></td>
                    <td><c:out value="${order.creationDate}"/></td>
                    <td>
                        <span><c:out value="${order.cruise.ship.routeName}"/></span>
                        <div class="font-italic"><c:out value="${order.cruise.startingDate} -"/></div>
                        <div class="font-italic"><c:out value="${order.cruise.finishingDate}"/></div>
                    </td>
                    <td><c:out value="${order.quantity}"/></td>
                    <td>
                        <c:if test="${order.status == 'NEW'}">
                            <span><fmt:message key="ui.order.excursions.promise"/></span>
                        </c:if>
                        <c:if test="${order.status == 'CANCELED'}">
                            <span><fmt:message key="ui.order.canceled"/></span>
                        </c:if>
                        <c:if test="${order.status == 'PAID'}">
                             <form class="form-inline" action="${pageContext.request.contextPath}/app/tourist/order/excursions" method="get">
                                <input type="hidden" id="orderId" name="orderId" value="${order.id}"/>
                                <input type="submit" class="btn btn-success" value="<fmt:message key='ui.button.add'/>" />
                             </form>
                        </c:if>
                        <span style="white-space:pre"><c:out value="${order.addedExcursions}"/></span>
                    </td>
                    <td>
                        <c:if test="${order.status == 'CANCELED'}">
                            <span><fmt:message key="ui.order.canceled"/></span>
                        </c:if>
                        <c:if test="${order.freeExtras == null && (order.status == 'NEW' || order.status == 'PAID' || order.status == 'EXCURSIONS_ADDED') }">
                            <span><fmt:message key="ui.order.bonuses.promise"/></span>
                        </c:if>
                        <span><c:out value="${order.freeExtras}"/></span>
                    </td>
                    <td><fmt:formatNumber value="${order.totalPrice}"/></td>
                    <td>
                        <%@ include file="/WEB-INF/fragments/orderStatusNice.jsp" %>
                    </td>
                    <td>
                        <c:if test="${order.status == 'NEW'}">
                            <form class="form-inline" action="${pageContext.request.contextPath}/app/tourist/pay_order" method="post">
                                <input type="hidden" name="orderId" value="${order.id}"/>
                                <input type="submit" class="btn btn-success" value="<fmt:message key='ui.button.pay'/>" />
                            </form>
                            <form class="form-inline" action="${pageContext.request.contextPath}/app/tourist/cancel_order" method="post">
                                <input type="hidden" name="orderId" value="${order.id}"/>
                                <input type="submit" class="btn btn-secondary" value="<fmt:message key='ui.button.cancel'/>" />
                            </form>
                        </c:if>
                        <c:if test="${order.status == 'EXTRAS_ADDED'}">
                            <a href="${pageContext.request.contextPath}/app/tourist/print_order" class="btn btn-success">
                                <fmt:message key="ui.button.print"/>
                            </a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
        <c:if test="${empty requestScope.orders}">
            <div class="text-success"><fmt:message key="ui.orders.list.empty"/></div>
        </c:if>
    </div>
</div>

</body>
</html>
