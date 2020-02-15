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

<h2 class="text-center"><fmt:message key="ui.cruise.bonuses" /></h2>
<div class="container">
    <div class="font-weight-bold"><fmt:message key="ui.order.id" />: ${param.orderId}</div>
    <div class="font-weight-bold">
        <span><fmt:message key="ui.price.total" />: <fmt:formatNumber value="${requestScope.order.totalPrice}"/></span>
    </div>
    <br/>
    <c:if test="${requestScope.no_order_found == true}">
        <h3 class="text-danger"><fmt:message key="ui.error.order.not.found" /></h3>
    </c:if>

    <c:if test="${ !empty requestScope.bonuses}">
        <form action="${pageContext.request.contextPath}/app/travel_agent/order/add_bonuses" method="post">
            <input type="hidden" id="orderId" name="orderId" value="${requestScope.order.id}"/>
            <fieldset>
                <c:forEach var="bonus" items="${requestScope.bonuses}">
                    <div class="form-group form-check">
                        <input type="checkbox" class="form-check-input" id="chosenBonuses" name="chosenBonuses" value="${bonus.id}">
                        <label class="form-check-label" for="chosenBonuses"><c:out value="${bonus.name}"/></label>
                    </div>
                </c:forEach>
            </fieldset>
            <input type="submit" class="btn btn-success" value="<fmt:message key='ui.button.add'/>" />
        </form>
    </c:if>

    <c:if test="${empty requestScope.bonuses}">
        <div class="text-success">No bonuses found</div>
    </c:if>
</div>

</body>
</html>
