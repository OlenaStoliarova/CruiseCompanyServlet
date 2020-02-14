<%@ page pageEncoding="UTF-8" %>
<span>
    <c:choose>
        <c:when test="${order.status == 'NEW'}">
            <fmt:message key="ui.order.status.new"/>
        </c:when>
        <c:when test="${order.status == 'CANCELED'}">
            <fmt:message key="ui.order.status.canceled"/>
        </c:when>
        <c:when test="${order.status == 'PAID'}">
            <fmt:message key="ui.order.status.paid"/>
        </c:when>
        <c:when test="${order.status == 'EXCURSIONS_ADDED'}">
            <fmt:message key="ui.order.status.excursions_added"/>
        </c:when>
        <c:when test="${order.status == 'EXTRAS_ADDED'}">
            <fmt:message key="ui.order.status.extras_added"/>
        </c:when>
    </c:choose>
</span>