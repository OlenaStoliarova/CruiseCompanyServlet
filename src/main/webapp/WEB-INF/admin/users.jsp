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

<h2 class="text-center"><fmt:message key="ui.users.title" /></h2>
<div class="container">
    <c:if test="${param.error_updating_role == true}">
        <h5 class="text-danger"><fmt:message key="ui.users.error.updating.role" /></h5>
    </c:if>
    <div class="table-responsive">
        <table class="table table-bordered table-hover">
            <thead>
            <tr class="table-primary">
                <th>id</th>
                <th><fmt:message key="ui.users.email" /></th>
                <th><fmt:message key="ui.users.first.name.en" /></th>
                <th><fmt:message key="ui.users.lastname.en" /></th>
                <th><fmt:message key="ui.users.first.name.native" /></th>
                <th><fmt:message key="ui.users.lastname.native" /></th>
                <th><fmt:message key="ui.users.role" /></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${allUsers}">
                <tr>
                    <td><c:out value="${user.id}"/></td>
                    <td><c:out value="${user.email}"/></td>
                    <td><c:out value="${user.firstNameEn}"/></td>
                    <td><c:out value="${user.lastNameEn}"/></td>
                    <td><c:out value="${user.firstNameNative}"/></td>
                    <td><c:out value="${user.lastNameNative}"/></td>
                    <td>
                        <c:if test="${user.email != sessionScope.user_name}">
                            <form class="form-inline" action="${pageContext.request.contextPath}/app/admin/updateUserRole" method="post">
                                <select class="form-control" id="userRoles" name="userRoles">
                                    <c:forEach var="role" items="${roles}">
                                        <option value="${role}" <c:if test="${role == user.role}">selected</c:if>>${role}</option>
                                    </c:forEach>
                                </select>
                                <input type="hidden" name="email" value="${user.email}">
                                <input class="btn btn-light" type="submit" value='<fmt:message key="ui.users.update.role" />'/>
                            </form>
                        </c:if>
                        <c:if test="${user.email == sessionScope.user_name}">
                            <span><c:out value="${user.role}"/></span>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
