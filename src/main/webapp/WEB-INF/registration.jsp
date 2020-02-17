<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">
<%@ include file="/WEB-INF/fragments/head.jsp" %>
<body>
<%@ include file="/WEB-INF/fragments/langlinks.jsp" %>


<div class="container">
    <div class="card-header">
        <h3 class="card-title"><fmt:message key="ui.registration.title" /></h3>
    </div><br/>
    <div class="card-body">
        <c:if test="${requestScope.validation_errors != null}">
            <h6 class="text-danger"><fmt:message key="ui.error.form.validation" /></h6>
        </c:if>
        <c:if test="${requestScope.non_unique == true}">
            <h6 class="text-danger"><fmt:message key="ui.registration.non.unique" /></h6>
        </c:if>
        <c:if test="${requestScope.registration_error == true}">
            <h6 class="text-danger"><fmt:message key="ui.registration.error" /></h6>
        </c:if>

        <c:set var="user" value="${requestScope.registration_user}"/>
        <form action="${pageContext.request.contextPath}/app/registration" method="post">
            <div class="form-group">
                <label for="email"><fmt:message key="ui.registration.email" /></label>
                <input type="email" class="form-control" id="email" name="email" required value="${user.email}">
            </div>
            <div class="form-group">
                <label for="password"><fmt:message key="ui.login.password" /></label>
                <input type="password" class="form-control" id="password" name="password" required>
            </div>
            <div class="form-group">
                <label for="repeatPassword"><fmt:message key="ui.registration.password.repeat" /></label>
                <input type="password" class="form-control" id="repeatPassword" name="repeatPassword" required>
            </div>

            <div class="form-group">
                <label for="nameEn"><fmt:message key="ui.registration.first.name.en" /></label>
                <input class="form-control" id="nameEn" name="nameEn" required value="${user.firstNameEn}"
                       pattern="<fmt:message key='regex.name.en'/>" title="<fmt:message key='regex.name.en.tip'/>">
            </div>
            <div class="form-group">
                <label for="lastnameEn"><fmt:message key="ui.registration.last.name.en" /></label>
                <input class="form-control" id="lastnameEn" name="lastnameEn" required value="${user.lastNameEn}"
                       pattern="<fmt:message key='regex.name.en'/>" title="<fmt:message key='regex.name.en.tip'/>">
            </div>
            <div class="form-group">
                <label for="nameNative"><fmt:message key="ui.registration.first.name.native" /></label>
                <input class="form-control" id="nameNative" name="nameNative" required value="${user.firstNameNative}">
            </div>
            <div class="form-group">
                <label for="lastnameNative"><fmt:message key="ui.registration.last.name.native" /></label>
                <input class="form-control" id="lastnameNative" name="lastnameNative" required value="${user.lastNameNative}">
            </div>

            <input type="submit" class="btn btn-primary" value="<fmt:message key='ui.registration.register'/>"/>
        </form>
    </div>
</div>


</body>
</html>
