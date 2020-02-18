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

<h2><fmt:message key="ui.cruise.excursions" /></h2>
<div class="container-fluid">
    <c:if test="${requestScope.untimely_operation == true}">
        <h3 class="text-danger"><fmt:message key="ui.error.order.wrong.stage.to.add.excursions" /></h3>
        <div><fmt:message key="ui.order.id"/>: <c:out value="${requestScope.order.id}"/></div>
        <div><fmt:message key="ui.order.status" />:
            <%@ include file="/WEB-INF/fragments/orderStatusNice.jsp" %></div>
    </c:if>
    <c:if test="${requestScope.illegal_request == true}">
        <h3 class="text-danger"><fmt:message key="ui.error.attempt.to.change.someone.elses.order" /></h3>
    </c:if>

    <c:if test="${requestScope.untimely_operation != true && requestScope.illegal_request != true}">
        <c:if test="${empty requestScope.excursions}">
            <div class="text-success"><fmt:message key="ui.excursion.all.empty.list"/></div>
        </c:if>
        <form action="${pageContext.request.contextPath}/app/tourist/order/add_excursions" method="post">
            <input type="hidden" name="orderId" value="${param.orderId}"/>
            <fieldset>
                <div class="table-responsive">
                    <table class="table table-condensed table-bordered table-hover">
                        <thead>
                        <tr class="table-primary">
                            <th></th>
                            <th><fmt:message key="ui.excursion.name" /></th>
                            <th><fmt:message key="ui.excursion.description" /></th>
                            <th><fmt:message key="ui.excursion.duration" /></th>
                            <th><fmt:message key="ui.excursion.price" /></th>
                            <th><fmt:message key="ui.excursion.port" /></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="excursion" items="${requestScope.excursions}">
                            <tr>
                                <td>
                                    <div class="form-group form-check">
                                        <input type="checkbox" class="form-check-input" id="chosenExcursions" name="chosenExcursions" value="${excursion.id}">
                                    </div>
                                </td>
                                <td><c:out value="${excursion.name}"/></td>
                                <td><c:out value="${excursion.description}"/></td>
                                <td><c:out value="${excursion.approximateDurationHr}"/></td>
                                <td><c:out value="${excursion.price}"/></td>
                                <td><c:out value="${excursion.seaport.name} (${excursion.seaport.country})"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </fieldset>
            <input type="submit" class="btn btn-success" value="<fmt:message key='ui.cruise.add.excursions'/>" />
        </form>
    </c:if>
</div>

</body>
</html>
