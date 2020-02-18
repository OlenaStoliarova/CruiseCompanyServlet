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

<div class="container-lg">
    <h2><fmt:message key="ui.ports.all.title" /></h2>
    <div class="row">
        <div class="col-7">
            <div class="table-responsive">
                <table class="table table-sm table-bordered table-hover">
                    <thead>
                    <tr class="table-primary">
                        <th>id</th>
                        <th><fmt:message key="ui.port.name.en" /></th>
                        <th><fmt:message key="ui.port.country.en" /></th>
                        <th><fmt:message key="ui.port.name.ukr" /></th>
                        <th><fmt:message key="ui.port.country.ukr" /></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="port" items="${all_ports}">
                        <tr>
                            <td><c:out value="${port.id}"/></td>
                            <td><c:out value="${port.nameEn}"/></td>
                            <td><c:out value="${port.countryEn}"/></td>
                            <td><c:out value="${port.nameUkr}"/></td>
                            <td><c:out value="${port.countryUkr}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="col-5">
            <h3><fmt:message key="ui.port.add" /></h3>
            <form action="${pageContext.request.contextPath}/app/admin/addPort" method="post">
                <div class="form-group">
                    <label for="nameEn"><fmt:message key="ui.port.add.label.name.en" /></label>
                    <input class="form-control" id="nameEn" name="nameEn" required="required"
                           pattern="<fmt:message key='regex.name.en'/>" title="<fmt:message key='regex.name.en.tip'/>">
                </div>
                <div class="form-group">
                    <label for="countryEn"><fmt:message key="ui.port.add.label.country.en" /></label>
                    <input class="form-control" id="countryEn" name="countryEn" required
                           pattern="<fmt:message key='regex.name.en'/>" title="<fmt:message key='regex.name.en.tip'/>">
                </div>
                <div class="form-group">
                    <label for="nameUkr"><fmt:message key="ui.port.add.label.name.ukr" /></label>
                    <input class="form-control" id="nameUkr" name="nameUkr" required
                           pattern="<fmt:message key='regex.name.ukr'/>" title="<fmt:message key='regex.name.ukr.tip'/>">
                </div>
                <div class="form-group">
                    <label for="countryUkr"><fmt:message key="ui.port.add.label.country.ukr" /></label>
                    <input class="form-control" id="countryUkr" name="countryUkr" required
                           pattern="<fmt:message key='regex.name.ukr'/>" title="<fmt:message key='regex.name.ukr.tip'/>">
                </div>
                <c:if test="${param.error==true}">
                    <div class="text-danger"><fmt:message key="ui.port.add.error" /></div>
                </c:if>
                <input type="submit" class="btn btn-primary" value="<fmt:message key='ui.button.add'/>"/>
            </form>
        </div>
    </div>
</div>

</body>
</html>