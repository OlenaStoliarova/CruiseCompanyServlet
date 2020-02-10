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

<h2 class="text-center"><fmt:message key="ui.excursion.all.title"/></h2>
<div class="container-fluid">
    <div class="row">
        <form class="col-6 form-inline" method="get">
            <span><fmt:message key="ui.seaport"/></span>
            <select class="form-control" id="seaportId" name="seaportId" required>
                <option hidden disabled selected value>-</option>
                <c:forEach var="seaport" items="${requestScope.all_seaports}">
                    <option value="${seaport.id}" <c:if test="${seaport.id == param.seaportId}">selected</c:if>>
                        <c:out value="${seaport.name}"/></option>
                </c:forEach>
            </select>
            <input type="submit" class="btn btn-light" value="<fmt:message key='ui.button.filter'/>"/>
            <a class="btn btn-light" href="${pageContext.request.contextPath}/app/travel_agent/excursions"><fmt:message
                    key="ui.button.clear.filters"/></a>
        </form>
        <span class="col-6 text-right">
            <a class="btn btn-success btn-lg" href="${pageContext.request.contextPath}/app/travel_agent/add_excursion"><fmt:message key="ui.button.add"/></a>
        </span>
    </div>

    <c:if test="${empty requestScope.all_excursions}">
        <div class="text-success"><fmt:message key="ui.excursion.all.empty.list"/></div>
    </c:if>

    <c:if test="${param.deleted == true}">
        <div class="text-success"><fmt:message key="ui.excursion.deleted.ok"/></div>
    </c:if>
    <c:if test="${param.deleted == false}">
        <div class="text-danger"><fmt:message key="ui.excursion.not.deleted"/></div>
    </c:if>

    <div class="table-responsive">
        <table class="table table-condensed table-bordered table-hover">
            <thead>
            <tr class="table-primary">
                <th>id</th>
                <th><fmt:message key="ui.excursion.name.en"/></th>
                <th><fmt:message key="ui.excursion.name.ukr"/></th>
                <th><fmt:message key="ui.excursion.description.en"/></th>
                <th><fmt:message key="ui.excursion.description.ukr"/></th>
                <th><fmt:message key="ui.excursion.duration"/></th>
                <th><fmt:message key="ui.excursion.price"/></th>
                <th><fmt:message key="ui.seaport"/></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="excursion" items="${requestScope.all_excursions}">
                <tr>
                    <td><c:out value="${excursion.id}"/></td>
                    <td><c:out value="${excursion.nameEn}"/></td>
                    <td><c:out value="${excursion.nameUkr}"/></td>
                    <td><c:out value="${excursion.descriptionEn}"/></td>
                    <td><c:out value="${excursion.descriptionUkr}"/></td>
                    <td><c:out value="${excursion.approximateDurationHr}"/></td>
                    <td><fmt:formatNumber value="${excursion.price}"/></td>
                    <td><c:out value="${excursion.seaport.name} (${excursion.seaport.country})"/></td>
                    <td>
                        <form class="form-inline" action="${pageContext.request.contextPath}/app/travel_agent/edit_excursion" method="get">
                            <input type="hidden" name="excursionId" value="${excursion.id}"/>
                            <input type="submit" class="btn btn-light" value="<fmt:message key='ui.button.edit'/>" />
                        </form>
                        <form class="form-inline" action="${pageContext.request.contextPath}/app/travel_agent/delete_excursion" method="post">
                            <input type="hidden" name="excursionId" value="${excursion.id}"/>
                            <input type="submit" class="btn btn-light" value="<fmt:message key='ui.button.delete'/>" />
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>


</body>
</html>
